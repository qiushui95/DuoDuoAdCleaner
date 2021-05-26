package com.duoduo.child.story.ad.cleaner

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo

internal class AdCleanerService : AccessibilityService() {



    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        when (event.eventType) {
            AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED -> onViewChanged()
            else -> {
            }
        }
    }

    private fun onViewChanged() {
        val rootNode = rootInActiveWindow ?: return

        val closeButtons =
            rootNode.findAccessibilityNodeInfosByViewId("com.duoduo.child.story:id/iv_close_banner")

        closeButtons.forEach {
            it.performAction(AccessibilityNodeInfo.ACTION_CLICK)
        }
    }

    override fun onInterrupt() {

    }
}