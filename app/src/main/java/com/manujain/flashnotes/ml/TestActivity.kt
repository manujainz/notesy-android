package com.manujain.flashnotes.ml

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.manujain.flashnotes.R
import com.manujain.flashnotes.databinding.TestActivityMainBinding

class TestActivity : AppCompatActivity() {
    private val gpt2: com.manujain.flashnotes.ml.GPT2Client by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: TestActivityMainBinding
                = DataBindingUtil.setContentView(this, R.layout.test_activity_main)

        // Bind layout with ViewModel
        binding.vm = gpt2

        // LiveData needs the lifecycle owner
        binding.lifecycleOwner = this
    }
}
