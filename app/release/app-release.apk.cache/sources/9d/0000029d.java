package com.yeasinrabbee.zafariqbal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import d.q;
import d.v;
import java.util.Timer;
import n2.a;

/* loaded from: classes.dex */
public class Splash extends q {

    /* renamed from: t  reason: collision with root package name */
    public int f2279t;

    /* renamed from: u  reason: collision with root package name */
    public final Timer f2280u = new Timer();

    /* renamed from: v  reason: collision with root package name */
    public final Intent f2281v = new Intent();

    @Override // androidx.fragment.app.v, androidx.activity.k, w.g, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_splash);
        this.f2279t = getSharedPreferences("MyPrefs", 0).getInt("SelectedTheme", 0);
        SharedPreferences.Editor edit = getSharedPreferences("MyPrefs", 0).edit();
        edit.putInt("SelectedTheme", this.f2279t);
        edit.apply();
        int i4 = this.f2279t;
        if (i4 != 0) {
            if (i4 == 1) {
                v.n(2);
            }
        } else {
            v.n(1);
        }
        this.f2280u.schedule(new a(this), 1000L);
    }
}