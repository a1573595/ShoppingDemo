package com.a1573595.shoppingdemo.customView

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Point
import android.util.AttributeSet
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import com.a1573595.shoppingdemo.tool.BezierEvaluator

class CartAnimationView constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    androidx.appcompat.widget.AppCompatImageView(context, attrs, defStyleAttr) {
    lateinit var startPosition: Point
    lateinit var endPosition: Point

    fun startAnimation() {
        val pointX = (startPosition.x + endPosition.x) / 2
        val pointY = endPosition.y + 100
        val bezierEvaluator = BezierEvaluator(Point(pointX, pointY))

        val animator = ValueAnimator.ofObject(bezierEvaluator, startPosition, endPosition)

        animator.addUpdateListener { valueAnimator ->
            val point = valueAnimator.animatedValue as Point

            x = point.x.toFloat()
            y = point.y.toFloat()
            invalidate()
        }

        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)

                //動畫結束時，移除該View
                val viewGroup = parent as ViewGroup
                viewGroup.removeView(this@CartAnimationView)
            }
        })

        animator.duration = 1000
        animator.interpolator = AccelerateDecelerateInterpolator()
        animator.start()
    }
}