package com.laserfountain.maxevolve;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class StartServiceActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        startService(new Intent(this, EvolveCounterOverlayService.class));

        finish();
    }

}
