package com.rizal.digitalsawitpro.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.provider.MediaStore
import com.rizal.digitalsawitpro.utils.Constant.REQUEST_IMAGE_CAPTURE


object ImageUtils {

    fun Context.captureImage() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        (this as Activity).startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
    }

}
