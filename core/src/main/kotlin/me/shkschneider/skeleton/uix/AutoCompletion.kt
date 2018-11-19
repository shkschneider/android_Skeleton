package me.shkschneider.skeleton.uix

import android.widget.ArrayAdapter
import androidx.appcompat.widget.AppCompatAutoCompleteTextView

object AutoCompletion {

    fun threshold(autoCompleteTextView: AppCompatAutoCompleteTextView, threshold: Int) {
        autoCompleteTextView.threshold = threshold
    }

    fun suggestions(autoCompleteTextView: AppCompatAutoCompleteTextView, layoutId: Int, suggestions: List<String>) {
        val adapter = ArrayAdapter<String>(autoCompleteTextView.context, layoutId, suggestions)
        autoCompleteTextView.setAdapter(adapter)
    }

}