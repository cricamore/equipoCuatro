package com.cristian.miniproyecto2.view

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.widget.RemoteViews
import com.cristian.miniproyecto2.R
import com.google.firebase.auth.FirebaseAuth

/**
 * Implementation of App Widget functionality.
 */
class WidgetAppInventory : AppWidgetProvider() {
    private lateinit var sharedPreferences: SharedPreferences

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {

        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        val action = intent!!.action ?: ""

        if (context != null && action == "changeIcon") {
            val firebaseAuth = FirebaseAuth.getInstance()
            val perfs = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
            val editor = perfs.edit()

            if(firebaseAuth.currentUser == null){
                val intent = Intent(context, LoginActivity::class.java)
                intent.putExtra("launchedFromWidget", true)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
            }else {
                // Toggle the isEyeOpen value
                editor.putBoolean("isEyeOpen", !perfs.getBoolean("isEyeOpen", false))
                editor.apply()

                val appWidgetManager = AppWidgetManager.getInstance(context)
                val appWidgetIds = appWidgetManager.getAppWidgetIds(
                    ComponentName(context, WidgetAppInventory::class.java)
                )
                for (appWidgetId in appWidgetIds) {
                    updateAppWidget(context, appWidgetManager, appWidgetId)
                }
            }
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    private fun pendingIntent(
        context: Context?,
        action: String
    ): PendingIntent? {
        val intent = Intent(context, javaClass)
        intent.action = action

        return PendingIntent.getBroadcast(
            context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
        )
    }


    private fun updateAppWidget(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int
    ) {
        val perfs = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)

        // Construct the RemoteViews object
        val views = RemoteViews(context.packageName, R.layout.widget_app_inventory)

        val isEyeOpen = perfs.getBoolean("isEyeOpen", false)

        val iconResource =
            if (isEyeOpen) R.drawable.eye_off_outline else R.drawable.eye_outline
        views.setImageViewResource(R.id.eye, iconResource)

        val textResource =
            if (isEyeOpen) "326.000,00" else "* * * *"
        views.setTextViewText(R.id.dinero, textResource)

        // Create an Intent with the "changeIcon" action
        val intent = Intent(context, WidgetAppInventory::class.java)
        intent.action = "changeIcon"

        // Associate the PendingIntent with the eye icon view
        views.setOnClickPendingIntent(R.id.eye, pendingIntent(context, "changeIcon"))

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views)
    }
}



