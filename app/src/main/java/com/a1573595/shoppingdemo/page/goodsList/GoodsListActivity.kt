package com.a1573595.shoppingdemo.page.goodsList

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.a1573595.shoppingdemo.BaseActivity
import com.a1573595.shoppingdemo.R
import com.a1573595.shoppingdemo.databinding.ActivityGoodsListBinding
import com.a1573595.shoppingdemo.page.goodsDetail.GoodsDetailActivity
import com.a1573595.shoppingdemo.page.cart.CartActivity
import com.a1573595.shoppingdemo.tool.EventObserver

/**
 * 顯示商品目錄
 */
class GoodsListActivity : BaseActivity<ActivityGoodsListBinding, GoodsListViewModel>() {
    companion object {
        fun newInstance(context: Context) {
            context.startActivity(Intent(context, GoodsListActivity::class.java))
        }
    }

    private lateinit var adapter: GoodsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("test", "onCreate")

        viewBinding.recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = GoodsListAdapter {
            GoodsDetailActivity.newInstance(this, it)
        }
        viewBinding.recyclerView.adapter = adapter

        viewModel.goodsListArray.observe(this, EventObserver {
            adapter.updateGoods(it)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_layout, menu)
        val menuItem = menu!!.findItem(R.id.itemCart)

        val cartBadge = menuItem.actionView.findViewById(R.id.cart_badge) as TextView
        menuItem.actionView.setOnClickListener {
            CartActivity.newInstance(this)
        }

        viewModel.count.observe(this) {
            cartBadge.text = "$it"
        }

        return true
    }
}