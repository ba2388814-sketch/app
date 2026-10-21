package com.example.ui.components

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Laptop
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.model.SelectedFileInfo
import com.example.model.WebService
import com.example.ui.theme.DarkByteAccent
import com.example.ui.theme.DarkByteBg
import com.example.ui.theme.DarkByteBlue
import com.example.ui.theme.DarkByteDanger
import com.example.ui.theme.DarkByteLine
import com.example.ui.theme.DarkByteMuted
import com.example.ui.theme.DarkBytePanel2
import com.example.ui.theme.DarkByteSuccess
import com.example.ui.theme.DarkByteSurface
import com.example.ui.theme.DarkByteWhite

@Composable
fun DarkByteHeader(
    isOnline: Boolean,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val dotAlpha by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(900, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "dotAlpha"
    )

    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // DB Metallic Icon
                Box(
                    modifier = Modifier
                        .size(46.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .background(
                            brush = Brush.linearGradient(
                                listOf(
                                    Color(0xFFFFFFFF),
                                    Color(0xFFAEBFE8),
                                    Color(0xFF4A5876)
                                )
                            )
                        )
                        .border(
                            1.dp,
                            Color(0x99FFFFFF),
                            RoundedCornerShape(15.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "DB",
                        color = Color(0xFF0A1020),
                        fontWeight = FontWeight.Black,
                        fontSize = 19.sp,
                        letterSpacing = 0.5.sp
                    )
                }

                Column {
                    Text(
                        text = "DARK BYTE",
                        color = DarkByteWhite,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 0.6.sp
                    )
                    Text(
                        text = "MIC 121 SYSTEM",
                        color = Color(0xFFA9B9DA),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 2.5.sp
                    )
                }
            }

            // Status Badge
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color(0x22111521))
                    .border(0.5.dp, DarkByteLine, RoundedCornerShape(20.dp))
                    .padding(horizontal = 10.dp, vertical = 6.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .alpha(if (isOnline) dotAlpha else 1f)
                        .background(if (isOnline) DarkByteSuccess else DarkByteDanger)
                )
                Text(
                    text = if (isOnline) "SYSTEM READY" else "OFFLINE",
                    color = if (isOnline) Color(0xFFC7D3E9) else DarkByteDanger,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.8.sp
                )
            }
        }

        HorizontalDivider(color = DarkByteLine, thickness = 1.dp)
    }
}

@Composable
fun HeroSection(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 22.dp, bottom = 18.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0x1A83AAFF))
                .border(0.5.dp, Color(0x4483AAFF), RoundedCornerShape(12.dp))
                .padding(horizontal = 10.dp, vertical = 4.dp)
        ) {
            Text(
                text = "✦",
                color = DarkByteBlue,
                fontSize = 10.sp
            )
            Text(
                text = "SECURE WEB LAUNCHER",
                color = Color(0xFFA7C0FF),
                fontSize = 10.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 1.8.sp
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "YOUR WEB.\nYOUR CONTROL.",
            color = DarkByteWhite,
            fontSize = 30.sp,
            fontWeight = FontWeight.Black,
            lineHeight = 36.sp,
            letterSpacing = (-0.5).sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Instagram, Telegram, WhatsApp এবং Messenger-এর অফিসিয়াল ওয়েবপেজ এক জায়গা থেকে দ্রুত ও ফুলস্ক্রিনে খুলুন।",
            color = DarkByteMuted,
            fontSize = 12.5.sp,
            lineHeight = 20.sp
        )
    }
}

