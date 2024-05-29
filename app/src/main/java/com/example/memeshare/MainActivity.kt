package com.example.memeshare

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    var current_Meme :String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadMeme()
    }

    private fun loadMeme(){
        // Instantiate the RequestQueue.
        // for progress bar
        val probgb = findViewById<ProgressBar>(R.id.progressBar)
        probgb.visibility = View.VISIBLE
        val queue = Volley.newRequestQueue(this)
        val url = "https://meme-api.com/gimme"
        val memeImageView = findViewById<ImageView>(R.id.memeImageView)
        // Request a string response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,url, null,
            { response ->
                current_Meme = response.getString("url")
                Glide.with(this).load(current_Meme).listener(object :RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>,
                        isFirstResource: Boolean
                    ): Boolean {
                        probgb.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable,
                        model: Any,
                        target: Target<Drawable>?,
                        dataSource: DataSource,
                        isFirstResource: Boolean
                    ): Boolean {
                        probgb.visibility = View.GONE
                        return false
                    }
                }).into(memeImageView)

            },
            { Toast.makeText(this,"Somethng went wrong",Toast.LENGTH_LONG).show() })// Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)
    }
    fun shareMeme(view: View) {
        // Share button In Meme APP
        val intent= Intent(Intent.ACTION_SEND)
        intent.type="text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"hey ! checkout this cool Meme $current_Meme")
        val choose = Intent.createChooser(intent,"Share this using - ")
        startActivity(choose)
    }
    fun next(view: View) { loadMeme()  }
}