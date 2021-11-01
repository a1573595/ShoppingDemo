package com.a1573595.shoppingdemo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.a1573595.shoppingdemo.api.LoginRes
import com.a1573595.shoppingdemo.page.login.LoginViewModel
import com.a1573595.shoppingdemo.repository.IMemberRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import io.reactivex.rxjava3.core.Single
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LoginViewModelTest {
    // For LiveData
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val rxSchedulerRule = RxSchedulerRule()

    private val account = "account"
    private val password = "password"

    @MockK(relaxed = true)
    private lateinit var repository: IMemberRepository

    private lateinit var viewModel: LoginViewModel

    @Before
    fun setupPresenter() {
        MockKAnnotations.init(this)

        viewModel = LoginViewModel(repository)
    }

    @Test
    fun login_without_account_and_password() {
        viewModel.login("", "")

        Assert.assertEquals(
            R.string.account_cannot_be_empty,
            viewModel.accountError.value?.peekContent()
        )
        Assert.assertEquals(
            R.string.account_cannot_be_empty,
            viewModel.toastResource.value?.peekContent()
        )

        viewModel.login(account, "")

        Assert.assertEquals(
            R.string.password_cannot_be_empty,
            viewModel.passwordError.value?.peekContent()
        )
        Assert.assertEquals(
            R.string.password_cannot_be_empty,
            viewModel.toastResource.value?.peekContent()
        )
    }

    @Test
    fun login_should_call_repository_and_show_loading() {
        viewModel.login(account, password)

        verify { repository.login(eq(account), eq(password)) }

        Assert.assertEquals(true, viewModel.isLoading.value?.peekContent())
    }

    @Test
    fun login_should_display_result() {
        // 模擬登入失敗
        every { repository.login(account, password) }.returns(Single.just(LoginRes(false, "", "")))

        viewModel.login(account, password)

        Assert.assertEquals(false, viewModel.isLoading.value?.peekContent())
        Assert.assertEquals(R.string.login_failed, viewModel.toastResource.value?.peekContent())

        // 模擬登入成功
        every { repository.login(account, password) }.returns(Single.just(LoginRes(true, "", "")))

        viewModel.login(account, password)

        Assert.assertEquals(false, viewModel.isLoading.value?.peekContent())
        Assert.assertEquals(R.string.login_success, viewModel.toastResource.value?.peekContent())
        viewModel.loginSuccess.observeForTesting {
            Assert.assertEquals(true, viewModel.loginSuccess.value)
        }
    }
}