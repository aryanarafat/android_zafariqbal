package com.yeasinrabbee.zafariqbal.Utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import f1.h;
import java.lang.reflect.Field;
import s2.a;

/* loaded from: classes.dex */
public class NonSwappableViewPager extends h {
    public NonSwappableViewPager(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        try {
            Field declaredField = h.class.getDeclaredField("h");
            declaredField.setAccessible(true);
            declaredField.set(this, new a(getContext()));
        } catch (Exception e4) {
            e4.printStackTrace();
        }
    }

    @Override // android.view.ViewGroup
    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return false;
    }

    @Override // android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        return false;
    }
}