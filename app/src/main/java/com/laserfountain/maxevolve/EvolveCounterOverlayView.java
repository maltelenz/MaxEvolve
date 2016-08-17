package com.laserfountain.maxevolve;

import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.TextView;

public class EvolveCounterOverlayView extends OverlayView {

    private static final float SCROLL_FACTOR = 60;
    private TextView base;
    private TextView lessNr;
    private TextView lessCandies;
    private TextView exactNr;
    private TextView exactCandies;
    private TextView moreNr;
    private TextView moreCandies;
    private Counter counter;
    private float unusedScrollDistance = 0;

    public EvolveCounterOverlayView(OverlayService service) {
        super(service, R.layout.overlay);
    }

    public int getGravity() {
        return Gravity.TOP + Gravity.END;
    }

    @Override
    protected void onInflateView() {
        counter = new Counter(getContext());

        base = (TextView) this.findViewById(R.id.textview_base);

        lessNr = (TextView) this.findViewById(R.id.textview_nr_evolved_less);
        lessCandies = (TextView) this.findViewById(R.id.textview_nr_candies_less);
        exactNr = (TextView) this.findViewById(R.id.textview_nr_evolved_exact);
        exactCandies = (TextView) this.findViewById(R.id.textview_nr_candies_exact);
        moreNr = (TextView) this.findViewById(R.id.textview_nr_evolved_more);
        moreCandies = (TextView) this.findViewById(R.id.textview_nr_candies_more);

        // Adjust display of main counter
        exactNr.setTextSize(22);
        exactCandies.setTextSize(22);
        exactNr.setTextColor(getResources().getColor(android.R.color.white));
        exactCandies.setTextColor(getResources().getColor(android.R.color.white));

        updateLayout();
    }

    @Override
    protected void updateLayout() {
        lessNr.setText(String.valueOf(counter.getNr(-1)));
        lessCandies.setText(String.valueOf(counter.getCandies(-1)));
        exactNr.setText(String.valueOf(counter.getNr()));
        exactCandies.setText(String.valueOf(counter.getCandies()));
        moreNr.setText(String.valueOf(counter.getNr(1)));
        moreCandies.setText(String.valueOf(counter.getCandies(1)));
        base.setText(String.valueOf(counter.getBase()));
    }

    @Override
    protected void onTouchEventUp(MotionEvent event) {
        counter.increaseBase();
        updateLayout();
    }

    @Override
    protected void onTouchEventScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        float cumulativeDistance = unusedScrollDistance - distanceY;
        int increase = (int) Math.floor(cumulativeDistance/SCROLL_FACTOR);
        counter.increaseNr(increase);
        unusedScrollDistance = cumulativeDistance - increase * SCROLL_FACTOR;
        updateLayout();
    }
}
