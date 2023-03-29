package com.rizal.digitalsawitpro.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rizal.digitalsawitpro.databinding.ActivityDetailBinding
import com.rizal.digitalsawitpro.utils.Constant
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val text = intent.getStringExtra(Constant.DETAIL_TEXT)
        val distance = intent.getStringExtra(Constant.DETAIL_DISTANCE)
        val duration = intent.getStringExtra(Constant.DETAIL_DURATION)

        binding.actionBack.setOnClickListener {
            onBackPressed()
        }
        binding.distanceInput.setText(distance)
        binding.timeInput.setText(duration)
        binding.resultInput.setText(text)
    }

}