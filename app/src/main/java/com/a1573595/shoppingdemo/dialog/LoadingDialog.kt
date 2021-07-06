package com.a1573595.shoppingdemo.dialog

import android.animation.ValueAnimator
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.a1573595.shoppingdemo.databinding.DialogLoadingBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class LoadingDialog : DialogFragment() {
    companion object {
        const val TAG: String = "LoadingDialog"
    }

    private var animator: ValueAnimator? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        animator = ValueAnimator.ofInt(0, 8)
        animator?.duration = 750
//        animator?.interpolator = LinearInterpolator()
        animator?.repeatCount = ValueAnimator.INFINITE

        val binding = DialogLoadingBinding.inflate(LayoutInflater.from(requireContext()))

        // , R.style.LoadingDialog
        val loadingDialog = MaterialAlertDialogBuilder(requireContext())
            .setBackground(ColorDrawable(Color.TRANSPARENT))
            .setView(binding.root)
            .setOnDismissListener { animator?.cancel() }
            .create()

        loadingDialog.show()

        animator?.addUpdateListener {
            binding.imgLoad.rotation = (it.animatedValue as Int).toFloat() * 45
            binding.imgLoad.requestLayout()
        }

        animator?.start()

        binding.root.setOnClickListener {
            if (isCancelable) {
                dismiss()
            }
        }

        return loadingDialog
    }

    override fun onDestroyView() {
        animator?.cancel()
        super.onDestroyView()
    }

    override fun show(manager: FragmentManager, tag: String?) {
//        mDismissed = false
//        mShownByMe = true
        val ft = manager.beginTransaction()
        ft.add(this, tag ?: TAG)
        ft.commitAllowingStateLoss()
    }

    override fun dismiss() {
        dismissAllowingStateLoss()
    }

    fun show(manager: FragmentManager) {
        show(manager, TAG)
    }
}