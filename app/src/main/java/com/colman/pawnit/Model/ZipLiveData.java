package com.colman.pawnitv2.Model;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

public class ZipLiveData<T1, T2, R> extends MediatorLiveData<R> {
    private T1 mLastLeft;
    private T2 mLastRight;
    private Zipper<T1, T2, R> mZipper;

    public static <T1, T2, R> LiveData<R> create(@NonNull LiveData<T1> left, @NonNull LiveData<T2> right, Zipper<T1, T2, R> zipper) {
        final ZipLiveData<T1, T2, R> liveData = new ZipLiveData<T1, T2, R>(zipper);

        liveData.addSource(left, value -> {
            liveData.mLastLeft = value;
            liveData.update();
        });
        liveData.addSource(right, value -> {
            liveData.mLastRight = value;
            liveData.update();
        });
        return liveData;
    }

    private ZipLiveData(@NonNull Zipper<T1, T2, R> zipper) {
        mZipper = zipper;
    }

    private void update() {
        final R result = mZipper.zip(mLastLeft, mLastRight);
        setValue(result);
    }

    public interface Zipper<T1, T2, R> {
        R zip(T1 left, T2 right);
    }
}
