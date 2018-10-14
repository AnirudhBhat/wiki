package com.abhat.wiki.presentation

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import com.abhat.wiki.R
import com.abhat.wiki.data.model.Wiki
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import android.view.animation.DecelerateInterpolator
import android.view.animation.AlphaAnimation
import android.view.animation.Animation



class MainActivity : AppCompatActivity() {

    private val wikiViewModelFactory: WikiViewModelFactory by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchButton.setOnClickListener {
            if (!TextUtils.isEmpty(searchLayout.text)) {
                val wikiViewModel = ViewModelProviders.of(this, wikiViewModelFactory).get(WikiViewModel::class.java)
                wikiViewModel.getWikiResult().observe(this, Observer {
                    processResponse(it)
                })
                wikiViewModel.getWikiResult().value = null
                wikiViewModel.fetchWiki(wikiViewModel.cleanSearchTerm(searchLayout.text.toString()), image)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putCharSequence("wikitext", wikiText.text)
    }


    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        wikiText.text = savedInstanceState?.getCharSequence("wikitext")
    }


    private fun processResponse(wiki: Wiki?) {
        val fadeIn = AlphaAnimation(0f, 1f)
        fadeIn.interpolator = DecelerateInterpolator()
        fadeIn.duration = 1000
        wikiText.animation = fadeIn
        wikiText.text = wiki?.extract
        Glide.with(this).load(wiki?.originalimage?.source).into(image)
    }
}
