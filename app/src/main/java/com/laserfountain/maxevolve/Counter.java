package com.laserfountain.maxevolve;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.HashSet;
import java.util.Set;

public class Counter {
    private int base;
    private int nr;
    private Context context;

    public Counter(Context context) {
        this.context = context;
        this.base = 12;
        nr = 1;
    }

    public int getBase() {
        return base;
    }

    public int getNr() {
        return nr;
    }

    public int getNr(int offset) {
        return nr + offset;
    }

    public int getCandies() {
        if (nr == 0) {
            return 0;
        }
        return base + (base - 1) * (nr - 1);
    }

    public int getCandies(int offset) {
        if (nr + offset == 0) {
            return 0;
        }
        return base + (base - 1) * ((nr + offset) - 1);
    }

    public void increaseNr(int i) {
        nr = Math.max(nr + i, 1);
    }

    public void increaseBase() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Set<String> levels = prefs.getStringSet("base_candy_levels", new HashSet<String>());

        int smallestLarger = Integer.MAX_VALUE;
        int smallest = Integer.MAX_VALUE;
        for (String lvl : levels) {
            Log.d("Counter", "Level:" + lvl);
            int v = Integer.valueOf(lvl);
            if (v > base && v < smallestLarger) {
                smallestLarger = v;
            }
            if (v < smallest) {
                smallest = v;
            }
        }
        if (smallestLarger != Integer.MAX_VALUE) {
            base = smallestLarger;
        } else if (smallest != Integer.MAX_VALUE) {
            base = smallest;
        } else {
            base = 12;
        }
    }
}
