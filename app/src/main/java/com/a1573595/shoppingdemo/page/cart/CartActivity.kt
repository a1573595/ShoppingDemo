package com.a1573595.shoppingdemo.page.cart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.a1573595.shoppingdemo.BaseActivity
import com.a1573595.shoppingdemo.databinding.ActivityCartBinding

class CartActivity : BaseActivity<ActivityCartBinding, CartViewModel>() {
    companion object {
        fun newInstance(context: Context) {
            context.startActivity(Intent(context, CartActivity::class.java))
        }
    }

    private lateinit var adapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = CartAdapter(viewModel)
        viewBinding.recyclerView.adapter = adapter

        viewBinding.btnSubmit.setOnClickListener {
            viewModel.submitCart()
        }

        viewModel.carts.observe(this) {
            adapter.notifyDataSetChanged()
        }

        viewModel.hasData.observe(this) {
            viewBinding.btnSubmit.visibility = if (it) View.VISIBLE else View.INVISIBLE
            viewBinding.tvNothing.visibility = if (it) View.INVISIBLE else View.VISIBLE
        }
    }
}