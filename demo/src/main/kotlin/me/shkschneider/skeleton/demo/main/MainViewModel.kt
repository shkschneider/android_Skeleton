package me.shkschneider.skeleton.demo.main

import androidx.annotation.UiThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import me.shkschneider.skeleton.demo.data.DataManager
import me.shkschneider.skeleton.demo.data.MyModel
import me.shkschneider.skeleton.kotlinx.Coroutines

/**
 * This is, so far, my favorite way of doing it.
 *
 * @see me.shkschneider.skeleton.SkeletonViewModel
 */
class MainViewModel : ViewModel() {

    private val models by lazy {
        MutableLiveData<List<MyModel>>().apply {
            // ...
        }
    }

    @UiThread
    fun models(force: Boolean = false): LiveData<List<MyModel>> {
        if (models.value == null || force) {
            Coroutines.ioThenMain({
                DataManager.getModels()
            }) {
                models.postValue(it)
            }
        }
        return models
    }

}
