package com.fdev.epars.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import com.fdev.epars.R

import com.fdev.epars.entity.AuthorizeFormModel
import com.fdev.epars.network.DaggerNetworkComponent
import com.fdev.epars.network.NetworkComponent
import com.google.android.material.textfield.TextInputLayout


class LoginActivity : AppCompatActivity() {

    private val injComponent: NetworkComponent by lazy { DaggerNetworkComponent.create() }

    private val loginInput by lazy { findViewById<TextInputLayout>(R.id.login_input_layout) }
    private val passwordInput by lazy { findViewById<TextInputLayout>(R.id.password_input_layout) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    /**
     * Login button onClick listener
     */
    fun onLoginButtonClick(view: View){

        injComponent.providesService().authorizationPost(
            AuthorizeFormModel(
                userName = loginInput.editText?.text.toString(),
                password = passwordInput.editText?.text.toString()
            )
        )
    }
}
