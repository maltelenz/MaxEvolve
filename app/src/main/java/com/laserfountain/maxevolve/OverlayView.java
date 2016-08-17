package com.laserfountain.maxevolve;


import android.content.Context;
import android.graphics.PixelFormat;
import android.support.v4.view.GestureDetectorCompat;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

public abstract class OverlayView extends RelativeLayout {

    protected WindowManager.LayoutParams layoutParameters;

    private int layoutResId;

    private GestureDetectorCompat mDetector;
    private boolean shown;

    public OverlayView(OverlayService service, int layoutResId) {
        super(service);

        this.layoutResId = layoutResId;

        mDetector = new GestureDetectorCompat(getContext(), new MyGestureListener());

        shown = true;

        load();
    }

    public OverlayService getService() {
        return (OverlayService) getContext();
    }

    private void setupLayoutParams() {
        layoutParameters = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |
                        WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH |
                        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
        );

        layoutParameters.gravity = getGravity();
    }

    private void inflateView() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(layoutResId, this);

        onInflateView();
    }

    protected void onInflateView() {

    }

    public boolean overlayIsShown() {
        return shown;
    }

    protected void addView() {
        setupLayoutParams();

        ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).addView(this, layoutParameters);

        super.setVisibility(View.GONE);
    }

    protected void load() {
        inflateView();
        addView();
        refresh();
    }

    protected void unload() {
        ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).removeView(this);

        removeAllViews();
    }

    public void destroy() {
        ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).removeView(this);
    }

    public void refresh() {
        if (!overlayIsShown()) {
            setVisibility(View.GONE);
        } else {
            setVisibility(View.VISIBLE);
            updateLayout();
        }
    }

    protected void updateLayout() {
        // Overridden in EvolveCounterOverlayView
    }

    @Override
    public void setVisibility(int visibility) {
        if (visibility == View.VISIBLE) {
            getService().moveToForeground();
        } else {
            getService().moveToBackground();
        }

        if (getVisibility() != visibility) {
            super.setVisibility(visibility);
        }
    }

    protected void onTouchEventUp(MotionEvent event) {

    }

    protected void onTouchEventScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    public void toggle(boolean booleanExtra) {
        shown = booleanExtra;
        refresh();
    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapUp(MotionEvent event) {
            onTouchEventUp(event);
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                                float distanceY) {
            onTouchEventScroll(e1, e2, distanceX, distanceY);
            return true;
        }

    }
}
