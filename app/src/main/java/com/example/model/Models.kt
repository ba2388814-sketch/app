package com.example.model

import androidx.compose.ui.graphics.Color
import com.example.R
import com.example.ui.theme.InstagramPink
import com.example.ui.theme.MessengerBlue
import com.example.ui.theme.TelegramBlue
import com.example.ui.theme.WhatsAppGreen

data class WebService(
    val id: String,
    val name: String,
    val domain: String,
    val url: String,
    val iconResId: Int,
    val brandColor: Color,
    val description: String
)

data class SelectedFileInfo(
    val uriString: String,
    val name: String,
    val sizeFormatted: String,
    val sizeBytes: Long,
    val mimeType: String,
    val extension: String,
    val timestamp: Long,
    val previewText: String? = null
)

val DEFAULT_SERVICES = listOf(
    WebService(
        id = "whatsapp",
        name = "WhatsApp",
        domain = "web.whatsapp.com",
        url = "https://web.whatsapp.com/",
        iconResId = R.drawable.ic_whatsapp,
        brandColor = WhatsAppGreen,
        description = "Official WhatsApp Web Client"
    ),
    WebService(
        id = "messenger",
        name = "Messenger",
        domain = "messenger.com",
        url = "https://www.messenger.com/",
        iconResId = R.drawable.ic_messenger,
        brandColor = MessengerBlue,
        description = "Official Facebook Messenger"
    ),
    WebService(
        id = "telegram",
        name = "Telegram",
        domain = "web.telegram.org",
        url = "https://web.telegram.org/k/",
        iconResId = R.drawable.ic_telegram,
        brandColor = TelegramBlue,
        description = "Official Telegram Web App"
    ),
    WebService(
        id = "instagram",
        name = "Instagram",
        domain = "instagram.com",
        url = "https://www.instagram.com/",
        iconResId = R.drawable.ic_instagram,
        brandColor = InstagramPink,
        description = "Official Instagram Web"
    )
)
