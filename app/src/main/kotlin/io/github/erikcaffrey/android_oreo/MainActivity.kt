/*
 * Copyright 2017 Erik Jhordan Rey
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.erikcaffrey.android_oreo

import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.support.design.widget.BottomNavigationView
import android.support.v4.provider.FontRequest
import android.support.v4.provider.FontsContractCompat
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                message.setText(R.string.title_fonts)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                message.setText(R.string.title_dashboard)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                message.setText(R.string.title_notifications)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fontRequest = FontRequest("com.google.android.gms.fonts",
                "com.google.android.gms", "Rock Salt", R.array.com_google_android_gms_fonts_certs)

        val fontCallback = object : FontsContractCompat.FontRequestCallback() {
            override fun onTypefaceRetrieved(typeface: Typeface?) {
                super.onTypefaceRetrieved(typeface)
                message.typeface = typeface
            }

            override fun onTypefaceRequestFailed(reason: Int) {
                super.onTypefaceRequestFailed(reason)
                Toast.makeText(this@MainActivity, "fails: "+ reason, Toast.LENGTH_LONG).show()
            }
        }

        val handlerThread = HandlerThread("fonts")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)

        FontsContractCompat.requestFont(this,fontRequest, fontCallback, handler)


        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }


}