@Composable
fun DesktopPreferenceCard(
    isDesktopMode: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("desktop_toggle_card"),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = DarkByteSurface),
        border = BorderStroke(1.dp, Color(0x3D83AAFF))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(Color(0x225784FF), Color.Transparent),
                        radius = 600f
                    )
                )
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(RoundedCornerShape(15.dp))
                            .background(Color(0x2182A5FF))
                            .border(1.dp, Color(0x4582A5FF), RoundedCornerShape(15.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Laptop,
                            contentDescription = "Desktop Mode",
                            tint = DarkByteBlue,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Column {
                        Text(
                            text = "Desktop Experience",
                            color = DarkByteWhite,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Black
                        )
                        Text(
                            text = "Loads full desktop sites (Best for WhatsApp QR)",
                            color = DarkByteMuted,
                            fontSize = 10.5.sp,
                            lineHeight = 15.sp
                        )
                    }
                }

                Switch(
                    checked = isDesktopMode,
                    onCheckedChange = { onToggle() },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color(0xFF17233D),
                        checkedTrackColor = DarkByteAccent,
                        uncheckedThumbColor = DarkByteWhite,
                        uncheckedTrackColor = Color(0xFF303646)
                    ),
                    modifier = Modifier.testTag("desktop_switch")
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = DarkByteLine, thickness = 1.dp)
            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (isDesktopMode) "Desktop preference is ON" else "Mobile-friendly preference is ON",
                    color = Color(0xFFAEBBD2),
                    fontSize = 10.5.sp
                )
                Text(
                    text = if (isDesktopMode) "DESKTOP ON" else "MOBILE ON",
                    color = if (isDesktopMode) DarkByteSuccess else DarkByteBlue,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 1.sp
                )
            }
        }
    }
}

@Composable
fun ServiceCard(
    service: WebService,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(176.dp)
            .clickable(onClick = onClick)
            .testTag("service_card_${service.id}"),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = DarkBytePanel2),
        border = BorderStroke(1.dp, Color(0x1FFFFFFF))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .clip(RoundedCornerShape(17.dp))
                        .background(
                            brush = Brush.linearGradient(
                                listOf(Color(0xFF283244), Color(0xFF121622))
                            )
                        )
                        .border(1.dp, Color(0x2AFFFFFF), RoundedCornerShape(17.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = service.iconResId),
                        contentDescription = service.name,
                        tint = Color.Unspecified,
                        modifier = Modifier.size(32.dp)
                    )
                }

                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .clip(CircleShape)
                        .border(1.dp, DarkByteLine, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "↗",
                        color = Color(0xFFCBD8F4),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Column {
                Text(
                    text = service.name,
                    color = DarkByteWhite,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Black
                )
                Text(
                    text = service.domain,
                    color = DarkByteMuted,
                    fontSize = 10.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "OPEN IN APP →",
                    color = DarkByteBlue,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 1.2.sp
                )
            }
        }
    }
}

