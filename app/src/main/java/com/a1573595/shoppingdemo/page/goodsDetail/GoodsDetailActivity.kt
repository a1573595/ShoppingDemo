package com.a1573595.shoppingdemo.page.goodsDetail

import android.content.Context
import android.content.Intent
import android.graphics.Point
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.a1573595.shoppingdemo.BaseActivity
import com.a1573595.shoppingdemo.R
import com.a1573595.shoppingdemo.customView.CartAnimationView
import com.a1573595.shoppingdemo.data.Goods
import com.a1573595.shoppingdemo.databinding.ActivityGoodsDetailBinding
import com.a1573595.shoppingdemo.dialog.SelectQuantityBottomSheetDialog
import com.a1573595.shoppingdemo.page.cart.CartActivity
import com.a1573595.shoppingdemo.tool.Event
import com.a1573595.shoppingdemo.tool.EventObserver
import com.a1573595.shoppingdemo.tool.GlideApp

/**
 * 商品詳細資訊
 */
class GoodsDetailActivity : BaseActivity<ActivityGoodsDetailBinding, GoodsDetailViewModel>() {
    companion object {
        private const val KEY_GOODS = "GOODS"

        fun newInstance(context: Context, goods: Goods) {
            val intent = Intent(context, GoodsDetailActivity::class.java)
            intent.putExtra(KEY_GOODS, goods)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (intent?.getSerializableExtra(KEY_GOODS) as? Goods)?.let {
            viewModel.goods.postValue(Event(it))
        }

        viewModel.goods.observe(this, EventObserver {
            GlideApp.with(this)
                .load(it.imgUrl)
                .fitCenter()
                .into(viewBinding.imgGoods)

            viewBinding.tvName.text = it.name
            viewBinding.tvPrice.text = "\$${it.price}"
        })

        viewBinding.btnBuy.setOnClickListener {
            val position = IntArray(2)
            it.getLocationInWindow(position)
            val startPoint = Point(position[0], position[1])

            val endPosition = IntArray(2)
            findViewById<View>(R.id.itemCart).getLocationOnScreen(endPosition)
            val endPoint = Point(endPosition[0], endPosition[1])

            SelectQuantityBottomSheetDialog(viewModel.goods.value?.peekContent()!!) {
                viewModel.addGoodsToCart(it) {
                    val cartAnimView = CartAnimationView(this)
                    cartAnimView.setImageResource(R.drawable.ic_shopping_cart)
                    cartAnimView.layoutParams = LinearLayout.LayoutParams(80, 80)
                    cartAnimView.startPosition = startPoint
                    cartAnimView.endPosition = endPoint

                    val rootView = this.window.decorView as ViewGroup
                    rootView.addView(cartAnimView)

                    cartAnimView.startAnimation()
                }
            }.show(supportFragmentManager)
        }
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

        return super.onCreateOptionsMenu(menu)
    }
}