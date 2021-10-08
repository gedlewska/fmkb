package com.example.fmkb.viewModel

import androidx.lifecycle.*
import com.example.fmkb.util.Order
import com.example.fmkb.util.OrderRepository
import kotlinx.coroutines.*

class OrderViewModel(private val repository: OrderRepository) : ViewModel() {

    val url = MutableLiveData<String>()
    val errorMessage = MutableLiveData<String>()
    val orderList = MutableLiveData<List<Order>>()
    var job: Job? = null
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }
    val loading = MutableLiveData<Boolean>()

    fun refreshOrders() {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = repository.refreshOrders()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    orderList.postValue(response.body()!!)
                    loading.value = false
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }
    }

    private fun onError(message: String) {
        errorMessage.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

    val allOrders: LiveData<List<Order>> = repository.allOrders.asLiveData()

    fun insertAllOrders(orders: List<Order?>?) = viewModelScope.launch {
        repository.deleteAllOrders()
        repository.insertAllOrders(orders)
    }

    fun setUrl(string: String) {
        url.value = string
    }

}