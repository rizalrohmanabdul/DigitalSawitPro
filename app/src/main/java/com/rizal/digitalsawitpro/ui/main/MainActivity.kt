package com.rizal.digitalsawitpro.ui.main

import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.vision.Frame
import com.google.android.gms.vision.text.TextRecognizer
import com.google.maps.DirectionsApiRequest
import com.google.maps.GeoApiContext
import com.google.maps.PendingResult
import com.google.maps.model.DirectionsResult
import com.google.maps.model.TravelMode
import com.rizal.digitalsawitpro.data.model.DataResult
import com.rizal.digitalsawitpro.databinding.ActivityMainBinding
import com.rizal.digitalsawitpro.utils.Constant.DETAIL_DISTANCE
import com.rizal.digitalsawitpro.utils.Constant.DETAIL_DURATION
import com.rizal.digitalsawitpro.utils.Constant.DETAIL_TEXT
import com.rizal.digitalsawitpro.utils.Constant.REQUEST_IMAGE_CAPTURE
import com.rizal.digitalsawitpro.utils.Constant.REQUEST_LOCATION_PERMISSIONS
import com.rizal.digitalsawitpro.utils.ImageUtils.captureImage
import com.rizal.digitalsawitpro.utils.LocationUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!arePermissionsGranted()){
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    android.Manifest.permission.CAMERA,
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                REQUEST_LOCATION_PERMISSIONS
            )
        }

        binding.btnCapture.setOnClickListener {
            captureImage()
        }

        viewModel.onSave.observe(this){
            
        }

        viewModel.data.observe(this){
            goToDetail(it)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            binding.imgPreview.setImageBitmap(imageBitmap)
            onImageResult(imageBitmap)
        }
    }

    private fun onImageResult(bitmap: Bitmap) {
        val progressDialog = ProgressDialog.show(this, "", "Processing picture...", true)
        val textRecognizer = TextRecognizer.Builder(this).build()
        val frameImage = Frame.Builder().setBitmap(bitmap).build()
        val textBlockSparseArray = textRecognizer.detect(frameImage)
        var stringImageText = ""
        for (i in 0 until textBlockSparseArray.size()) {
            val textBlock = textBlockSparseArray[textBlockSparseArray.keyAt(i)]
            stringImageText = stringImageText + " " + textBlock.value
        }


        val locationUtils = LocationUtils(this)
        locationUtils.getCurrentLocation { location ->
            if (location != null) {
                val endLocation = LatLng(-6.195959, 106.820743) // Plaza Indonesia Jakarta
                estimateDistanceAndTime(location, endLocation, stringImageText, progressDialog)
            } else {
                Toast.makeText(this, "Unable to get current location", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun arePermissionsGranted(): Boolean {
        return (ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED)
    }

    fun estimateDistanceAndTime(startLocation: Location, endLocation: LatLng, text: String, pd: ProgressDialog) {
        val context = GeoApiContext.Builder()
            .apiKey(viewModel.loadGoogleApi())
            .build()
        val request = DirectionsApiRequest(context)
            .origin(com.google.maps.model.LatLng(startLocation.latitude, startLocation.longitude))
            .destination(com.google.maps.model.LatLng(endLocation.latitude, endLocation.longitude))
            .mode(TravelMode.DRIVING)

        request.setCallback(object : PendingResult.Callback<DirectionsResult> {
            override fun onResult(result: DirectionsResult) {
                val distance = result.routes[0].legs[0].distance.humanReadable
                val time = result.routes[0].legs[0].duration.humanReadable
                viewModel.save(text, distance.toString(), time.toString())

                pd.dismiss()
            }

            override fun onFailure(e: Throwable) {
                pd.dismiss()
                Log.e(MainActivity::class.java.name, "Error getting directions: " + e.message)
                Toast.makeText(this@MainActivity, "Error getting directions", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun goToDetail(data: DataResult){
        val intent = Intent(this@MainActivity, DetailActivity::class.java)
        intent.putExtra(DETAIL_TEXT, data.text)
        intent.putExtra(DETAIL_DISTANCE, data.distance)
        intent.putExtra(DETAIL_DURATION, data.time)
        startActivity(intent)
    }
}