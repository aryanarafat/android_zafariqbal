package com.yeasinrabbee.zafariqbal.Activities;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.b3;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.yeasinrabbee.zafariqbal.R;
import d.q;
import j2.b;
import java.util.ArrayList;
import java.util.List;
import o2.l;
import o2.m;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class ListOfContentActivity extends q {
    public static final /* synthetic */ int A = 0;

    /* renamed from: t  reason: collision with root package name */
    public RecyclerView f2250t;

    /* renamed from: u  reason: collision with root package name */
    public ArrayList f2251u;

    /* renamed from: v  reason: collision with root package name */
    public l f2252v;

    /* renamed from: w  reason: collision with root package name */
    public int f2253w = 0;

    /* renamed from: x  reason: collision with root package name */
    public String f2254x = "";

    /* renamed from: y  reason: collision with root package name */
    public String f2255y;

    /* renamed from: z  reason: collision with root package name */
    public TextView f2256z;

    @Override // androidx.activity.k, android.app.Activity
    public final void onBackPressed() {
        ImageView imageView = (ImageView) findViewById(R.id.searchviewimage);
        EditText editText = (EditText) findViewById(R.id.edittext);
    }

    @Override // androidx.fragment.app.v, androidx.activity.k, w.g, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_list_of_content);
        this.f2256z = (TextView) findViewById(R.id.listOfcontentTitle_textview);
        ((ImageView) findViewById(R.id.list_imageback)).setOnClickListener(new b(5, this));
        this.f2250t = (RecyclerView) findViewById(R.id.rv);
        this.f2251u = new ArrayList();
        this.f2253w = getIntent().getIntExtra("type", 0);
        this.f2254x = getIntent().getStringExtra("title");
        this.f2255y = getIntent().getStringExtra("name");
        this.f2256z.setText(this.f2254x);
        String q02 = s2.b.q0(this, "json2.json");
        if (q02 != null) {
            try {
                JSONArray jSONArray = new JSONObject(q02).getJSONArray("data");
                for (int i4 = 0; i4 < jSONArray.length(); i4++) {
                    JSONObject jSONObject = jSONArray.getJSONObject(i4);
                    if (jSONObject.getString("title").equals(this.f2254x)) {
                        JSONArray jSONArray2 = jSONObject.getJSONArray("content");
                        int i5 = 0;
                        while (i5 < jSONArray2.length()) {
                            int i6 = i5 + 1;
                            this.f2251u.add(new m(s2.b.E(String.valueOf(i6)), jSONArray2.getJSONObject(i5).getString("title"), this.f2255y));
                            i5 = i6;
                        }
                    }
                }
                this.f2252v = new l((List) this.f2251u, this.f2253w);
                this.f2250t.setLayoutManager(new LinearLayoutManager(1));
                this.f2250t.setAdapter(this.f2252v);
            } catch (JSONException e4) {
                throw new RuntimeException(e4);
            }
        }
        EditText editText = (EditText) findViewById(R.id.edittext);
        ((EditText) findViewById(R.id.edittext)).addTextChangedListener(new b3(this, 3));
    }
}