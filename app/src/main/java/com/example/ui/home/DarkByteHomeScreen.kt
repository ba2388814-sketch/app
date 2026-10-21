package com.example.ui.home

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.DEFAULT_SERVICES
import com.example.model.SelectedFileInfo
import com.example.model.WebService
import com.example.ui.components.BottomNavBar
import com.example.ui.components.DarkByteHeader
import com.example.ui.components.DarkByteToast
import com.example.ui.components.DesktopPreferenceCard
import com.example.ui.components.DeveloperInfoModal
import com.example.ui.components.FileInspectModal
import com.example.ui.components.FileManagerCard
import com.example.ui.components.FooterSection
import com.example.ui.components.HeroSection
import com.example.ui.components.SecurityNoteCard
import com.example.ui.components.ServiceCard
import com.example.ui.theme.DarkByteBg
import com.example.ui.theme.DarkByteMuted
import com.example.ui.theme.DarkByteWhite
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun DarkByteHomeScreen(
    isOnline: Boolean,
    isDesktopMode: Boolean,
    selectedFile: SelectedFileInfo?,
    isDeveloperModalOpen: Boolean,
    fileToInspect: SelectedFileInfo?,
    toastMessage: String?,
    currentTab: String,
    onToggleDesktopMode: () -> Unit,
    onOpenService: (WebService) -> Unit,
    onFileSelected: (Uri) -> Unit,
    onClearSelectedFile: () -> Unit,
    onInspectFile: (SelectedFileInfo) -> Unit,
    onCloseInspectModal: () -> Unit,
    onOpenDeveloperModal: () -> Unit,
    onCloseDeveloperModal: () -> Unit,
    onSelectTab: (String) -> Unit,
    onClearToast: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    var appsSectionY = 0f
    var filesSectionY = 0f

    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri ->
        if (uri != null) {
            onFileSelected(uri)
        }
    }

    // Auto clear toast after 3 seconds
    LaunchedEffect(toastMessage) {
        if (toastMessage != null) {
            delay(3000)
            onClearToast()
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = DarkByteBg,
        bottomBar = {
            BottomNavBar(
                activeTab = currentTab,
                onTabSelected = { tab ->
                    onSelectTab(tab)
                    coroutineScope.launch {
                        when (tab) {
                            "home" -> scrollState.animateScrollTo(0)
                            "apps" -> scrollState.animateScrollTo(appsSectionY.toInt().coerceAtLeast(0))
                            "files" -> scrollState.animateScrollTo(filesSectionY.toInt().coerceAtLeast(0))
                            "developer" -> onOpenDeveloperModal()
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(Color(0x285278D2), Color.Transparent),
                        center = androidx.compose.ui.geometry.Offset(500f, 0f),
                        radius = 900f
                    )
                )
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .widthIn(max = 760.dp)
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        // Header
                        DarkByteHeader(isOnline = isOnline)

                        // Hero Section
                        HeroSection()

                        // Desktop Mode Preference
                        DesktopPreferenceCard(
                            isDesktopMode = isDesktopMode,
                            onToggle = onToggleDesktopMode
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        // Section Head: Applications
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .onGloballyPositioned { coordinates ->
                                    appsSectionY = coordinates.positionInParent().y
                                },
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "YOUR APPLICATIONS",
                                color = DarkByteWhite,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Black,
                                letterSpacing = 1.5.sp
                            )
                            Text(
                                text = "4 SERVICES",
                                color = DarkByteMuted,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // 2x2 Grid of services
                        Column(
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            // Row 1: WhatsApp & Messenger
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                ServiceCard(
                                    service = DEFAULT_SERVICES[0], // WhatsApp
                                    onClick = { onOpenService(DEFAULT_SERVICES[0]) },
                                    modifier = Modifier.weight(1f)
                                )
                                ServiceCard(
                                    service = DEFAULT_SERVICES[1], // Messenger
                                    onClick = { onOpenService(DEFAULT_SERVICES[1]) },
                                    modifier = Modifier.weight(1f)
                                )
                            }

                            // Row 2: Telegram & Instagram
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                ServiceCard(
                                    service = DEFAULT_SERVICES[2], // Telegram
                                    onClick = { onOpenService(DEFAULT_SERVICES[2]) },
                                    modifier = Modifier.weight(1f)
                                )
                                ServiceCard(
                                    service = DEFAULT_SERVICES[3], // Instagram
                                    onClick = { onOpenService(DEFAULT_SERVICES[3]) },
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(26.dp))

                        // MIC File Manager
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .onGloballyPositioned { coordinates ->
                                    filesSectionY = coordinates.positionInParent().y
                                }
                        ) {
                            FileManagerCard(
                                selectedFile = selectedFile,
                                onChooseFile = {
                                    filePickerLauncher.launch(
                                        arrayOf(
                                            "*/*",
                                            "application/zip",
                                            "application/x-zip-compressed",
                                            "application/x-chrome-extension",
                                            "application/javascript",
                                            "application/json",
                                            "application/x-xpinstall"
                                        )
                                    )
                                },
                                onInspectFile = onInspectFile,
                                onClearFile = onClearSelectedFile
                            )
                        }

                        Spacer(modifier = Modifier.height(18.dp))

                        // Security Note
                        SecurityNoteCard()

                        // Footer
                        FooterSection()
                    }
                }
            }

            // Glowing Toast popup
            DarkByteToast(
                message = toastMessage,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 24.dp)
            )
        }
    }

    // Developer modal
    DeveloperInfoModal(
        isOpen = isDeveloperModalOpen,
        onDismiss = onCloseDeveloperModal
    )

    // File Inspect modal
    FileInspectModal(
        fileInfo = fileToInspect,
        onDismiss = onCloseInspectModal
    )
}
