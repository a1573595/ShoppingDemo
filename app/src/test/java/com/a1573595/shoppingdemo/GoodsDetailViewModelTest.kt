package com.a1573595.shoppingdemo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.a1573595.shoppingdemo.data.Goods
import com.a1573595.shoppingdemo.page.goodsDetail.GoodsDetailViewModel
import com.a1573595.shoppingdemo.repository.GoodsRepository
import com.a1573595.shoppingdemo.tool.Event
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GoodsDetailViewModelTest {
    // For LiveData
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val rxSchedulerRule = RxSchedulerRule()

    @MockK(relaxed = true)
    private lateinit var repository: GoodsRepository

    private lateinit var viewModel: GoodsDetailViewModel

    private val goodsCount = 10

    @Before
    fun setupPresenter() {
        MockKAnnotations.init(this)

        every { repository.fetchCartCount() }.returns(Flowable.just(goodsCount))

        viewModel = GoodsDetailViewModel(repository)
    }

    @Test
    fun add_goods_to_cart() {
        var complete = false

        viewModel.goods = MutableLiveData(Event(Goods("", "", 0, "", "")))
        every { repository.addGoodsToCart(any(), any()) }.returns(Completable.complete())

        viewModel.addGoodsToCart(10) {
            complete = true
        }

        Assert.assertTrue(complete)
    }

    @Test
    fun get_cart_count() {
        Assert.assertEquals(goodsCount, viewModel.count.value)
    }
}