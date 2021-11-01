package com.a1573595.shoppingdemo.page.goodsList

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.collection.SparseArrayCompat
import androidx.recyclerview.widget.RecyclerView
import com.a1573595.shoppingdemo.data.Goods
import com.a1573595.shoppingdemo.databinding.ItemGoodsBinding
import com.a1573595.shoppingdemo.databinding.ItemScrollviewBinding
import com.a1573595.shoppingdemo.tool.GlideApp

class GoodsListAdapter(private val onClick: (Goods) -> Unit) :
    RecyclerView.Adapter<GoodsListAdapter.GoodsListViewHolder>() {
    private var goodsListArray: SparseArrayCompat<List<Goods>> = SparseArrayCompat()

    private val margin = 36
    private var recyclerViewWidth = 0

    inner class GoodsListViewHolder(val binding: ItemScrollviewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            when (position) {
                0 -> {
                    binding.tvTitle.text = "新品"
                    binding.tvSubTitle.text = "季線限定，產地直送"
                }
                1 -> {
                    binding.tvTitle.text = "熱賣"
                    binding.tvSubTitle.text = "物真價實，貨美價廉"
                }
                else -> {
                    binding.tvTitle.text = "其他"
                    binding.tvSubTitle.text = "快速出貨，24小時到貨"
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoodsListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemScrollviewBinding.inflate(layoutInflater, parent, false)

        val recyclerView = parent as RecyclerView
        recyclerViewWidth = recyclerView.width

        return GoodsListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GoodsListViewHolder, position: Int) {
        val goodsList = goodsListArray[position]!!
        val context = holder.itemView.context

        val paddingLeft = holder.itemView.paddingTop
        val paddingRight = holder.itemView.paddingTop

        val disPlayItems = 3
        val totalMargin = margin * disPlayItems
        val imageWidth =
            (recyclerViewWidth - totalMargin - paddingLeft - paddingRight) / disPlayItems

        holder.bind(position)

        goodsList.forEachIndexed { index, goods ->
            val binding = ItemGoodsBinding.inflate(
                LayoutInflater.from(context),
                holder.binding.linearLayout,
                true
            )

            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )

            params.marginStart = margin

            binding.root.layoutParams = params

            GlideApp.with(context)
                .load(goods.imgUrl)
                .fitCenter()
                .into(binding.imgGoods)

            val imageParams = LinearLayout.LayoutParams(imageWidth, imageWidth)
            binding.imgGoods.layoutParams = imageParams
            binding.tvName.text = goodsList[index].name

            binding.root.setOnClickListener {
                onClick.invoke(goods)
            }
        }
    }

    override fun getItemCount(): Int = goodsListArray.size()

    fun updateGoods(goodsListArray: SparseArrayCompat<List<Goods>>) {
        this.goodsListArray.clear()
        this.goodsListArray.putAll(goodsListArray)
        notifyDataSetChanged()
    }
}