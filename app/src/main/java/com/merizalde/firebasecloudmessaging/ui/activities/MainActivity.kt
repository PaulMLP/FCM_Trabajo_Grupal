package com.merizalde.firebasecloudmessaging.ui.activities

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.merizalde.firebasecloudmessaging.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("TOKEN_FB", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            val token = task.result

            token.toString()
            Log.d("TOKEN_FB", token)
            Toast.makeText(baseContext,"El token es: $token", Toast.LENGTH_SHORT).show()
        })
    }

}