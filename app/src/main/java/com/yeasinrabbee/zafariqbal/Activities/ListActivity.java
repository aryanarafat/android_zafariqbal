package com.yeasinrabbee.zafariqbal.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.yeasinrabbee.zafariqbal.Beans.ListAdapter;
import com.yeasinrabbee.zafariqbal.Beans.ListModel;
import com.yeasinrabbee.zafariqbal.Cm.ContentActivity;
import com.yeasinrabbee.zafariqbal.R;
import com.yeasinrabbee.zafariqbal.Utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListActivity extends AppCompatActivity {

    private RecyclerView rv;
    private List<ListModel> listModelList;
    private ListAdapter adapter;
    private InterstitialAd mInterstitialAd;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        MobileAds.initialize(this, initializationStatus -> {
        });

      //  loadInterstitialAd();


        TextView listtitelTextview = findViewById(R.id.listTitleTextview);

        ImageView imageViewBack = findViewById(R.id.imageviewBack);
        imageViewBack.setOnClickListener(v -> finish());

        rv = findViewById(R.id.rv);
        listModelList = new ArrayList<>();
        EditText edittext = findViewById(R.id.edittext);

        String name = getIntent().getStringExtra("name");
        int type = getIntent().getIntExtra("Type", 0);

        listtitelTextview.setText(name);

        boolean isListOfContent = false;

        String json = Utils.loadJsonfromcachdir(this, Utils.getJson(type));

        if (json != null) {

            try {


                JSONObject jsonObject = new JSONObject(json);

                JSONArray array = jsonObject.getJSONArray("data");


                isListOfContent = jsonObject.getBoolean("type");


                for (int i = 0; i < array.length(); i++) {

                    int n = i + 1;

                    JSONObject object = array.getJSONObject(i);

                    if (isListOfContent) {

                        ListModel model = new ListModel(
                                Utils.conNum(String.valueOf(n)),
                                object.getString("title"),
                                "মোট কনটেন্ট " + Utils.conNum(String.valueOf(object.getJSONArray("content").length())) + " টি",
                                name
                        );

                        listModelList.add(model);
                    } else {

                        JSONArray content = object.getJSONArray("content");


                        for (int j = 0; j < content.length(); j++) {

                            JSONObject ob = content.getJSONObject(j);

                            int n1 = j + 1;

                            ListModel model = new ListModel(
                                    Utils.conNum(String.valueOf(n1)),
                                    ob.getString("title"),
                                    "",
                                    name
                            );

                            listModelList.add(model);
                        }

                    }

                }


                adapter = new ListAdapter(listModelList, isListOfContent ? 1 : 2, type);
                rv.setLayoutManager(new LinearLayoutManager(this));
                rv.setAdapter(adapter);


                adapter.cb((type1, title, name1, booktype) -> {

                    if (mInterstitialAd != null) {
                        showInterstitial();
                        mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdDismissedFullScreenContent() {

                                mInterstitialAd = null;
                                loadInterstitialAd();

                                Intent intent;
                                intent = new Intent(ListActivity.this, ContentActivity.class);
                                intent.putExtra("title", title);
                                intent.putExtra("type", booktype);
                                intent.putExtra("name", name1);
                                startActivity(intent);

                            }

                        });


                    } else {

                        Intent intent;
                        intent = new Intent(ListActivity.this, ContentActivity.class);
                        intent.putExtra("title", title);
                        intent.putExtra("type", booktype);
                        intent.putExtra("name", name1);
                        startActivity(intent);


                    }


                });


                edittext.setHint("মোট কনটেন্ট  " + Utils.conNum(String.valueOf(listModelList.size())) + " টি");


            } catch (JSONException e) {
                throw new RuntimeException(e);
            }


        }


        edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                String txt = editable.toString();


                if (!txt.isEmpty()) {

                    List<ListModel> copy = new ArrayList<>();

                    for (ListModel model : listModelList) {

                        if (model.getTitle().contains(txt)) {
                            copy.add(model);
                        }

                    }

                    adapter.addData(copy);


                } else {
                    adapter.addData(listModelList);
                }

            }
        });


    }


    public void loadInterstitialAd() {
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(this, "ca-app-pub-3940256099942544/1033173712", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        mInterstitialAd = interstitialAd;
                        Log.d("TAG", "onAdLoaded");
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {

                        Log.i("TAG", loadAdError.getMessage());
                        mInterstitialAd = null;


                        String error = String.format(
                                Locale.ENGLISH,
                                "domain: %s, code: %d, message: %s",
                                loadAdError.getDomain(),
                                loadAdError.getCode(),
                                loadAdError.getMessage());

                        Log.d("TAG", "onAdFailedToLoad: " + error);


                    }
                });
    }

    private void showInterstitial() {
        if (mInterstitialAd != null) {
            mInterstitialAd.show(this);
        } else {
            Toast.makeText(this, "Ad did not load", Toast.LENGTH_SHORT).show();
            Log.d("TAG", "Ad did not load");
        }
    }


}