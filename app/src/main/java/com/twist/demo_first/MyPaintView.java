package com.twist.demo_first;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * File description
 *
 * @author twist
 * @date 2019/10/14 19 19
 * @email twistonidea@gmail.com
 */
public class MyPaintView extends View {
    private List<Point> allPoints = new ArrayList<Point>(); //保存所有坐标点

    public MyPaintView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        super.setBackgroundColor(Color.WHITE);
        super.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Point point = new Point((int) motionEvent.getX(),(int)motionEvent.getY());
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) { //判断按下
                    allPoints = new ArrayList<Point>(); //开始新的记录
                    allPoints.add(point);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_MOVE){
                    allPoints.add(point);
                    MyPaintView.this.postInvalidate(); //重绘
                }else if (motionEvent.getAction() == MotionEvent.ACTION_UP){
                    allPoints.add(point);
                }
                return true; //表示下面的不再执行了
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        if (allPoints.size() > 1){
            Iterator<Point> iterator = allPoints.iterator();
            Point firstPoint = null;//开始点
            Point lastPoint = null;//结束点
            while (iterator.hasNext()) {
                if (firstPoint == null) {//找到开始点
                    firstPoint = (Point) iterator.next();
                }else {
                    if (lastPoint != null){
                        firstPoint = lastPoint;
                    }
                    lastPoint = (Point) iterator.next();
                    canvas.drawLine(firstPoint.x, firstPoint.y, lastPoint.x, lastPoint.y, paint);//画线
                }
            }
        }
        super.onDraw(canvas);
    }
}
