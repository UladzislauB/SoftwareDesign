package com.example.yetanotherfeed.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.yetanotherfeed.database.getDatabase
import com.example.yetanotherfeed.network.YetAnotherFeedNetwork
import com.example.yetanotherfeed.repository.ItemsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Retrofit
import java.io.IOException
import java.lang.Exception


enum class LoadingStatus { LOADING, ERROR, DONE }

class OverviewViewModel(application: Application) : ViewModel() {

    private var _eventNetworkError = MutableLiveData<Boolean>(false)
    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError


    private val _status = MutableLiveData<LoadingStatus>()
    val status: LiveData<LoadingStatus>
        get() = _status

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)


    private val itemsRepository = ItemsRepository(getDatabase(application))

    val items = itemsRepository.items


    init {
//        refreshDataFromRepository("https://news.tut.by/rss/index.rss")
//        refreshDataFromRepository("https://news.tut.by/rss/economics.rss")
        refreshDataFromRepository("http://feeds.bbci.co.uk/news/rss.xml")
//        refreshDataFromRepository("https://news.tut.by/rss.html")
    }

    private fun refreshDataFromRepository(filter: String) {
        coroutineScope.launch {
            val getRssObjectDeferred = YetAnotherFeedNetwork.feeds.getFeeds(filter)
            try {
                _status.value = LoadingStatus.LOADING
                val objectResult = getRssObjectDeferred.await()
                _status.value = LoadingStatus.DONE
                itemsRepository.refreshVideos(objectResult.items)
            } catch (e: HttpException) {
                _status.value = LoadingStatus.ERROR
                _eventNetworkError.value = true
            } catch (e: Exception) {
                _status.value = LoadingStatus.ERROR
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }


    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(OverviewViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return OverviewViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}