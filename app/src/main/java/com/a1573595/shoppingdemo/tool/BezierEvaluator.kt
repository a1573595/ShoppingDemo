package com.a1573595.shoppingdemo.tool

import android.animation.TypeEvaluator
import android.graphics.Point

class BezierEvaluator(private val controlPoint: Point) : TypeEvaluator<Point> {
    override fun evaluate(t: Float, startValue: Point, endValue: Point): Point {
        val x =
            ((1 - t) * (1 - t) * startValue.x.toFloat() + 2f * t * (1 - t) * controlPoint.x.toFloat() + t * t * endValue.x.toFloat()).toInt()
        val y =
            ((1 - t) * (1 - t) * startValue.y.toFloat() + 2f * t * (1 - t) * controlPoint.y.toFloat() + t * t * endValue.y.toFloat()).toInt()
        return Point(x, y)
    }
}