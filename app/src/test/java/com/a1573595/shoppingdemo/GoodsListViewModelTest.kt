package com.a1573595.shoppingdemo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.collection.SparseArrayCompat
import com.a1573595.shoppingdemo.data.Goods
import com.a1573595.shoppingdemo.page.goodsList.GoodsListViewModel
import com.a1573595.shoppingdemo.repository.GoodsRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.reactivex.rxjava3.core.Flowable
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GoodsListViewModelTest {
    // For LiveData
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val rxSchedulerRule = RxSchedulerRule()

    @MockK(relaxed = true)
    private lateinit var repository: GoodsRepository

    private lateinit var viewModel: GoodsListViewModel

    private val goodsCount = 10

    @Before
    fun setupPresenter() {
        MockKAnnotations.init(this)

        every { repository.fetchCartCount() }.returns(Flowable.just(goodsCount))

        viewModel = GoodsListViewModel(repository)
    }

    @Test
    fun fetch_goods_should_call_repository_and_show_loading() {
        viewModel.fetchGoods()

        Assert.assertEquals(true, viewModel.isLoading.value?.peekContent())
    }

    @Test
    fun fetch_goods_should_display_result() {
        val goodsArrayList: SparseArrayCompat<List<Goods>> = SparseArrayCompat()
        goodsArrayList.put(0, listOf(Goods("", "", 0, "", "")))

        every { repository.fetchGoods(any()) }.answers {
            val completion = this.arg<(SparseArrayCompat<List<Goods>>) -> Unit>(0)
            completion.invoke(goodsArrayList)
        }

        viewModel.fetchGoods()

        Assert.assertEquals(goodsArrayList, viewModel.goodsListArray.value?.peekContent())
        Assert.assertEquals(false, viewModel.isLoading.value?.peekContent())
    }

    @Test
    fun get_cart_count() {
        Assert.assertEquals(goodsCount, viewModel.count.value)
    }
}