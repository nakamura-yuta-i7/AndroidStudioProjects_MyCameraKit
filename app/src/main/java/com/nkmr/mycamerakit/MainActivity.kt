package com.nkmr.mycamerakit

import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : FragmentActivity() {

    companion object {
        var pictureBitmap: Bitmap? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        goCameraButton.setOnClickListener {
            startActivity(Intent(this, CameraActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()

        val tmpDir = ShattoTmpImgsDir(this)
        Log.d("tmpDir.getFiles()", tmpDir.getFiles().map { it.absolutePath }.toString())

        tmpDir.getFiles().forEach { file ->
            var bitmap = BitmapFactory.decodeFile(file.absolutePath)
            imageView.setImageBitmap(bitmap)
        }
    }
}
