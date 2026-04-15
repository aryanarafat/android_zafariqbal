package com.yeasinrabbee.zafariqbal;

import android.os.Bundle;
import android.widget.ImageView;
import d.q;
import j2.b;

/* loaded from: classes.dex */
public class AboutApp extends q {

    /* renamed from: t  reason: collision with root package name */
    public static final /* synthetic */ int f2244t = 0;

    @Override // androidx.fragment.app.v, androidx.activity.k, w.g, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_about_app);
        ((ImageView) findViewById(R.id.sample_back_button)).setOnClickListener(new b(3, this));
    }
}