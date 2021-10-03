package com.radiantmood.kuttit.util

import android.app.Activity
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType.IMMEDIATE
import com.google.android.play.core.install.model.UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS
import com.google.android.play.core.install.model.UpdateAvailability.UPDATE_AVAILABLE

object AppUpdater {
    private const val UPDATE_REQ_CODE = 979
    private const val MAX_PRIO = 5

    fun onResume(activity: Activity) {
        maybePromptForUpdate(activity)
    }

    fun onActivityResult(activity: Activity, requestCode: Int, resultCode: Int) {
        if (requestCode == UPDATE_REQ_CODE && resultCode != ComponentActivity.RESULT_OK) {
            Toast.makeText(activity,
                "Something went wrong. This update is required for the app to function.",
                Toast.LENGTH_SHORT).show()
            maybePromptForUpdate(activity)
        }
    }

    @Suppress("UNREACHABLE_CODE")
    private fun maybePromptForUpdate(activity: Activity) {
        // TODO#15zpcjv: test in-app updates
        // https://developer.android.com/guide/playcore/in-app-updates/test
        return

        val appUpdateManager = AppUpdateManagerFactory.create(activity)

        // Returns an intent object that you use to check for an update.
        val appUpdateInfoTask = appUpdateManager.appUpdateInfo

        // Checks whether the platform allows the specified type of update, and checks the update priority.
        appUpdateInfoTask.addOnSuccessListener { info ->
            if (info.isUpdateInProgress() || info.shouldUpdate()) {
                appUpdateManager.startUpdateFlowForResult(
                    info,
                    IMMEDIATE,
                    activity,
                    UPDATE_REQ_CODE
                )
            }
        }
    }

    private fun AppUpdateInfo.shouldUpdate() =
        isUpdateAvailable() && isUpdateMaxPriority() && canImmediatelyUpdate()

    private fun AppUpdateInfo.isUpdateAvailable() = updateAvailability() == UPDATE_AVAILABLE

    private fun AppUpdateInfo.isUpdateInProgress() =
        updateAvailability() == DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS

    private fun AppUpdateInfo.isUpdateMaxPriority() = updatePriority() >= MAX_PRIO

    private fun AppUpdateInfo.canImmediatelyUpdate() = isUpdateTypeAllowed(IMMEDIATE)
}