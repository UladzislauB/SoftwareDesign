package com.example.calculator

data class Page(val expression: String, val result: String)

object HistorySingleton {
    var pages = ArrayList<Page>()
}