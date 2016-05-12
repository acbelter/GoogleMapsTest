package com.acbelter.maptest;

import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

public final class TestData {
    private List<Pair<String, String>> mTestAddresses;
    private int mCurrentIndex;

    public TestData() {
        mTestAddresses = new ArrayList<>();
        mTestAddresses.add(Pair.create("Дмитровское ш., 13 корпус 2 строение 3, Москва", "ул. Новокузнецкая, 44/2, Москва"));
        mTestAddresses.add(Pair.create("Рузовская ул., 31, Санкт-Петербург", "Центральная ул., 23, Жилево"));
        mTestAddresses.add(Pair.create("1531-1535 SW 2nd Ave, Miami, FL 33129, США", "ул. 8 Марта, 1, город Москва"));
    }

    public Pair<String, String> getNextAddresses() {
        Pair<String, String> next = mTestAddresses.get(mCurrentIndex);
        if (mCurrentIndex == mTestAddresses.size() - 1) {
            mCurrentIndex = 0;
        } else {
            mCurrentIndex++;
        }

        return next;
    }
}
