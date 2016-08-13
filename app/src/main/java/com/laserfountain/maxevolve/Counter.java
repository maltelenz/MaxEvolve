package com.laserfountain.maxevolve;

public class Counter {
    private int base;
    private int nr;

    public Counter() {
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
        switch(base) {
            case 12:
                base = 25;
                break;
            case 25:
                base = 50;
                break;
            case 50:
                base = 100;
                break;
            case 100:
                base = 400;
                break;
            case 400:
                base = 12;
                break;
        }
    }
}
