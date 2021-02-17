package com.habib.jokes.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.habib.jokes.network.FirebaseSource
import com.habib.jokes.network.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: FirebaseSource
) : ViewModel() {

    var email: String? = null
    var password: String? = null

    private val _navigateToSignUp = MutableLiveData<Boolean?>()
    private val _navigateToHome = MutableLiveData<Boolean?>()

    val navigateToSignUp: LiveData<Boolean?>
        get() = _navigateToSignUp

    val navigateToHome: LiveData<Boolean?>
        get() = _navigateToHome


    fun onCreateAccount() {
        _navigateToSignUp.value = true
    }

    fun doneNavigateToSignUp() {
        _navigateToSignUp.value = false
    }

    fun doneNavigateToHome() {
        _navigateToHome.value = false
    }

    fun onLogin() {
        Log.d("asdasdsad","on Login")
        email?.let { email ->
            if (email.isEmpty()) {
                Log.d("asdasdsad","Empty email")
                return
            }

            password?.let { pass ->
                if (pass.isEmpty()) {
                    Log.d("asdasdsad","Empty pass")
                    return
                }
                viewModelScope.launch {
                    try {
                        val res = repository.login(email, pass)
                        res?.let {
                            if (it.isSuccessful) {
                                Log.d("asdasdsad","success")
                                _navigateToHome.value = true
                            }else{
                                Log.d("asdasdsad","No success "+it.exception?.message)
                            }
                        }
                    } catch (ex: Exception) {
                        Log.d("asdasdsad","task not success "+ex.message)
                    }
                }
            }
        }
    }
}