package com.dicodingsm.ghus.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicodingsm.ghus.data.response.Items
import com.dicodingsm.ghus.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    private val _listUser = MutableLiveData<List<Items>>()
    val listUser : LiveData<List<Items>> = _listUser

    init {
        getListUser()
    }
    private fun getListUser() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getListUsers()
        client.enqueue(object : Callback<List<Items>> {
            override fun onResponse(call: Call<List<Items>>, response: Response<List<Items>>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listUser.value = response.body()
                } else {
                    _listUser.value = listOf()
                    Log.d("TAG", "onResponse: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<Items>>, t: Throwable) {
                _isLoading.value = false
                Log.d("TAG", "onResponse: ${t.message}")

            }
        })
    }
}