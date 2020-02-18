package com.example.yetanotherfeed.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.yetanotherfeed.models.RssObject
import com.example.yetanotherfeed.network.YetAnotherFeedNetwork
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

class OverviewViewModel : ViewModel() {

    private val _rssObject = MutableLiveData<RssObject?>()
    val rssObject: LiveData<RssObject?>
        get() = _rssObject


    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        getRssFeedObject("http://feeds.bbci.co.uk/news/rss.xml")
    }

    private fun getRssFeedObject(filter: String) {
        coroutineScope.launch {
            val getRssObjectDeferred = YetAnotherFeedNetwork.feeds.getFeeds(filter)
            try {
                val objectResult = getRssObjectDeferred.await()
                _rssObject.value = objectResult
            } catch (e: Exception) {
                _rssObject.value = null
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}