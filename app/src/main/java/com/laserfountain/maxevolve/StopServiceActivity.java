package com.laserfountain.maxevolve;

import android.app.Activity;
import android.os.Bundle;

public class StopServiceActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        EvolveCounterOverlayService.stop();

        finish();
    }

}
