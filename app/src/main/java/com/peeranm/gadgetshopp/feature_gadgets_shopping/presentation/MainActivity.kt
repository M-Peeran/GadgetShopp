package com.peeranm.gadgetshopp.feature_gadgets_shopping.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.peeranm.gadgetshopp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
    }
}