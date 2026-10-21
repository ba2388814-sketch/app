package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.DarkByteViewModel
import com.example.ui.browser.InAppBrowserScreen
import com.example.ui.home.DarkByteHomeScreen
import com.example.ui.theme.DarkByteBg
import com.example.ui.theme.DarkByteTheme

class MainActivity : ComponentActivity() {

    private val viewModel: DarkByteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            DarkByteTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = DarkByteBg
                ) {
                    val isOnline by viewModel.isOnline.collectAsStateWithLifecycle()
                    val isDesktopMode by viewModel.isDesktopMode.collectAsStateWithLifecycle()
                    val activeService by viewModel.activeService.collectAsStateWithLifecycle()
                    val selectedFile by viewModel.selectedFile.collectAsStateWithLifecycle()
                    val isDeveloperModalOpen by viewModel.isDeveloperModalOpen.collectAsStateWithLifecycle()
                    val fileToInspect by viewModel.fileToInspect.collectAsStateWithLifecycle()
                    val toastMessage by viewModel.toastMessage.collectAsStateWithLifecycle()
                    val currentTab by viewModel.currentTab.collectAsStateWithLifecycle()

                    Crossfade(
                        targetState = activeService,
                        label = "ScreenTransition"
                    ) { service ->
                        if (service != null) {
                            InAppBrowserScreen(
                                service = service,
                                isDesktopMode = isDesktopMode,
                                onToggleDesktopMode = { viewModel.toggleDesktopMode() },
                                onClose = { viewModel.closeBrowser() }
                            )
                        } else {
                            DarkByteHomeScreen(
                                isOnline = isOnline,
                                isDesktopMode = isDesktopMode,
                                selectedFile = selectedFile,
                                isDeveloperModalOpen = isDeveloperModalOpen,
                                fileToInspect = fileToInspect,
                                toastMessage = toastMessage,
                                currentTab = currentTab,
                                onToggleDesktopMode = { viewModel.toggleDesktopMode() },
                                onOpenService = { s -> viewModel.openService(s) },
                                onFileSelected = { uri -> viewModel.onFileSelected(uri) },
                                onClearSelectedFile = { viewModel.clearSelectedFile() },
                                onInspectFile = { f -> viewModel.inspectFile(f) },
                                onCloseInspectModal = { viewModel.closeInspectModal() },
                                onOpenDeveloperModal = { viewModel.openDeveloperModal() },
                                onCloseDeveloperModal = { viewModel.closeDeveloperModal() },
                                onSelectTab = { tab -> viewModel.setTab(tab) },
                                onClearToast = { viewModel.clearToast() }
                            )
                        }
                    }
                }
            }
        }
    }
}
