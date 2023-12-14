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
import com.cristian.miniproyecto2.model.Articulo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.NumberFormat
import java.util.Locale


/**
 * Implementation of App Widget functionality.
 */
class WidgetAppInventory : AppWidgetProvider() {
    private lateinit var sharedPreferences: SharedPreferences
    private val db = FirebaseFirestore.getInstance()


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
        val firebaseAuth = FirebaseAuth.getInstance()

        if (context != null && action == "changeIcon") {
            val perfs = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
            val editor = perfs.edit()

            if (firebaseAuth.currentUser == null) {
                val intent = Intent(context, LoginActivity::class.java)
                intent.putExtra("launchedFromWidget", true)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
            } else {
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
        } else if (context != null && action == "gestionarInventario") {
            if (firebaseAuth.currentUser == null) {
                val intent = Intent(context, LoginActivity::class.java)
//                intent.putExtra("launchedFromWidget", true)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
            } else {
                val intent = Intent(context, InventarioActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
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

    private fun sumaPrecios(callback: (Double) -> Unit) {
        var articulos = mutableListOf<Articulo>()
        var suma = 0.0
        db.collection("articulo").get().addOnSuccessListener {
            for (document in it.documents) {
                val articulo = Articulo(
                    id = document.get("id") as Long,
                    name = document.get("name") as String,
                    price = document.get("price") as Double,
                    quantity = document.get("quantity") as Long
                )
                articulos.add(articulo)
                suma += articulo.price * articulo.quantity.toDouble()
            }
            callback(suma)  // Pass the sum to the callback function
        }
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

//        val iconResource =
//            if (isEyeOpen) R.drawable.eye_off_outline else R.drawable.eye_outline


        sumaPrecios { sum ->
            var currency = NumberFormat.getNumberInstance(Locale("es", "ES")).format(sum)
            val textResource: String
            val iconResource: Int
            if (isEyeOpen) {
                    textResource = "$ "+ "$currency" + ",00"
                    iconResource = R.drawable.eye_off_outline
                } else {
                    textResource = "$ * * * *"
                    iconResource = R.drawable.eye_outline
                }
            views.setTextViewText(R.id.dinero, textResource)
            views.setImageViewResource(R.id.eye, iconResource)
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }

        // Create an Intent with the "changeIcon" action
        val intent = Intent(context, WidgetAppInventory::class.java)
        intent.action = "changeIcon"
        intent.action = "gestionarInventario"

        views.setOnClickPendingIntent(R.id.eye, pendingIntent(context, "changeIcon"))
        views.setOnClickPendingIntent(R.id.tuerca, pendingIntent(context, "gestionarInventario"))

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views)
    }
}



