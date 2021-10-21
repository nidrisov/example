package site.muerte.example.core.presentation.util.ex

import android.os.Build
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment

fun Fragment.onBackPressedDispatcher() {
    requireActivity().onBackPressedDispatcher.addCallback(this) {
        if (Build.VERSION.SDK_INT >= 21)
            activity?.finishAndRemoveTask()
        else
            activity?.finish()
    }
}

fun Fragment.toast(text: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this.context, text, length).show()
}