package com.rizal.digitalsawitpro.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.rizal.digitalsawitpro.databinding.ActivitySplashBinding
import com.rizal.digitalsawitpro.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity: AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val viewModel: SplashViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.loadSplash()
        viewModel.load.observe(this){
            if (it){
                goToMain()
            }
        }
    }


    private fun goToMain(){
        val intent= Intent(this@SplashActivity, MainActivity::class.java)
        startActivity(intent).also { finish() }
    }
}