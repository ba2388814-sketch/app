package com.example.ui

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.net.Uri
import android.provider.OpenableColumns
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.model.DEFAULT_SERVICES
import com.example.model.SelectedFileInfo
import com.example.model.WebService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.Locale

class DarkByteViewModel(application: Application) : AndroidViewModel(application) {

    private val prefs = application.getSharedPreferences("dark_byte_prefs", Context.MODE_PRIVATE)

    private val _isDesktopMode = MutableStateFlow(prefs.getBoolean("desktop_mode", true))
    val isDesktopMode: StateFlow<Boolean> = _isDesktopMode.asStateFlow()

    private val _activeService = MutableStateFlow<WebService?>(null)
    val activeService: StateFlow<WebService?> = _activeService.asStateFlow()

    private val _selectedFile = MutableStateFlow<SelectedFileInfo?>(null)
    val selectedFile: StateFlow<SelectedFileInfo?> = _selectedFile.asStateFlow()

    private val _fileHistory = MutableStateFlow<List<SelectedFileInfo>>(emptyList())
    val fileHistory: StateFlow<List<SelectedFileInfo>> = _fileHistory.asStateFlow()

    private val _isDeveloperModalOpen = MutableStateFlow(false)
    val isDeveloperModalOpen: StateFlow<Boolean> = _isDeveloperModalOpen.asStateFlow()

    private val _fileToInspect = MutableStateFlow<SelectedFileInfo?>(null)
    val fileToInspect: StateFlow<SelectedFileInfo?> = _fileToInspect.asStateFlow()

    private val _toastMessage = MutableStateFlow<String?>(null)
    val toastMessage: StateFlow<String?> = _toastMessage.asStateFlow()

    private val _currentTab = MutableStateFlow("home")
    val currentTab: StateFlow<String> = _currentTab.asStateFlow()

    private val _isOnline = MutableStateFlow(true)
    val isOnline: StateFlow<Boolean> = _isOnline.asStateFlow()

    init {
        monitorNetwork(application)
    }

    private fun monitorNetwork(context: Context) {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
        if (connectivityManager != null) {
            val activeNetwork = connectivityManager.activeNetwork
            val caps = connectivityManager.getNetworkCapabilities(activeNetwork)
            _isOnline.value = caps?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true

            val request = NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .build()
            connectivityManager.registerNetworkCallback(request, object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    _isOnline.value = true
                }

                override fun onLost(network: Network) {
                    _isOnline.value = false
                }
            })
        }
    }

    fun toggleDesktopMode() {
        val newVal = !_isDesktopMode.value
        _isDesktopMode.value = newVal
        prefs.edit().putBoolean("desktop_mode", newVal).apply()
        showToast(
            if (newVal) "Desktop preference enabled. Best for WhatsApp Web QR & Full views."
            else "Mobile preference enabled."
        )
    }

    fun openService(service: WebService) {
        _activeService.value = service
        showToast("Opening ${service.name}...")
    }

    fun closeBrowser() {
        _activeService.value = null
    }

    fun setTab(tab: String) {
        _currentTab.value = tab
        if (tab == "developer") {
            _isDeveloperModalOpen.value = true
        }
    }

    fun openDeveloperModal() {
        _isDeveloperModalOpen.value = true
    }

    fun closeDeveloperModal() {
        _isDeveloperModalOpen.value = false
    }

    fun inspectFile(file: SelectedFileInfo) {
        _fileToInspect.value = file
    }

    fun closeInspectModal() {
        _fileToInspect.value = null
    }

    fun clearSelectedFile() {
        _selectedFile.value = null
        showToast("File selection cleared")
    }

    fun onFileSelected(uri: Uri) {
        viewModelScope.launch {
            try {
                val context = getApplication<Application>()
                val contentResolver = context.contentResolver

                var fileName = "Unknown File"
                var fileSize: Long = 0

                contentResolver.query(uri, null, null, null, null)?.use { cursor ->
                    if (cursor.moveToFirst()) {
                        val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                        val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)
                        if (nameIndex != -1) fileName = cursor.getString(nameIndex) ?: "Unknown"
                        if (sizeIndex != -1 && !cursor.isNull(sizeIndex)) {
                            fileSize = cursor.getLong(sizeIndex)
                        }
                    }
                }

                val mimeType = contentResolver.getType(uri) ?: "application/octet-stream"
                val extension = if (fileName.contains(".")) {
                    fileName.substringAfterLast(".", "").lowercase(Locale.ROOT)
                } else {
                    ""
                }

                var previewText: String? = null
                val isTextLike = mimeType.startsWith("text/") ||
                        extension in listOf("json", "js", "txt", "html", "css", "xml", "csv", "md")
                if (isTextLike && fileSize in 1..262144) {
                    try {
                        contentResolver.openInputStream(uri)?.use { inputStream ->
                            val reader = BufferedReader(InputStreamReader(inputStream))
                            val sb = StringBuilder()
                            var line: String?
                            var lineCount = 0
                            while (reader.readLine().also { line = it } != null && lineCount < 200) {
                                sb.append(line).append("\n")
                                lineCount++
                            }
                            previewText = sb.toString()
                        }
                    } catch (_: Exception) {
                        previewText = null
                    }
                }

                val fileInfo = SelectedFileInfo(
                    uriString = uri.toString(),
                    name = fileName,
                    sizeFormatted = formatBytes(fileSize),
                    sizeBytes = fileSize,
                    mimeType = mimeType,
                    extension = extension,
                    timestamp = System.currentTimeMillis(),
                    previewText = previewText
                )

                _selectedFile.value = fileInfo
                _fileHistory.update { list ->
                    listOf(fileInfo) + list.filter { it.uriString != uri.toString() }.take(9)
                }
                showToast("File selected: $fileName")
            } catch (e: Exception) {
                showToast("Error selecting file: ${e.message}")
            }
        }
    }

    fun showToast(message: String) {
        _toastMessage.value = message
    }

    fun clearToast() {
        _toastMessage.value = null
    }

    private fun formatBytes(bytes: Long): String {
        if (bytes <= 0) return "0 B"
        if (bytes < 1024) return "$bytes B"
        if (bytes < 1024 * 1024) return String.format(Locale.US, "%.1f KB", bytes / 1024.0)
        if (bytes < 1024 * 1024 * 1024) return String.format(Locale.US, "%.2f MB", bytes / (1024.0 * 1024.0))
        return String.format(Locale.US, "%.2f GB", bytes / (1024.0 * 1024.0 * 1024.0))
    }
}
