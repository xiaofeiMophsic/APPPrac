package com.example.analogclockview;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;


/**
 * 作者：xiaofei
 * 日期：2016/9/7
 * 邮箱：paozi.xiaofei.123@gmail.com
 */

public class AnalogClockView extends View{

    private GregorianCalendar mCalendar;

    private Drawable mDial;
    private Drawable mHourHand;
    private Drawable mMinuteHand;

    private int mDialWidth;
    private int mDialHeight;

    private float mMinutes;
    private float mHour;

    private boolean mAttached;
    private boolean mChanged;

    private final BroadcastReceiver intentReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals(Intent.ACTION_TIMEZONE_CHANGED)) {
                String tz = intent.getStringExtra("time-zone");
                mCalendar = new GregorianCalendar(TimeZone.getTimeZone(tz));
            }

            onTimeChanged();
            invalidate();
        }
    };

    public AnalogClockView(Context context) {
        this(context, null);
    }

    public AnalogClockView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AnalogClockView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public AnalogClockView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        mCalendar = new GregorianCalendar();

        if (mDial == null) {
            mDial = context.getDrawable(R.drawable.clock_dial);
        }

        if (mHourHand == null) {
            mHourHand = context.getDrawable(R.drawable.clock_hand_hour);
        }

        if (mMinuteHand == null) {
            mMinuteHand = context.getDrawable(R.drawable.clock_hand_minute);
        }

        mDialWidth = mDial.getIntrinsicWidth();
        mDialHeight = mDial.getIntrinsicHeight();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        float hScale = 1.0f;
        float vScale = 1.0f;

        if (widthMode != MeasureSpec.UNSPECIFIED && widthSize < mDialWidth) {
            hScale = (float) widthSize / mDialWidth;
        }

        if (heightMode != MeasureSpec.UNSPECIFIED && heightSize < mDialHeight) {
            hScale = (float) heightSize / mDialHeight;
        }

        float scale = Math.min(hScale, vScale);

        setMeasuredDimension(
                resolveSizeAndState((int) (mDialWidth * scale), widthMeasureSpec, 0),
                resolveSizeAndState((int) (mDialHeight * scale), heightMeasureSpec, 0));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mChanged = true;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!mAttached) {
            mAttached = true;

            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(Intent.ACTION_TIME_CHANGED);
            intentFilter.addAction(Intent.ACTION_TIME_TICK);
            intentFilter.addAction(Intent.ACTION_TIMEZONE_CHANGED);

            getContext().registerReceiver(intentReceiver, intentFilter);
        }

        mCalendar = new GregorianCalendar();
        onTimeChanged();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mAttached) {
            mAttached = false;
            getContext().unregisterReceiver(intentReceiver);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        boolean isChanged = mChanged;
        if (isChanged) {
            mChanged = false;
        }

        final Drawable dial = mDial;
        int w = dial.getIntrinsicWidth();
        int h = dial.getIntrinsicHeight();

        int availableWidth = super.getRight() - super.getLeft();
        int availableHeight = super.getBottom() - super.getTop();

        int x = availableWidth / 2;
        int y = availableHeight / 2;

        boolean scaled = false;

        if (availableWidth < w || availableHeight < h) {
            scaled = true;
            float scale = Math.min((float) availableWidth / w, (float) availableHeight / h);
            canvas.save();
            canvas.scale(scale, scale, x, y);
        }

        // 给表盘设置边界，然后再边界内进行绘制
        if (isChanged) {
            dial.setBounds(x - w / 2, y - h / 2, x + w / 2, y + h / 2);
        }
        dial.draw(canvas);

        // 绘制时针
        canvas.save();
        canvas.rotate(mHour / 12f * 360, x, y);
        final Drawable hourHand = mHourHand;
        if (isChanged) {
            hourHand.setBounds(x - hourHand.getIntrinsicWidth() / 2, y - hourHand.getIntrinsicHeight() / 2,
                    x + hourHand.getIntrinsicWidth() / 2, y + hourHand.getIntrinsicHeight() / 2);
        }
        hourHand.draw(canvas);
        canvas.restore();

        // 绘制分针
        canvas.save();
        canvas.rotate(mMinutes / 60f * 360, x, y);
        final Drawable minuteHand = mMinuteHand;
        if (isChanged) {
            minuteHand.setBounds(x - minuteHand.getIntrinsicWidth() / 2, y - minuteHand.getIntrinsicHeight() / 2,
                    x + minuteHand.getIntrinsicWidth() / 2, y + minuteHand.getIntrinsicHeight() / 2);
        }
        minuteHand.draw(canvas);
        canvas.restore();

        if (scaled) {
            canvas.restore();
        }
    }

    private void onTimeChanged() {
        mCalendar.setTimeInMillis(System.currentTimeMillis());

        int hour = mCalendar.get(Calendar.HOUR);
        int minute = mCalendar.get(Calendar.MINUTE);
        int second = mCalendar.get(Calendar.SECOND);

        mMinutes = minute + second / 60f;
        mHour = hour + minute / 60f;

        mChanged = true;
    }
}
