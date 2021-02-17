package com.habib.jokes.ui.signup

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.habib.jokes.network.FirebaseSource
import kotlinx.coroutines.launch
import java.lang.Exception

class SignUpViewModel(
    private val repository: FirebaseSource
    ) : ViewModel() {

     var email: String? = null
     var name : String? = null
     var password: String? = null

    private val _navigateToLogin = MutableLiveData<Boolean?>()
    private val _navigateToHome = MutableLiveData<Boolean?>()

    val navigateToLogin : LiveData<Boolean?>
           get() = _navigateToLogin
    val navigateToHome : LiveData<Boolean?>
           get() = _navigateToHome

    fun onSignUp(){
        if (email!=null){
            if (password!=null){
                viewModelScope.launch {
                    try {
                        val res = repository.register(email!!,password!!)
                        res?.let {
                            if (it.isSuccessful){
                                _navigateToHome.value = true
                            }else{ Log.d("SignUpViewModel","User not created "+res.exception?.message)}
                        }
                    }catch (ex:Exception){
                        Log.d("SignUpViewModel","User created exception "+ex.message )
                    }
                }
            }else{ Log.d("SignUpViewModel","password empty") }
        }else{ Log.d("SignUpViewModel","email empty") }
    }

    fun onLogin(){
        _navigateToLogin.value = true
    }

    fun doneNavigation(){
        _navigateToLogin.value = false
        _navigateToHome.value = false
    }
}