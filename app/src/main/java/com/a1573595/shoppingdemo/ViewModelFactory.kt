package com.a1573595.shoppingdemo

import androidx.lifecycle.ViewModelProvider

class ViewModelFactory : ViewModelProvider.NewInstanceFactory() {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        return when {
//            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
//                LoginViewModel() as T
//            }
//            modelClass.isAssignableFrom(GoodsListViewModel::class.java) -> {
//                GoodsListViewModel() as T
//            }
//            modelClass.isAssignableFrom(GoodsDetailViewModel::class.java) -> {
//                GoodsDetailViewModel() as T
//            }
//            modelClass.isAssignableFrom(ShoppingCartViewModel::class.java) -> {
//                ShoppingCartViewModel() as T
//            }
//            else -> throw IllegalArgumentException("Unknown ViewModel class")
//        }
//    }
}