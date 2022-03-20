package com.bipulhstu.inappreview

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.ReviewManagerFactory
import com.google.android.play.core.review.testing.FakeReviewManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        findViewById<TextView>(R.id.hello_world).setOnClickListener {
            Log.d("check", "working")
            rateus()
        }
    }


    private fun rateus() {
        //val manager = ReviewManagerFactory.create(applicationContext)
        val manager = FakeReviewManager(this)
        val request = manager.requestReviewFlow()
        var reviewInfo: ReviewInfo? = null
        request.addOnCompleteListener { request ->
            if (request.isSuccessful) {
                // We got the ReviewInfo object
                reviewInfo = request.result
                val flow = manager.launchReviewFlow(this, request.result)
                flow.addOnCompleteListener {
                    // The flow has finished. The API does not indicate whether the user
                    // reviewed or not, or even whether the review dialog was shown. Thus, no
                    // matter the result, we continue our app flow.
                }
            } else {
                // There was some problem, continue regardless of the result.
            }
        }
    }
}