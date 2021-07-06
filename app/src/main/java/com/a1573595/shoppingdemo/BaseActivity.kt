package com.a1573595.shoppingdemo

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.a1573595.shoppingdemo.dialog.LoadingDialog
import com.a1573595.shoppingdemo.tool.EventObserver
import java.lang.reflect.ParameterizedType

abstract class BaseActivity<VB : ViewBinding, VM : BaseViewModel> : AppCompatActivity() {
    protected lateinit var viewBinding: VB

    protected lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = inflateBindingGeneric(javaClass, layoutInflater)
        setContentView(viewBinding.root)

        title = this.javaClass.simpleName

        viewModel = ViewModelProvider(this, ViewModelFactory()).get(getTClass())
        lifecycle.addObserver(viewModel)

        viewModel.toastResource.observe(this, EventObserver {
            Toast.makeText(this, getString(it), Toast.LENGTH_SHORT).show()
        })

        viewModel.toastText.observe(this, EventObserver {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })

        viewModel.isLoading.observe(this, EventObserver {
            if (it) {
                val dialog = LoadingDialog()
//            dialog.isCancelable = cancelable
                dialog.show(supportFragmentManager)
            } else {
                val loadingDialog = supportFragmentManager.findFragmentByTag(LoadingDialog.TAG)
                if (loadingDialog is LoadingDialog) {
                    loadingDialog.dismiss()
                }
            }
        })
    }

    override fun onDestroy() {
        dismissAllDialogs(supportFragmentManager)
        super.onDestroy()
    }

    override fun onBackPressed() {
        finishAfterTransition()
    }

    private fun inflateBindingGeneric(javaClass: Class<Any>, layoutInflater: LayoutInflater): VB {
        val parameterizedType = javaClass.genericSuperclass as ParameterizedType
        val actualType = parameterizedType.actualTypeArguments[0] as Class<VB>

        val method = actualType.getMethod("inflate", LayoutInflater::class.java)

        return method.invoke(null, layoutInflater) as VB
    }

    private fun getTClass(): Class<VM> {
        return (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[1] as Class<VM>
    }

    private fun dismissAllDialogs(fm: FragmentManager) {
        val fragments = fm.fragments

        fragments.forEach {
            if (it is DialogFragment) {
                it.dismissAllowingStateLoss()
            }

            dismissAllDialogs(it.childFragmentManager)
        }
    }
}