package me.shkschneider.skeleton.demo.main

import androidx.annotation.UiThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import me.shkschneider.skeleton.demo.data.DataManager.loadModels
import me.shkschneider.skeleton.demo.data.MyModel
import me.shkschneider.skeleton.kotlinx.Coroutines

class MainViewModel : ViewModel() {

    private lateinit var models: MutableLiveData<List<MyModel>>

    @UiThread
    fun getModels(): LiveData<List<MyModel>> {
        if (!::models.isInitialized) {
            models = MutableLiveData()
            Coroutines.ioThenMain(::loadModels) {
                models.postValue(it)
            }
        }
        return models
    }

}
