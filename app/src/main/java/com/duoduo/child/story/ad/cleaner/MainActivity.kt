package com.duoduo.child.story.ad.cleaner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.text.TextUtils
import com.duoduo.child.story.ad.cleaner.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.tvStatus.setOnClickListener {
            val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }

    private fun isServiceOn(): Boolean {

        val service = "$packageName/${AdCleanerService::class.java.canonicalName}"

        val accessibilityEnabled = try {
            Settings.Secure.getInt(
                applicationContext.contentResolver,
                Settings.Secure.ACCESSIBILITY_ENABLED
            )
        } catch (e: Exception) {
            return false
        }

        if (accessibilityEnabled != 1) {
            return false
        }

        val settingValue = Settings.Secure.getString(
            applicationContext.contentResolver,
            Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
        ) ?: return false

        val stringSplitter = TextUtils.SimpleStringSplitter(':')
        stringSplitter.setString(settingValue)

        while (stringSplitter.hasNext()) {
            val accessibilityService = stringSplitter.next()

            if (accessibilityService.equals(service, true)) {
                return true
            }
        }

        return false
    }

    override fun onResume() {
        super.onResume()

        if (isServiceOn()) {
            binding.tvStatus.setText(R.string.accessibility_service_on)
        } else {
            binding.tvStatus.setText(R.string.accessibility_service_off)
        }
    }
}