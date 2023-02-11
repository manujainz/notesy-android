package com.manujain.notesy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.manujain.notesy.core.presentation.WindowFullScreenActivator
import com.manujain.notesy.core.presentation.WindowFullScreenActivatorImpl
import com.manujain.notesy.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), WindowFullScreenActivator by WindowFullScreenActivatorImpl() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        registerWindowWithLifecycle(this, window)
        setContentView(binding.root)
    }
}
