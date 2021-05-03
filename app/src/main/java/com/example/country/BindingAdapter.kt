package com.example.country

import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYouListener

//import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
//import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYouListener
//import com.bumptech.glide.request.target.SizeReadyCallback
//import com.example.country.databasehander.CountryData
//import java.net.URL
//import com.bumptech.glide.load.Options;
//import com.bumptech.glide.load.ResourceDecoder;
//import com.bumptech.glide.load.engine.Resource;
//import com.bumptech.glide.load.resource.SimpleResource;
//import com.caverock.androidsvg.SVG;
//import com.caverock.androidsvg.SVGParseException;

@BindingAdapter("setImg")
fun setImage(imageView: ImageView,url: String?){
    url?.let {
        val imgUri = url.toUri().buildUpon().scheme("https").build()
        Log.i("hello","image uri=$imgUri")
        Glide.with(imageView.context)
                .load(imgUri)
                .apply(RequestOptions()
                        .placeholder(R.drawable.loading_animation)
                        .error(R.drawable.ic_broken_image))
                 .fitCenter()
                .into(imageView)

    }

}
//@BindingAdapter("data")
//fun bindRecyclerView(recyclerView: RecyclerView,countryData: List<CountryData>?){
//    Log.i("hello","recyclerview Binder called$countryData")
//
//        val adapter=CountryAdapter()
//        recyclerView.adapter=adapter
//        adapter.submitList(countryData)
//        Log.i("hello","recyclerview Binder called")
//
//
//}
@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    Log.i("hello"," bind image called$imgUrl")
    imgUrl?.let { val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()


        GlideToVectorYou
            .init()
            .with(imgView.context)
            .withListener(object : GlideToVectorYouListener {
                override fun onLoadFailed() {
                    Toast.makeText(imgView.context, "Loading failed", Toast.LENGTH_SHORT).show()
                }

                override fun onResourceReady() {

                }
            }).setPlaceHolder(R.drawable.loading_animation,R.drawable.ic_broken_image)
            .load(imgUri, imgView)
    }
}
