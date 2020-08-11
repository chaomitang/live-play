package com.wuqi.playersdk.demo.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Process;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.wuqi.playersdk.R;

@SuppressLint("ViewConstructor")
public class SurfaceImageView extends SurfaceView implements SurfaceHolder.Callback {

    private Bitmap mBitmap;
    private HandlerThread mThread;

    public SurfaceImageView(Context context) {
        super(context);
        init();
    }

    public SurfaceImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SurfaceImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        Drawable drawable = getContext().getResources().getDrawable(R.drawable.room);
        getHolder().addCallback(this);
        mBitmap = ((BitmapDrawable) drawable).getBitmap();
        drawable.setCallback(this);
        drawable.setLevel(0);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    private int left = 0;
    private boolean toRight = true;
    private boolean running = true;
    @Override
    public void surfaceCreated(final SurfaceHolder holder) {
        running = true;
        mThread = new HandlerThread("SurfaceImage-" + System.currentTimeMillis(), Process.THREAD_PRIORITY_BACKGROUND);
        mThread.start();
        Handler handler = new Handler(mThread.getLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                while (running) {
                    Canvas canvas = null;
                    try {
                        if (left <= 0) {
                            toRight = true;
                        } else if (left > 1080) {
                            toRight = false;
                        }
                        if (toRight) {
                            left = left + 5;
                        } else {
                            left = left - 5;
                        }
                        canvas = holder.lockCanvas();
                        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                        canvas.drawBitmap(mBitmap, left, 0, null);
                    } finally {
                        if (canvas != null) {
                            holder.unlockCanvasAndPost(canvas);
                        }
                    }
                }
            }
        });
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        running = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            mThread.quitSafely();
        } else {
            mThread.quit();
        }
    }
}
