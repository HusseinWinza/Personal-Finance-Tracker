package www.mc.com.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import www.mc.com.R

/**
 * Created by SegunFrancis
 */

fun ViewGroup.inflate(layout: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layout, this, attachToRoot)
}

fun <T> MutableLiveData<T>.asLiveData(): LiveData<T> = this

fun View.makeGone() {
    visibility = View.GONE
}

fun View.makeVisible() {
    visibility = View.VISIBLE
}

fun View.makeInvisible() {
    visibility = View.INVISIBLE
}

fun View.enable() {
    isEnabled = true
    alpha = 1f
}


fun View.disable() {
    isEnabled = false
    alpha = 0.5f
}

fun ImageView.loadImage(image: Any?) {
    Glide.with(this.context).load(image).circleCrop().placeholder(R.drawable.pic)
        .error(R.drawable.pic).into(this)
}