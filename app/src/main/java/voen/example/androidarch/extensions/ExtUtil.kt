package voen.example.androidarch.extensions

import android.content.Context
import android.widget.Toast

/**
 * Created by voen on 01/05/18.
 */
fun Context.toast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}