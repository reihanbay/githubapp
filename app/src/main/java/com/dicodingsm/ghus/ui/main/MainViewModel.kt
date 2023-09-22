package com.dicodingsm.ghus.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicodingsm.ghus.data.response.Items
import com.dicodingsm.ghus.data.response.SearchResponse
import com.dicodingsm.ghus.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    private val _listUser = MutableLiveData<List<Items>>()
    val listUser : LiveData<List<Items>> = _listUser

    private val _message = MutableLiveData<String>()
    val message : LiveData<String> = _message
    init {
        getListUser()
    }
    fun getListUser() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getListUsers()
        client.enqueue(object : Callback<List<Items>> {
            override fun onResponse(call: Call<List<Items>>, response: Response<List<Items>>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listUser.value = response.body()
                } else {
                    _listUser.value = listOf()
                    _message.value = response.message()
                    Log.d("TAG", "onResponse: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<Items>>, t: Throwable) {
                _isLoading.value = false
                _message.value = t.message


            }
        })
    }

    fun searchListUser(query: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().searchUser(query)
        client.enqueue(object : Callback<SearchResponse> {
            override fun onResponse(call: Call<SearchResponse>, response: Response<SearchResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listUser.value = response.body()?.items
                } else {
                    _listUser.value = listOf()
                    _message.value = response.message()
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                _isLoading.value = false
                _message.value = t.message
            }
        })
    }
}