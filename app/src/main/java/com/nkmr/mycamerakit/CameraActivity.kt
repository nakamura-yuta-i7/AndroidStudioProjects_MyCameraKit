package com.nkmr.mycamerakit

import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.util.Log
import com.wonderkiln.camerakit.CameraKit
import com.wonderkiln.camerakit.CameraListener
import kotlinx.android.synthetic.main.activity_camera.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async

class CameraActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        Log.d("koko1", "!!!!!!!!!!!!!!!!!!!")

        requestedOrientation = resources.configuration.orientation

        cameraButton.setOnClickListener {

            async(UI) {
                cameraView.captureImage()
            }
        }

        //cameraView.setMethod(CameraKit.Constants.METHOD_STILL)
        cameraView.setJpegQuality(80)
        cameraView.setPermissions(CameraKit.Constants.PERMISSIONS_PICTURE)
        cameraView.setCameraListener(object : CameraListener() {
            override fun onPictureTaken(jpeg: ByteArray?) {
                super.onPictureTaken(jpeg)

                if (jpeg!=null) {
                    Log.d("jpeg.size", jpeg.size.toString())
                    val bitmap = BitmapFactory.decodeByteArray(jpeg, 0, jpeg.size)

                    val tmpDir = ShattoTmpImgsDir(applicationContext)
                    tmpDir.createPngFromBitmap(filename = "test.png", bitmap = bitmap)
                }
                finish()
            }
        })

        closeButton.setOnClickListener { finish() }
    }

    override fun onResume() {
        super.onResume()

        Log.d("onResume", "!!!!!!!!!!!!!!!!!!!")

        async(UI) {
            cameraView.start()
        }
    }

    override fun onPause() {

        async(UI) {
            cameraView.stop()
        }

        Log.d("onPause", "!!!!!!!!!!!!!!!!!!!")
        super.onPause()
    }
}