@Composable
fun FileManagerCard(
    selectedFile: SelectedFileInfo?,
    onChooseFile: () -> Unit,
    onInspectFile: (SelectedFileInfo) -> Unit,
    onClearFile: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("file_manager_card"),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = DarkByteSurface),
        border = BorderStroke(1.dp, DarkByteLine)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color(0xFF252D3E)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Folder,
                        contentDescription = "Folder",
                        tint = DarkByteAccent,
                        modifier = Modifier.size(22.dp)
                    )
                }

                Column {
                    Text(
                        text = "MIC FILE MANAGER",
                        color = DarkByteWhite,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Black
                    )
                    Text(
                        text = "Select an extension or archive file (.crx, .zip, .js, .json, .xpi)",
                        color = DarkByteMuted,
                        fontSize = 10.sp,
                        lineHeight = 14.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onChooseFile,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("choose_file_button"),
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFDFE8FF),
                    contentColor = Color(0xFF101729)
                )
            ) {
                Text(
                    text = "＋ SELECT FILE FROM DEVICE",
                    fontWeight = FontWeight.Black,
                    fontSize = 12.sp,
                    letterSpacing = 0.5.sp
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            if (selectedFile != null) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = DarkBytePanel2),
                    border = BorderStroke(1.dp, Color(0x3383AAFF))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(Color(0x3383AAFF))
                                        .padding(horizontal = 6.dp, vertical = 3.dp)
                                ) {
                                    Text(
                                        text = selectedFile.extension.uppercase().ifEmpty { "FILE" },
                                        color = DarkByteBlue,
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Black
                                    )
                                }
                                Text(
                                    text = selectedFile.name,
                                    color = DarkByteWhite,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }

                            IconButton(
                                onClick = onClearFile,
                                modifier = Modifier.size(24.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Clear",
                                    tint = DarkByteMuted,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "${selectedFile.sizeFormatted} • ${selectedFile.mimeType}",
                            color = DarkByteMuted,
                            fontSize = 10.sp
                        )

                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = { onInspectFile(selectedFile) },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(38.dp),
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0x2283AAFF),
                                    contentColor = DarkByteAccent
                                ),
                                contentPadding = PaddingValues(horizontal = 8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Visibility,
                                    contentDescription = null,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Inspect", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }

                            Button(
                                onClick = {
                                    try {
                                        val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                            type = selectedFile.mimeType
                                            putExtra(
                                                Intent.EXTRA_STREAM,
                                                Uri.parse(selectedFile.uriString)
                                            )
                                            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                                        }
                                        context.startActivity(
                                            Intent.createChooser(shareIntent, "Share file")
                                        )
                                    } catch (_: Exception) { }
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(38.dp),
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0x22FFFFFF),
                                    contentColor = DarkByteWhite
                                ),
                                contentPadding = PaddingValues(horizontal = 8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Share,
                                    contentDescription = null,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Share", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            } else {
                Text(
                    text = "No file selected",
                    color = Color(0xFFAAB6CB),
                    fontSize = 11.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun SecurityNoteCard(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0x09FFFFFF)),
        border = BorderStroke(1.dp, Color(0x14FFFFFF))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                    tint = DarkByteAccent,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = "SECURITY NOTE",
                    color = DarkByteAccent,
                    fontSize = 10.5.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 1.sp
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "এই লঞ্চার কোনো অ্যাকাউন্টের পাসওয়ার্ড বা ব্যক্তিগত লগইন তথ্য সংগ্রহ করে না। নিরাপদে লগইন করার জন্য শুধুমাত্র সংশ্লিষ্ট অফিসিয়াল ওয়েবসাইট ব্যবহার করুন।",
                color = Color(0xFF9BA8BD),
                fontSize = 10.5.sp,
                lineHeight = 17.sp
            )
        }
    }
}

@Composable
fun FooterSection(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 28.dp, bottom = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .width(65.dp)
                .height(2.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(
                    brush = Brush.horizontalGradient(
                        listOf(Color.Transparent, DarkByteBlue, Color.Transparent)
                    )
                )
        )
        Spacer(modifier = Modifier.height(14.dp))
        Text(
            text = "DARK BYTE MIC 121 SYSTEM",
            color = DarkByteWhite,
            fontSize = 15.sp,
            fontWeight = FontWeight.Black,
            letterSpacing = 1.2.sp
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = "MY MIC IS 2026 BEST",
            color = Color(0xFF8794AC),
            fontSize = 10.sp,
            letterSpacing = 0.8.sp
        )
        Text(
            text = "CREATED FOR ANDROID • MOBILE-FIRST NATIVE UI",
            color = Color(0xFFD4DEF2),
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
        )
    }
}

@Composable
fun BottomNavBar(
    activeTab: String,
    onTabSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = Color(0xEE090C13),
        border = BorderStroke(1.dp, Color(0x1AFFFFFF))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val tabs = listOf(
                Triple("home", "⌂", "HOME"),
                Triple("apps", "▦", "APPS"),
                Triple("files", "▤", "FILES"),
                Triple("developer", "👨‍💻", "DEVELOPER")
            )

            tabs.forEach { (key, icon, label) ->
                val isSelected = activeTab == key
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = { onTabSelected(key) }
                        )
                        .padding(horizontal = 14.dp, vertical = 4.dp)
                        .testTag("nav_tab_$key")
                ) {
                    Text(
                        text = icon,
                        fontSize = 19.sp,
                        color = if (isSelected) DarkByteAccent else Color(0xFF8491A8)
                    )
                    Spacer(modifier = Modifier.height(3.dp))
                    Text(
                        text = label,
                        fontSize = 9.sp,
                        fontWeight = if (isSelected) FontWeight.Black else FontWeight.Bold,
                        color = if (isSelected) DarkByteAccent else Color(0xFF8491A8),
                        letterSpacing = 0.6.sp
                    )
                }
            }
        }
    }
}

