package com.a1573595.shoppingdemo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.a1573595.shoppingdemo.api.SubmitRes
import com.a1573595.shoppingdemo.data.Goods
import com.a1573595.shoppingdemo.database.Cart
import com.a1573595.shoppingdemo.page.cart.CartViewModel
import com.a1573595.shoppingdemo.repository.*
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * 窘境：牽扯到Room的資料更新，造成無法驗證資料是否刪除。
 */
class CartViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val rxSchedulerRule = RxSchedulerRule()

    @MockK(relaxed = true)
    private lateinit var goodsRepository: IGoodsRepository

    @MockK(relaxed = true)
    private lateinit var shoppingRepository: IShoppingRepository

    private lateinit var viewModel: CartViewModel

    private var carts: List<Cart> = listOf(
        Cart(Goods("0001", "Apple", 10, "", ""), 1),
        Cart(Goods("0002", "Banana", 15, "", ""), 2),
        Cart(Goods("0003", "Orange", 23, "", ""), 3)
    )

    @Before
    fun setupPresenter() {
        MockKAnnotations.init(this)

        every { goodsRepository.fetchCart() }.returns(Flowable.just(carts))

        viewModel = CartViewModel(goodsRepository, shoppingRepository)
    }

    @Test
    fun reduceQuantity() {
        val count: Int = viewModel.carts.value?.get(1)?.quantity!! - 1
        every { goodsRepository.updateGoods(any()) }.returns(Completable.complete())

        viewModel.reduceQuantity(1)
        Assert.assertEquals(count, viewModel.carts.value?.get(1)?.quantity)
    }

    @Test
    fun increaseQuantity() {
        val count: Int = viewModel.carts.value?.get(1)?.quantity!! + 1
        every { goodsRepository.updateGoods(any()) }.returns(Completable.complete())

        viewModel.increaseQuantity(1)
        Assert.assertEquals(count, viewModel.carts.value?.get(1)?.quantity)
    }

    @Test
    fun submitCart() {
        // 送出失敗
        every { shoppingRepository.buy(eq(carts)) }.returns(Single.just(SubmitRes(false)))
        every { goodsRepository.deleteAllGoods() }.returns(Completable.complete())

        viewModel.submitCart()
        viewModel.hasData.observeForTesting {
            Assert.assertEquals(true, viewModel.hasData.value)
        }

        Assert.assertEquals(R.string.server_error, viewModel.toastResource.value?.peekContent())

        // 送出成功
        every { shoppingRepository.buy(eq(carts)) }.returns(Single.just(SubmitRes(true)))
        every { goodsRepository.deleteAllGoods() }.returns(Completable.complete())

        viewModel.submitCart()
        viewModel.carts.postValue(emptyList()) // 模擬資料被刪除

        viewModel.hasData.observeForTesting {
            Assert.assertEquals(false, viewModel.hasData.value)
        }
        Assert.assertEquals(false, viewModel.isLoading.value?.peekContent())
    }

    // For liveData Transformations
    private fun <T> LiveData<T>.observeForTesting(block: () -> Unit) {
        val observer = Observer<T> { }
        try {
            observeForever(observer)
            block()
        } finally {
            removeObserver(observer)
        }
    }
}