package com.yeasinrabbee.zafariqbal.Activities;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.widget.b3;
import androidx.recyclerview.widget.RecyclerView;
import com.yeasinrabbee.zafariqbal.R;
import d.q;
import j2.b;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class ListActivity extends q {

    /* renamed from: x  reason: collision with root package name */
    public static final /* synthetic */ int f2245x = 0;

    /* renamed from: t  reason: collision with root package name */
    public ArrayList f2246t;

    /* renamed from: u  reason: collision with root package name */
    public EditText f2247u;

    /* renamed from: v  reason: collision with root package name */
    public String f2248v;

    /* renamed from: w  reason: collision with root package name */
    public TextView f2249w;

    @Override // androidx.activity.k, android.app.Activity
    public final void onBackPressed() {
        EditText editText = (EditText) findViewById(R.id.edittext);
    }

    @Override // androidx.fragment.app.v, androidx.activity.k, w.g, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_list);
        this.f2249w = (TextView) findViewById(R.id.listTitleTextview);
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.download_layout);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.main_layout);
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        ((ImageView) findViewById(R.id.imageviewBack)).setOnClickListener(new b(4, this));
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv);
        this.f2246t = new ArrayList();
        this.f2247u = (EditText) findViewById(R.id.edittext);
        this.f2248v = getIntent().getStringExtra("name");
        getIntent().getIntExtra("Type", 0);
        this.f2249w.setText(this.f2248v);
        EditText editText = (EditText) findViewById(R.id.edittext);
        this.f2247u.addTextChangedListener(new b3(this, 2));
    }
}