@Composable
fun DeveloperInfoModal(
    isOpen: Boolean,
    onDismiss: () -> Unit
) {
    if (!isOpen) return

    val context = LocalContext.current

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xCC020409))
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onDismiss
                )
                .padding(20.dp),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = { /* prevent dismiss */ }
                    )
                    .testTag("developer_modal"),
                shape = RoundedCornerShape(25.dp),
                colors = CardDefaults.cardColors(containerColor = DarkByteSurface),
                border = BorderStroke(1.dp, Color(0x4791B1FF))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            brush = Brush.radialGradient(
                                colors = listOf(Color(0x2E5784FF), Color.Transparent),
                                radius = 700f
                            )
                        )
                        .padding(20.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(46.dp)
                                    .clip(RoundedCornerShape(15.dp))
                                    .background(
                                        brush = Brush.linearGradient(
                                            listOf(DarkByteAccent, Color(0xFF718BC5))
                                        )
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("👨‍💻", fontSize = 22.sp)
                            }
                            Column {
                                Text(
                                    text = "Developer Info",
                                    color = DarkByteWhite,
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.Black
                                )
                                Text(
                                    text = "DARK BYTE MIC 121 • Official Center",
                                    color = DarkByteMuted,
                                    fontSize = 10.sp
                                )
                            }
                        }

                        IconButton(
                            onClick = onDismiss,
                            modifier = Modifier
                                .size(34.dp)
                                .clip(CircleShape)
                                .background(Color(0x1AFFFFFF))
                                .border(1.dp, DarkByteLine, CircleShape)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close",
                                tint = DarkByteWhite,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color(0x0DFFFFFF))
                            .border(1.dp, Color(0x14FFFFFF), RoundedCornerShape(16.dp))
                            .padding(14.dp)
                    ) {
                        Column {
                            Text(
                                text = "Welcome to DARK BYTE MIC 121.",
                                color = DarkByteAccent,
                                fontSize = 11.5.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "এই সিস্টেমটি তৈরি করা হয়েছে দ্রুত, নিরাপদ ও সহজভাবে প্রয়োজনীয় ওয়েব সার্ভিসে প্রবেশের জন্য। কোনো সমস্যা বা সহযোগিতার প্রয়োজন হলে যোগাযোগ করুন।",
                                color = Color(0xFFB8C4DA),
                                fontSize = 10.5.sp,
                                lineHeight = 17.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Contact buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        ContactCard(
                            icon = "✈️",
                            title = "Telegram",
                            subtitle = "Message on TG",
                            onClick = {
                                try {
                                    context.startActivity(
                                        Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/"))
                                    )
                                } catch (_: Exception) { }
                            },
                            modifier = Modifier.weight(1f)
                        )
                        ContactCard(
                            icon = "🌐",
                            title = "Official Site",
                            subtitle = "Visit website",
                            onClick = {
                                try {
                                    context.startActivity(
                                        Intent(Intent.ACTION_VIEW, Uri.parse("https://google.com"))
                                    )
                                } catch (_: Exception) { }
                            },
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    val infoItems = listOf(
                        "Support" to "অ্যাপের ব্যবহার, ফাইল ম্যানেজার বা ওয়েব লিংক সংক্রান্ত সহায়তার জন্য সর্বদা প্রস্তুত।",
                        "Updates" to "নতুন ফিচার ও গুরুত্বপূর্ণ নিরাপত্তা ঘোষণা অফিশিয়াল চ্যানেলে দেওয়া হবে।",
                        "Privacy" to "আপনার পাসওয়ার্ড বা ব্যক্তিগত লগইন তথ্য এই লঞ্চার কখনোই সংগ্রহ বা শেয়ার করে না।"
                    )

                    infoItems.forEach { (title, desc) ->
                        Row(
                            verticalAlignment = Alignment.Top,
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.padding(vertical = 4.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .padding(top = 5.dp)
                                    .size(6.dp)
                                    .clip(CircleShape)
                                    .background(DarkByteBlue)
                            )
                            Text(
                                text = "$title: $desc",
                                color = Color(0xFFAEBBD2),
                                fontSize = 10.5.sp,
                                lineHeight = 16.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    HorizontalDivider(color = DarkByteLine, thickness = 1.dp)
                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "DARK BYTE MIC 121 SYSTEM\nBuilt with care for a faster and safer web experience.",
                        color = Color(0xFF8794AC),
                        fontSize = 9.sp,
                        textAlign = TextAlign.Center,
                        lineHeight = 14.sp,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Composable
fun ContactCard(
    icon: String,
    title: String,
    subtitle: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .clip(RoundedCornerShape(15.dp))
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0x12FFFFFF)),
        border = BorderStroke(1.dp, Color(0x1FFFFFFF))
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFF252E43)),
                contentAlignment = Alignment.Center
            ) {
                Text(icon, fontSize = 16.sp)
            }
            Column {
                Text(title, color = DarkByteWhite, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                Text(subtitle, color = DarkByteMuted, fontSize = 9.sp, maxLines = 1)
            }
        }
    }
}

@Composable
fun FileInspectModal(
    fileInfo: SelectedFileInfo?,
    onDismiss: () -> Unit
) {
    if (fileInfo == null) return

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xCC020409))
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onDismiss
                )
                .padding(20.dp),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = {}
                    ),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = DarkByteSurface),
                border = BorderStroke(1.dp, Color(0x3383AAFF))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "File Inspection",
                                color = DarkByteWhite,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Black
                            )
                            Text(
                                text = fileInfo.name,
                                color = DarkByteBlue,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                        IconButton(onClick = onDismiss) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close",
                                tint = DarkByteWhite
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Metadata details
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(DarkBytePanel2)
                            .padding(10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text("Size", color = DarkByteMuted, fontSize = 9.sp)
                            Text(fileInfo.sizeFormatted, color = DarkByteWhite, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                        Column {
                            Text("MIME Type", color = DarkByteMuted, fontSize = 9.sp)
                            Text(fileInfo.mimeType, color = DarkByteWhite, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                        Column {
                            Text("Format", color = DarkByteMuted, fontSize = 9.sp)
                            Text(fileInfo.extension.uppercase().ifEmpty { "N/A" }, color = DarkByteSuccess, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = if (fileInfo.previewText != null) "Text Preview (First 200 lines):" else "Binary / Archive file (Preview not available as plain text)",
                        color = DarkByteMuted,
                        fontSize = 10.sp
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    if (fileInfo.previewText != null) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(220.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color(0xFF090D17))
                                .border(1.dp, DarkByteLine, RoundedCornerShape(10.dp))
                                .padding(10.dp)
                                .verticalScroll(rememberScrollState())
                        ) {
                            Text(
                                text = fileInfo.previewText,
                                color = Color(0xFFC7D4EC),
                                fontSize = 10.sp,
                                fontFamily = FontFamily.Monospace,
                                lineHeight = 15.sp
                            )
                        }
                    } else {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color(0xFF090D17))
                                .border(1.dp, DarkByteLine, RoundedCornerShape(10.dp))
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Binary extension archive file (${fileInfo.extension})\nReady to be shared or packaged with MIC 121.",
                                color = DarkByteMuted,
                                fontSize = 11.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))
                    Button(
                        onClick = onDismiss,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = DarkByteBlue,
                            contentColor = DarkByteBg
                        )
                    ) {
                        Text("Done", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun DarkByteToast(
    message: String?,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = message != null,
        enter = fadeIn() + slideInVertically(initialOffsetY = { 40 }),
        exit = fadeOut() + slideOutVertically(targetOffsetY = { 40 }),
        modifier = modifier
    ) {
        if (message != null) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(14.dp))
                    .background(Color(0xEE222A3A))
                    .border(1.dp, Color(0x33FFFFFF), RoundedCornerShape(14.dp))
                    .padding(horizontal = 16.dp, vertical = 10.dp)
            ) {
                Text(
                    text = message,
                    color = DarkByteWhite,
                    fontSize = 11.5.sp,
                    lineHeight = 16.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
