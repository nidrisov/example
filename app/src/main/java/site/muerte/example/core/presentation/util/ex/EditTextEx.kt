package site.muerte.example.core.presentation.util.ex

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import site.muerte.example.R

fun EditText.validateEmail(context: Context) {
    val text = this.text.toString()
    if (text.isNotBlank()) {
        if (!text.isValidEmail()) {
            this.error = context.resources.getString(R.string.incorrect_email)
        }
    }
}

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }
    })
}