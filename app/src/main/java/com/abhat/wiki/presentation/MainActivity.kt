package com.abhat.wiki.presentation

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.util.Log
import com.abhat.wiki.R
import com.abhat.wiki.data.model.Wiki
import com.abhat.wiki.data.repository.WikiRepositoryImpl
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.Main
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val wikiRepository: WikiRepositoryImpl by inject()
    private var job = Job()
    private lateinit var wiki: Wiki
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchButton.setOnClickListener {
            if (!TextUtils.isEmpty(searchLayout.text)) {
                try {
                    job = async {
                        wiki =  wikiRepository.getWiki(searchLayout.text.toString()).await()
                        Log.d("TAG", "")
                        withContext(Dispatchers.Main) {
                            Glide.with(this@MainActivity).load(wiki.originalimage?.source).into(image)
                            wikiText.text = wiki.extract
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}
