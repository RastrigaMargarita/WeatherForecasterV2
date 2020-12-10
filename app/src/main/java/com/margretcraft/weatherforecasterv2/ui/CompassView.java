package com.margretcraft.weatherforecasterv2.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.margretcraft.weatherforecasterv2.R;

public class CompassView extends View {
    // Цвет компаса
    private int compassColor;
    // Цвет компаса, когда нет данных
    private int errorColor = Color.RED;

    private Paint compassPaint;
    private Paint compassErrorPaint;
    // Размеры элементов
    private int sizeCompassView = 0;
    private int sizeCompassArrow = 0;

    private float angle;
    Bitmap bitmapArrow;
    Bitmap rotatedBitmapArrow;
    Matrix matrix;

    private Boolean sensorValid = false;

    public CompassView(Context context) {
        super(context);
        init();
    }

    public CompassView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttr(context, attrs);
        init();
    }

    public CompassView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(context, attrs);
        init();
    }

    public CompassView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initAttr(context, attrs);
        init();
    }

    public void setAngle(float angle) {
        this.angle = -angle;
    }

    public void setSensorValid(Boolean sensorValid) {
        this.sensorValid = sensorValid;
    }

    // Инициализация атрибутов пользовательского элемента из xml
    private void initAttr(Context context, AttributeSet attrs) {

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CompassView, 0, 0);

        compassColor = typedArray.getColor(R.styleable.CompassView_compass_color, Color.BLACK);
        errorColor = typedArray.getColor(R.styleable.CompassView_error_color, Color.YELLOW);
        bitmapArrow = BitmapFactory.decodeResource(context.getResources(), typedArray.getResourceId(R.styleable.CompassView_compass_pict, 0));

        typedArray.recycle();
    }

    // Начальная инициализация полей класса
    private void init() {
        compassPaint = new Paint();
        compassPaint.setColor(compassColor);
        compassPaint.setStyle(Paint.Style.STROKE);
        compassPaint.setStrokeWidth(10);

        matrix = new Matrix();

        compassErrorPaint = new Paint();
        compassErrorPaint.setColor(errorColor);
        compassErrorPaint.setStyle(Paint.Style.STROKE);
        compassErrorPaint.setStrokeWidth(10);

        sizeCompassArrow = bitmapArrow.getWidth();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        sizeCompassView = w;
    }

    // Вызывается, когда надо нарисовать элемент
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(sizeCompassView / 2, sizeCompassView / 2, 140, sensorValid ? compassPaint : compassErrorPaint);
        matrix.setRotate(angle, sizeCompassArrow / 2, sizeCompassArrow / 2);
        rotatedBitmapArrow = Bitmap.createBitmap(bitmapArrow, 0, 0, sizeCompassArrow, sizeCompassArrow, matrix, true);
        canvas.drawBitmap(rotatedBitmapArrow, (sizeCompassView - rotatedBitmapArrow.getWidth()) / 2, (sizeCompassView - rotatedBitmapArrow.getHeight()) / 2, compassPaint);
    }

}
