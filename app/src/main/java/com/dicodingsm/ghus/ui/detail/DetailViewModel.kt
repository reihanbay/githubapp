package com.dicodingsm.ghus.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicodingsm.ghus.data.response.DetailUserResponse
import com.dicodingsm.ghus.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    private val _user = MutableLiveData<DetailUserResponse>()
    val user : LiveData<DetailUserResponse> = _user

    private val _message = MutableLiveData<String>()
    val message : LiveData<String> = _message

    fun getDetailUser(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().detailUser(username)
        client.enqueue(object : Callback<DetailUserResponse>{
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _user.value = response.body()
                } else {
                    _message.value = response.message()
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                _message.value = t.message
            }
        })
    }
}