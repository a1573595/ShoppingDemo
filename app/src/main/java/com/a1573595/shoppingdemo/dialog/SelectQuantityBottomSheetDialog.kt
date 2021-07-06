package com.a1573595.shoppingdemo.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.a1573595.shoppingdemo.data.Goods
import com.a1573595.shoppingdemo.databinding.DialogSelectQuantityBinding
import com.a1573595.shoppingdemo.tool.GlideApp
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SelectQuantityBottomSheetDialog(private val goods: Goods, private val onBuy: (Int) -> Unit) :
    BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewBinding = DialogSelectQuantityBinding.inflate(LayoutInflater.from(requireContext()))

        init(viewBinding)

        return viewBinding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext())

        dialog.setOnShowListener {
            val bottomSheet =
                dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as View

            val behavior: BottomSheetBehavior<*> = BottomSheetBehavior.from(bottomSheet)

            behavior.skipCollapsed = true
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }

        return dialog
    }

    fun show(manager: FragmentManager) = super.show(manager, "SelectQuantityBottomSheetDialog")

    private fun init(viewBinding: DialogSelectQuantityBinding) {
        var quantity = 1

        viewBinding.tvName.text = goods.name
        GlideApp.with(this)
            .load(goods.imgUrl)
            .fitCenter()
            .into(viewBinding.imgGoods)

        viewBinding.tvReduce.setOnClickListener {
            if (quantity > 1) {
                viewBinding.tvQuantity.text = "${--quantity}"
            }
        }

        viewBinding.tvIncrease.setOnClickListener {
            viewBinding.tvQuantity.text = "${++quantity}"
        }

        viewBinding.btnBuy.setOnClickListener {
            dismissAllowingStateLoss()
            onBuy.invoke(quantity)
        }
    }
}