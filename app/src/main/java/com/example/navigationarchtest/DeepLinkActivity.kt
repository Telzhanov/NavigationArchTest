package com.example.navigationarchtest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class DeepLinkActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deep_link)

        if (intent.data != null) {
            val newIntent = Intent(this, MainActivity::class.java)
            newIntent.putExtra("deeplink", intent.data.toString())
            startActivity(newIntent)
            finish()
        }
    }
}
