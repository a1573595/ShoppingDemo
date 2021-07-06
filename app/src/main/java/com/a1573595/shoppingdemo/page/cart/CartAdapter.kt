package com.a1573595.shoppingdemo.page.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.a1573595.shoppingdemo.database.Cart
import com.a1573595.shoppingdemo.databinding.ItemCartBinding
import com.a1573595.shoppingdemo.tool.GlideApp

class CartAdapter(private val viewModel: CartViewModel) :
    RecyclerView.Adapter<CartAdapter.CartViewHolder>() {
    // 會取不到數值
//    private val cartList: List<Cart>? = viewModel.carts.value

    inner class CartViewHolder(private val binding: ItemCartBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.tvReduce.setOnClickListener {
                viewModel.reduceQuantity(absoluteAdapterPosition)
            }

            binding.tvIncrease.setOnClickListener {
                viewModel.increaseQuantity(absoluteAdapterPosition)
            }
        }

        fun bind(cart: Cart) {
            binding.tvName.text = cart.goods.name

            GlideApp.with(binding.root.context)
                .load(cart.goods.imgUrl)
                .fitCenter()
                .into(binding.imgGoods)

            binding.tvQuantity.text = "${cart.quantity}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemCartBinding.inflate(layoutInflater, parent, false)

        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        viewModel.carts.value?.get(position)?.let {
            holder.bind(it)
        }
    }

    override fun getItemCount(): Int = viewModel.carts.value?.size ?: 0
}