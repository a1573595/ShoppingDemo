package com.a1573595.shoppingdemo.page.login

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.a1573595.shoppingdemo.BaseActivity
import com.a1573595.shoppingdemo.databinding.ActivityLoginBinding
import com.a1573595.shoppingdemo.page.goodsList.GoodsListActivity
import com.a1573595.shoppingdemo.tool.EventObserver

/**
 * 使用者登入
 */
class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.accountError.observe(this, EventObserver {
            viewBinding.edAccount.error = if (it == null) it else getString(it)
        })

        viewModel.passwordError.observe(this, EventObserver {
            viewBinding.edPassword.error = if (it == null) it else getString(it)
        })

        viewModel.loginSuccess.observe(this) {
            if (it) {
                GoodsListActivity.newInstance(this)
            }

            viewBinding.btnLogin.isEnabled = true
        }

        viewBinding.btnLogin.setOnClickListener {
            viewBinding.btnLogin.isEnabled = false

            it.hideKeyboard()

            val loginId = viewBinding.edAccount.text.toString()
            val password = viewBinding.edPassword.text.toString()

            viewModel.login(loginId, password)
        }
    }

    override fun onBackPressed() {
        finishAfterTransition()
    }

    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}