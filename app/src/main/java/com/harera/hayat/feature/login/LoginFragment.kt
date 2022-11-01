package com.harera.hayat.feature.login

import android.graphics.Color
import android.os.Bundle
import android.os.Parcel
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getColor
import androidx.fragment.app.viewModels
import com.harera.hayat.R
import com.harera.hayat.common.BaseFragment
import com.harera.hayat.common.afterTextChanged
import com.harera.hayat.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment() {

    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var binding: FragmentLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSignupSpannable()
        setupListeners()
        setupObservers()
    }

    private fun setupObservers() {

    }

    private fun setupListeners() {
        binding.username.afterTextChanged {
            loginViewModel.setUsername(it)
        }

        binding.password.afterTextChanged {
            loginViewModel.setPassword(it)
        }

        binding.loginButton.setOnClickListener {
            checkFormState()
        }
    }

    private fun checkFormState() {
        val loginFormState = loginViewModel.loginFormState.value ?: return
        if (loginFormState.isValid) {
            return loginViewModel.login()
        }

        if (loginFormState.usernameError != null) {
            binding.usernameTL.error = getString(loginFormState.usernameError)
        } else if(loginFormState.passwordError != null) {
            binding.passwordTL.error = getString(loginFormState.passwordError)
        }
    }

    private fun setupSignupSpannable() {
        val notHaveAccount = getString(R.string.not_have_account).plus(" ")
        val signup = getString(R.string.signup)

        val spannable: Spannable = SpannableString(notHaveAccount.plus(signup))
        spannable.setSpan(
            ForegroundColorSpan(Color.BLACK),
            0,
            notHaveAccount.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannable.setSpan(
            ForegroundColorSpan(getColor(requireContext(), R.color.colorPrimary)),
            notHaveAccount.length,
            signup.length + notHaveAccount.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        binding.signup.text = spannable
    }
}