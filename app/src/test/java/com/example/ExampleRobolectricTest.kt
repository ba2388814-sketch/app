package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.model.DEFAULT_SERVICES
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("Dark Byte", appName)
  }

  @Test
  fun `verify default services count and ids`() {
    assertEquals(4, DEFAULT_SERVICES.size)
    val ids = DEFAULT_SERVICES.map { it.id }
    assertTrue(ids.contains("whatsapp"))
    assertTrue(ids.contains("messenger"))
    assertTrue(ids.contains("telegram"))
    assertTrue(ids.contains("instagram"))
  }
}
