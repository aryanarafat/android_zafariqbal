package com.yeasinrabbee.zafariqbal.Cm;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Typeface;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.yeasinrabbee.zafariqbal.Beans.ListAdapter;
import com.yeasinrabbee.zafariqbal.Beans.ListModel;
import com.yeasinrabbee.zafariqbal.Db.Helper;
import com.yeasinrabbee.zafariqbal.R;
import com.yeasinrabbee.zafariqbal.Utils.Constant;
import com.yeasinrabbee.zafariqbal.Utils.RatingDialog;
import com.yeasinrabbee.zafariqbal.Utils.SaveData;
import com.yeasinrabbee.zafariqbal.Utils.Utils;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ContentActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {


    TextView solaimanlipi;

    TextView default_Font, shamimcholonkita, harappa, shokhunlota, ani, likhannormal, mukti, sagarnormal, siyam, lohit;


    private Typeface currentTypeface;
    SharedPreferences prefs;
    private SharedPreferences sharedPreferences;

    private SeekBar fontSizeSeekBar;
    private SeekBar lineSpacingSeekBar;

    private int fontSize;

    private SharedPreferences preferences;
    private static final String PREF_BRIGHTNESS = "brightness";

    LinearLayout content_linear_background_layout;
    private SharedPreferences f;
    private TextView titel_textview;


    ImageView Love_rate;


    private ToggleButton mToggleButton;
    private SeekBar mSeekBar;

    private int mScrollSpeed;
    private boolean mAutoScroll;
    private CardView scroll_cardview;
    private final Handler mHandler = new Handler();

    TextView body;


    private int type = 0;
    private String title = "";
    private String name = "";
    private String category = "";

    private Helper helper;

    FrameLayout left_panel;

    LinearLayout menu_layout;

    private int width;

    EditText edittext;
    private static final String PREFS_NAME = "MyPrefs";

    private static final String DIALOG_SHOWN_KEY = "dialogShown";

    public ContentActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);


        ratingDialog();





        prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        left_panel = findViewById(R.id.left_panel);

        menu_layout = findViewById(R.id.menu_layout);

        LinearLayout main_linearLayout = findViewById(R.id.main_linearLayout);


        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        width = displayMetrics.widthPixels;

        left_panel.setTranslationX(-width);

        left_panel.setOnClickListener(v -> {

            left_panel.animate().translationX(-width).setDuration(300);

        });


        menu_layout.setOnClickListener(v -> {

            left_panel.animate().translationX(0).setDuration(300);


        });


        helper = new Helper(ContentActivity.this);


        body = findViewById(R.id.body);
        titel_textview = findViewById(R.id.titel_textview);

        TextView contentTextView = findViewById(R.id.content_textview);


        type = getIntent().getIntExtra("type", 0);
        title = getIntent().getStringExtra("title");
        name = getIntent().getStringExtra("name");

        loadContent();


        ImageView close1 = findViewById(R.id.close);


        LinearLayout font = findViewById(R.id.font_layout);
        LinearLayout close = findViewById(R.id.close_autoscroll);


        mToggleButton = findViewById(R.id.toggle_button);
        mSeekBar = findViewById(R.id.seek_bar);


        CardView cardView = findViewById(R.id.cardview);
        scroll_cardview = findViewById(R.id.scroll_cardview);

        scroll_cardview.setVisibility(View.GONE);

        font.setOnClickListener(v -> {

            if (!mToggleButton.isChecked()) {

                if (scroll_cardview.getVisibility() == View.GONE) {


                    Toast.makeText(ContentActivity.this, "স্বয়ংক্রিয় স্ক্রলিংয়ের জন্য প্লে বোতামে ক্লিক করে সিকবার থেকে স্পীর্ড নিয়ন্ত্রণ করুন",
                            Toast.LENGTH_SHORT).show();

                }


            }


            if (scroll_cardview.getVisibility() == View.GONE) {


                scroll_cardview.setVisibility(View.VISIBLE);

            } else {

                scroll_cardview.setVisibility(View.GONE);
            }


            /*    cardView.setVisibility(View.GONE);*/

        });


        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mScrollSpeed = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });


        close.setOnClickListener(v -> {

            scroll_cardview.setVisibility(View.GONE);
            cardView.setVisibility(View.VISIBLE);

            if (mAutoScroll) {
                mAutoScroll = false;
            }


            mToggleButton.setChecked(false);

            if (mAutoScroll) {


            } else {

            }


        });


        Love_rate = findViewById(R.id.favorite);


        LinearLayout copylayout = findViewById(R.id.copy_layout);
        LinearLayout share_layout = findViewById(R.id.share_layout);


        copylayout.setOnClickListener(v -> {


            Copy();


        });

        share_layout.setOnClickListener(v -> Share());


        SeekBar brightnessSeekBar = findViewById(R.id.seekBarBrightness);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        int brightness = preferences.getInt(PREF_BRIGHTNESS, 100);
        brightnessSeekBar.setProgress(brightness);

        brightnessSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                preferences.edit().putInt(PREF_BRIGHTNESS, progress).apply();
                WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
                layoutParams.screenBrightness = (float) progress / 255f;
                getWindow().setAttributes(layoutParams);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        prefs = PreferenceManager.getDefaultSharedPreferences(this);


        content_linear_background_layout = findViewById(R.id.content_linear_background_layout);


        ImageView imageViewback = findViewById(R.id.imageviewback);

        imageViewback.setOnClickListener(v -> finish());

        fontSizeSeekBar = findViewById(R.id.font_seekbar);
        lineSpacingSeekBar = findViewById(R.id.lineSpacingSeekBar);


        this.f = getSharedPreferences("f", 0);


        String savedValue = f.getString("Spacing", "3");
        int progress = Integer.parseInt(savedValue);
        float lineSpacing = 0.75f + (progress - 1) * 0.25f;
        body.setLineSpacing(0.0f, lineSpacing);
        lineSpacingSeekBar.setProgress(progress);


        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        fontSize = sharedPreferences.getInt("FONT_SIZE", 17);

        int textcolor = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            textcolor = sharedPreferences.getInt("textColor", getColor(R.color.text_color));
        }
        int backgroundcolor = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            backgroundcolor = sharedPreferences.getInt("backgroundColor", getColor(R.color.color_primary_dark));
        }


        content_linear_background_layout.setBackgroundColor(backgroundcolor);
        body.setTextColor(textcolor);

        body.setTextSize(fontSize);
        fontSizeSeekBar.setProgress(fontSize);

        fontSizeSeekBar.setOnSeekBarChangeListener(this);
        lineSpacingSeekBar.setOnSeekBarChangeListener(this);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            textcolorcontrol();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            brightness();
        }


        AppBarLayout appbarLayout = findViewById(R.id.appbarLayout);


        BottomNavigationView navigationView = findViewById(R.id.navigationView);
        LinearLayout LinearLayoutcardview = findViewById(R.id.LinearLayoutcardview);
        LinearLayout content_text_layout = findViewById(R.id.content_text_layout);
        LinearLayout content_brightness_layout = findViewById(R.id.content_brightness_layout);
        LinearLayout brightnesslayout = findViewById(R.id.brightnesslayout);
        LinearLayout full_textlayout = findViewById(R.id.full_textlayout);

        content_text_layout.setOnClickListener(v -> {

            if (full_textlayout.getVisibility() == View.GONE) {
                full_textlayout.setVisibility(View.VISIBLE);

            } else {
                full_textlayout.setVisibility(View.GONE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    navigationView.setBackgroundColor(getColor(R.color.color_primary));
                }
            }

            if (brightnesslayout.getVisibility() == View.VISIBLE) {

                brightnesslayout.setVisibility(View.GONE);
            }


        });

        content_brightness_layout.setOnClickListener(v -> {

            if (brightnesslayout.getVisibility() == View.GONE) {
                brightnesslayout.setVisibility(View.VISIBLE);
            } else {
                brightnesslayout.setVisibility(View.GONE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    navigationView.setBackgroundColor(getColor(R.color.color_primary));
                }
            }

            if (full_textlayout.getVisibility() == View.VISIBLE) {

                full_textlayout.setVisibility(View.GONE);
            }

        });


        body.setOnClickListener(v -> {


            if (appbarLayout.getVisibility() == View.VISIBLE) {
                appbarLayout.setVisibility(View.GONE);


                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

            } else {


                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);


                appbarLayout.setVisibility(View.VISIBLE);

            }
            if (LinearLayoutcardview.getVisibility() == View.GONE) {
                LinearLayoutcardview.setVisibility(View.VISIBLE);
            } else {
                LinearLayoutcardview.setVisibility(View.GONE);

            }


        });


        TextView fontresttextview = findViewById(R.id.Font_rest_textview);
        TextView brightnessresttexview = findViewById(R.id.brightnessresttexview);

        brightnessresttexview.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                content_linear_background_layout.setBackgroundColor(getColor(R.color.color_primary_dark));
            }
            SharedPreferences.Editor editor = sharedPreferences.edit();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                editor.putInt("backgroundColor", getColor(R.color.color_primary_dark));
            }
            editor.apply();

        });

        fontresttextview.setOnClickListener(v -> {
            ContentActivity.this.fontSizeSeekBar.setProgress(15);
            ContentActivity.this.lineSpacingSeekBar.setProgress(2);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                body.setTextColor(getColor(R.color.text_color));
            }


            SharedPreferences.Editor editor = sharedPreferences.edit();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                editor.putInt("textColor", getColor(R.color.text_color));
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                editor.putInt("backgroundColor", getColor(R.color.color_primary_dark));
            }
            editor.putInt("FONT_SIZE", fontSize);

            sharedPreferences.edit().remove("backgroundColor").apply();

            editor.apply();
        });


        titel_textview.setText(getIntent().getStringExtra("title"));
        contentTextView.setText(getIntent().getStringExtra("content"));
        /* category_textview.setText(getIntent().getStringExtra("category"));*/


        isFav();

        Love_rate.setOnClickListener(v -> {

            Cursor isFav = helper.getAt(title, Helper.TYPE_FAV);

            if (isFav != null && isFav.getCount() > 0) return;


            Love_rate.setImageDrawable(getDrawable(R.drawable.ic_favourite_select));


            helper.add(title, String.valueOf(type), category, name, new Date().getTime(), Helper.TYPE_FAV);

            Log.d("datebase", "datebase added ");


        });


        default_Font = findViewById(R.id.default_Font);
        solaimanlipi = findViewById(R.id.solaimanlipi);
        shamimcholonkita = findViewById(R.id.shamimcholonkita);
        harappa = findViewById(R.id.harappa);
        shokhunlota = findViewById(R.id.shokhunlota);
        ani = findViewById(R.id.ani);
        likhannormal = findViewById(R.id.likhannormal);
        mukti = findViewById(R.id.mukti);
        sagarnormal = findViewById(R.id.sagarnormal);
        siyam = findViewById(R.id.siyam);
        lohit = findViewById(R.id.lohit);
        default_Font = findViewById(R.id.default_Font);


        currentTypeface = Typeface.DEFAULT;

        default_Font.setOnClickListener(v -> {


            setFont("sens.ttf");


            default_Font.setTypeface(null, Typeface.BOLD);
            solaimanlipi.setTypeface(null, Typeface.BOLD);
            shamimcholonkita.setTypeface(null, Typeface.NORMAL);
            harappa.setTypeface(null, Typeface.NORMAL);
            shokhunlota.setTypeface(null, Typeface.NORMAL);
            ani.setTypeface(null, Typeface.NORMAL);
            likhannormal.setTypeface(null, Typeface.NORMAL);
            mukti.setTypeface(null, Typeface.NORMAL);
            sagarnormal.setTypeface(null, Typeface.NORMAL);
            siyam.setTypeface(null, Typeface.NORMAL);
            lohit.setTypeface(null, Typeface.NORMAL);


        });

        solaimanlipi.setOnClickListener(v -> {


            setFont("solaimanlipi.ttf");


            default_Font.setTypeface(null, Typeface.NORMAL);

            solaimanlipi.setTypeface(null, Typeface.BOLD);
            solaimanlipi.setTypeface(currentTypeface);

            shamimcholonkita.setTypeface(null, Typeface.NORMAL);
            harappa.setTypeface(null, Typeface.NORMAL);
            shokhunlota.setTypeface(null, Typeface.NORMAL);
            ani.setTypeface(null, Typeface.NORMAL);
            likhannormal.setTypeface(null, Typeface.NORMAL);
            mukti.setTypeface(null, Typeface.NORMAL);

            sagarnormal.setTypeface(null, Typeface.NORMAL);
            siyam.setTypeface(null, Typeface.NORMAL);
            lohit.setTypeface(null, Typeface.NORMAL);


        });
        shamimcholonkita.setOnClickListener(v -> {


            setFont("shamimcholonkita.ttf");
            default_Font.setTypeface(null, Typeface.NORMAL);
            solaimanlipi.setTypeface(null, Typeface.NORMAL);

            shamimcholonkita.setTypeface(null, Typeface.BOLD);
            shamimcholonkita.setTypeface(currentTypeface);

            harappa.setTypeface(null, Typeface.NORMAL);
            shokhunlota.setTypeface(null, Typeface.NORMAL);
            ani.setTypeface(null, Typeface.NORMAL);
            likhannormal.setTypeface(null, Typeface.NORMAL);
            mukti.setTypeface(null, Typeface.NORMAL);
            sagarnormal.setTypeface(null, Typeface.NORMAL);
            siyam.setTypeface(null, Typeface.NORMAL);
            lohit.setTypeface(null, Typeface.NORMAL);


        });
        harappa.setOnClickListener(v -> {


            setFont("harappa.ttf");

            default_Font.setTypeface(null, Typeface.NORMAL);
            solaimanlipi.setTypeface(null, Typeface.NORMAL);
            shamimcholonkita.setTypeface(null, Typeface.NORMAL);
            harappa.setTypeface(null, Typeface.BOLD);
            harappa.setTypeface(currentTypeface);

            shokhunlota.setTypeface(null, Typeface.NORMAL);
            ani.setTypeface(null, Typeface.NORMAL);
            likhannormal.setTypeface(null, Typeface.NORMAL);
            mukti.setTypeface(null, Typeface.NORMAL);
            sagarnormal.setTypeface(null, Typeface.NORMAL);
            siyam.setTypeface(null, Typeface.NORMAL);
            lohit.setTypeface(null, Typeface.NORMAL);


        });
        shokhunlota.setOnClickListener(v -> {


            setFont("shokhunlota.ttf");

            default_Font.setTypeface(null, Typeface.NORMAL);
            solaimanlipi.setTypeface(null, Typeface.NORMAL);
            shamimcholonkita.setTypeface(null, Typeface.NORMAL);
            harappa.setTypeface(null, Typeface.NORMAL);
            shokhunlota.setTypeface(null, Typeface.BOLD);
            shokhunlota.setTypeface(currentTypeface);

            ani.setTypeface(null, Typeface.NORMAL);
            likhannormal.setTypeface(null, Typeface.NORMAL);
            mukti.setTypeface(null, Typeface.NORMAL);

            sagarnormal.setTypeface(null, Typeface.NORMAL);
            siyam.setTypeface(null, Typeface.NORMAL);
            lohit.setTypeface(null, Typeface.NORMAL);


        });

        ani.setOnClickListener(v -> {


            setFont("ani.ttf");


            default_Font.setTypeface(null, Typeface.NORMAL);
            solaimanlipi.setTypeface(null, Typeface.NORMAL);
            shamimcholonkita.setTypeface(null, Typeface.NORMAL);
            harappa.setTypeface(null, Typeface.NORMAL);
            shokhunlota.setTypeface(null, Typeface.NORMAL);
            ani.setTypeface(null, Typeface.NORMAL);
            ani.setTypeface(currentTypeface);

            likhannormal.setTypeface(null, Typeface.NORMAL);
            mukti.setTypeface(null, Typeface.NORMAL);

            sagarnormal.setTypeface(null, Typeface.NORMAL);
            siyam.setTypeface(null, Typeface.NORMAL);
            lohit.setTypeface(null, Typeface.NORMAL);


        });
        likhannormal.setOnClickListener(v -> {


            setFont("likhannormal.ttf");
            likhannormal.setTypeface(currentTypeface);

            default_Font.setTypeface(null, Typeface.NORMAL);
            solaimanlipi.setTypeface(null, Typeface.NORMAL);
            shamimcholonkita.setTypeface(null, Typeface.NORMAL);
            harappa.setTypeface(null, Typeface.NORMAL);
            shokhunlota.setTypeface(null, Typeface.NORMAL);
            ani.setTypeface(null, Typeface.NORMAL);
            likhannormal.setTypeface(null, Typeface.BOLD);
            mukti.setTypeface(null, Typeface.NORMAL);

            sagarnormal.setTypeface(null, Typeface.NORMAL);
            siyam.setTypeface(null, Typeface.NORMAL);
            lohit.setTypeface(null, Typeface.NORMAL);


        });
        mukti.setOnClickListener(v -> {


            default_Font.setTypeface(null, Typeface.NORMAL);
            solaimanlipi.setTypeface(null, Typeface.NORMAL);
            shamimcholonkita.setTypeface(null, Typeface.NORMAL);
            harappa.setTypeface(null, Typeface.NORMAL);
            shokhunlota.setTypeface(null, Typeface.NORMAL);
            ani.setTypeface(null, Typeface.NORMAL);
            likhannormal.setTypeface(null, Typeface.NORMAL);
            mukti.setTypeface(null, Typeface.BOLD);
            mukti.setTypeface(currentTypeface);

            sagarnormal.setTypeface(null, Typeface.NORMAL);
            siyam.setTypeface(null, Typeface.NORMAL);
            lohit.setTypeface(null, Typeface.NORMAL);


            setFont("mukti.ttf");


        });
        sagarnormal.setOnClickListener(v -> {


            setFont("sagarnormal.ttf");

            default_Font.setTypeface(null, Typeface.NORMAL);
            solaimanlipi.setTypeface(null, Typeface.NORMAL);
            shamimcholonkita.setTypeface(null, Typeface.NORMAL);
            harappa.setTypeface(null, Typeface.NORMAL);
            shokhunlota.setTypeface(null, Typeface.NORMAL);
            ani.setTypeface(null, Typeface.NORMAL);
            likhannormal.setTypeface(null, Typeface.NORMAL);
            mukti.setTypeface(null, Typeface.NORMAL);

            sagarnormal.setTypeface(null, Typeface.BOLD);
            sagarnormal.setTypeface(currentTypeface);

            siyam.setTypeface(null, Typeface.NORMAL);
            lohit.setTypeface(null, Typeface.NORMAL);


        });
        siyam.setOnClickListener(v -> {


            default_Font.setTypeface(null, Typeface.NORMAL);
            solaimanlipi.setTypeface(null, Typeface.NORMAL);
            shamimcholonkita.setTypeface(null, Typeface.NORMAL);
            harappa.setTypeface(null, Typeface.NORMAL);
            shokhunlota.setTypeface(null, Typeface.NORMAL);
            ani.setTypeface(null, Typeface.NORMAL);
            likhannormal.setTypeface(null, Typeface.NORMAL);
            mukti.setTypeface(null, Typeface.NORMAL);

            sagarnormal.setTypeface(null, Typeface.NORMAL);
            siyam.setTypeface(null, Typeface.BOLD);
            siyam.setTypeface(currentTypeface);

            lohit.setTypeface(null, Typeface.NORMAL);


            setFont("siyam.ttf");


        });
        lohit.setOnClickListener(v -> {


            setFont("lohit.ttf");

            default_Font.setTypeface(null, Typeface.NORMAL);
            solaimanlipi.setTypeface(null, Typeface.NORMAL);
            shamimcholonkita.setTypeface(null, Typeface.NORMAL);
            harappa.setTypeface(null, Typeface.NORMAL);
            shokhunlota.setTypeface(null, Typeface.NORMAL);
            ani.setTypeface(null, Typeface.NORMAL);
            likhannormal.setTypeface(null, Typeface.NORMAL);
            mukti.setTypeface(null, Typeface.NORMAL);

            sagarnormal.setTypeface(null, Typeface.NORMAL);
            siyam.setTypeface(null, Typeface.NORMAL);
            lohit.setTypeface(null, Typeface.BOLD);
            lohit.setTypeface(currentTypeface);


        });

        String fontPath = prefs.getString("font", "");

        if (!fontPath.isEmpty()) {

            setFont(fontPath);

        }


    }

    private void setFonts(String font) {

    }

    private void isFav() {

        Cursor isFav = helper.getAt(title, Helper.TYPE_FAV);

        if (isFav != null && isFav.getCount() > 0) {


            Love_rate.setImageResource(R.drawable.ic_favourite_select);


        } else {

            Love_rate.setImageResource(R.drawable.favourite_unselect);

        }

    }

    private void loadContent() {


        TextView body = findViewById(R.id.body);

        String json = Utils.loadJsonfromcachdir(this, Utils.getJson(type));


        if (json != null) {

            try {


                JSONObject jsonObject = new JSONObject(json);

                JSONArray array = jsonObject.getJSONArray("data");

                titel_textview.setText(title);


                for (int i = 0; i < array.length(); i++) {


                    JSONObject object = array.getJSONObject(i);


                    JSONArray list = object.getJSONArray("content");

                    for (int j = 0; j < list.length(); j++) {

                        JSONObject ob = list.getJSONObject(j);

                        if (!ob.has("title")) {

                            Toast.makeText(this, object.getString("title"), Toast.LENGTH_SHORT).show();
                            Log.e("ERROR", object.getString("title"));

                            return;

                        }

                        if (ob.getString("title").equals(title)) {

                            body.setText(ob.getString("body"));
                            category = object.getString("title");
                            helper.add(title, String.valueOf(type), category, name, new Date().getTime(), Helper.TYPE_HISTORY);

                        }


                    }

                }


            } catch (JSONException e) {

                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                //throw new RuntimeException(e);
            }


        }

        rvpanel();


    }

    private void setFont(String name) {


        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/" + name);
        setTextViewTypeface(face);

        prefs.edit().putString("font", name).apply();
    }

    private void setCuastomTypeface(String fontFileName) {








/*
        final File fontFile = new File(getExternalCacheDir(), fontFileName);

        if (fontFile.exists()) {

            setFont(fontFile);

            return;
        }


       */
/* FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference fontRef = storageRef.child(fontFileName);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(R.layout.progress);
        dialog = builder.create();
        dialog.setCancelable(true);
        dialog.show();*//*



        fontRef.getFile(fontFile).addOnSuccessListener(taskSnapshot -> {

            setFont(fontFile);
            dialog.dismiss();
            Log.d("font", fontFileName + " font downloaded successfully.");

            try {
                byte[] fontData = new byte[0];
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    fontData = Files.readAllBytes(fontFile.toPath());
                }
                String fontBase64 = Base64.encodeToString(fontData, Base64.DEFAULT);
                prefs.edit().putString(fontPrefKey, fontBase64).apply();
                Log.d("font", fontFileName + " font saved in SharedPreferences.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).addOnFailureListener(exception -> {

            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            Log.d("font", "Failed to download " + fontFileName + " font.");
        });
*/

    }

    private void setTextViewTypeface(Typeface font) {
        currentTypeface = font;
        body.setTypeface(currentTypeface);


    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        if (seekBar == fontSizeSeekBar) {
            int newFontSize = progress < 10 ? 10 : (Math.min(progress, 40));
            body.setTextSize(newFontSize);
            fontSize = newFontSize;
        } else if (seekBar == lineSpacingSeekBar) {


            float lineSpacing = 0.75f + (progress - 1) * 0.25f;
            ContentActivity.this.body.setLineSpacing(0.0f, lineSpacing);
            ContentActivity.this.f.edit().putString("Spacing", String.valueOf(progress)).apply();


        }

    }


    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {


        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("FONT_SIZE", fontSize);

        editor.apply();
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void textcolorcontrol() {


        LinearLayout theme1Button = findViewById(R.id.theme1_button);
        LinearLayout theme2Button = findViewById(R.id.theme2_button);
        LinearLayout theme3Button = findViewById(R.id.theme3_button);
        LinearLayout theme4Button = findViewById(R.id.theme4_button);
        LinearLayout theme5Button = findViewById(R.id.theme5_button);
        LinearLayout theme6Button = findViewById(R.id.theme6_button);
        LinearLayout theme7Button = findViewById(R.id.theme7_button);
        LinearLayout theme8Button = findViewById(R.id.theme8_button);
        LinearLayout theme9Button = findViewById(R.id.theme9_button);
        LinearLayout theme10Button = findViewById(R.id.theme10_button);
        LinearLayout theme11Button = findViewById(R.id.theme11_button);
        LinearLayout theme12Button = findViewById(R.id.theme12_button);


        ImageView imageview_check_1 = findViewById(R.id.imageview_check_1);
        ImageView imageview_check_2 = findViewById(R.id.imageview_check_2);
        ImageView imageview_check_3 = findViewById(R.id.imageview_check_3);
        ImageView imageview_check_4 = findViewById(R.id.imageview_check_4);
        ImageView imageview_check_5 = findViewById(R.id.imageview_check_5);
        ImageView imageview_check_6 = findViewById(R.id.imageview_check_6);
        ImageView imageview_check_7 = findViewById(R.id.imageview_check_7);
        ImageView imageview_check_8 = findViewById(R.id.imageview_check_8);
        ImageView imageview_check_9 = findViewById(R.id.imageview_check_9);
        ImageView imageview_check_10 = findViewById(R.id.imageview_check_10);
        ImageView imageview_check_11 = findViewById(R.id.imageview_check_11);
        ImageView imageview_check_12 = findViewById(R.id.imageview_check_12);

        boolean isImageviewCheck1Visible = sharedPreferences.getBoolean("imageview_check_1_visibility", false);
        if (isImageviewCheck1Visible) {
            imageview_check_1.setVisibility(View.VISIBLE);

        } else {
            imageview_check_1.setVisibility(View.GONE);


        }

        boolean isImageviewCheck2Visible = sharedPreferences.getBoolean("imageview_check_2_visibility", false);
        if (isImageviewCheck2Visible) {
            imageview_check_2.setVisibility(View.VISIBLE);
        } else {
            imageview_check_2.setVisibility(View.GONE);
        }
        boolean isImageviewCheck3Visible = sharedPreferences.getBoolean("imageview_check_3_visibility", false);
        if (isImageviewCheck3Visible) {
            imageview_check_3.setVisibility(View.VISIBLE);
        } else {
            imageview_check_3.setVisibility(View.GONE);
        }
        boolean isImageviewCheck4Visible = sharedPreferences.getBoolean("imageview_check_4_visibility", false);
        if (isImageviewCheck4Visible) {
            imageview_check_4.setVisibility(View.VISIBLE);
        } else {
            imageview_check_4.setVisibility(View.GONE);
        }
        boolean isImageviewCheck5Visible = sharedPreferences.getBoolean("imageview_check_5_visibility", false);
        if (isImageviewCheck5Visible) {
            imageview_check_5.setVisibility(View.VISIBLE);
        } else {
            imageview_check_5.setVisibility(View.GONE);
        }
        boolean isImageviewCheck6Visible = sharedPreferences.getBoolean("imageview_check_6_visibility", false);
        if (isImageviewCheck6Visible) {
            imageview_check_6.setVisibility(View.VISIBLE);
        } else {
            imageview_check_6.setVisibility(View.GONE);
        }
        boolean isImageviewCheck7Visible = sharedPreferences.getBoolean("imageview_check_7_visibility", false);
        if (isImageviewCheck7Visible) {
            imageview_check_7.setVisibility(View.VISIBLE);
        } else {
            imageview_check_7.setVisibility(View.GONE);
        }
        boolean isImageviewCheck8Visible = sharedPreferences.getBoolean("imageview_check_8_visibility", false);
        if (isImageviewCheck8Visible) {
            imageview_check_8.setVisibility(View.VISIBLE);
        } else {
            imageview_check_8.setVisibility(View.GONE);
        }
        boolean isImageviewCheck9Visible = sharedPreferences.getBoolean("imageview_check_9_visibility", false);
        if (isImageviewCheck9Visible) {
            imageview_check_9.setVisibility(View.VISIBLE);
        } else {
            imageview_check_9.setVisibility(View.GONE);
        }
        boolean isImageviewCheck10Visible = sharedPreferences.getBoolean("imageview_check_10_visibility", false);
        if (isImageviewCheck10Visible) {
            imageview_check_10.setVisibility(View.VISIBLE);
        } else {
            imageview_check_10.setVisibility(View.GONE);
        }
        boolean isImageviewCheck11Visible = sharedPreferences.getBoolean("imageview_check_11_visibility", false);
        if (isImageviewCheck11Visible) {
            imageview_check_11.setVisibility(View.VISIBLE);
        } else {
            imageview_check_11.setVisibility(View.GONE);
        }
        boolean isImageviewCheck12Visible = sharedPreferences.getBoolean("imageview_check_12_visibility", false);
        if (isImageviewCheck12Visible) {
            imageview_check_12.setVisibility(View.VISIBLE);
        } else {
            imageview_check_12.setVisibility(View.GONE);
        }


        theme1Button.setOnClickListener(v -> {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                body.setTextColor(getColor(R.color.theme_colorgrey));
            }
            SharedPreferences.Editor editor = sharedPreferences.edit();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                editor.putInt("textColor", getColor(R.color.theme_colorgrey));
            }
            editor.putBoolean("imageview_check_1_visibility", true);
            editor.putBoolean("imageview_check_2_visibility", false);
            editor.putBoolean("imageview_check_3_visibility", false);
            editor.putBoolean("imageview_check_4_visibility", false);
            editor.putBoolean("imageview_check_5_visibility", false);
            editor.putBoolean("imageview_check_6_visibility", false);
            editor.putBoolean("imageview_check_7_visibility", false);
            editor.putBoolean("imageview_check_8_visibility", false);
            editor.putBoolean("imageview_check_9_visibility", false);
            editor.putBoolean("imageview_check_10_visibility", false);
            editor.putBoolean("imageview_check_11_visibility", false);
            editor.putBoolean("imageview_check_12_visibility", false);
            editor.apply();


            imageview_check_1.setVisibility(View.VISIBLE);
            imageview_check_2.setVisibility(View.GONE);
            imageview_check_3.setVisibility(View.GONE);
            imageview_check_4.setVisibility(View.GONE);
            imageview_check_5.setVisibility(View.GONE);
            imageview_check_6.setVisibility(View.GONE);
            imageview_check_7.setVisibility(View.GONE);
            imageview_check_8.setVisibility(View.GONE);
            imageview_check_9.setVisibility(View.GONE);
            imageview_check_10.setVisibility(View.GONE);
            imageview_check_11.setVisibility(View.GONE);
            imageview_check_12.setVisibility(View.GONE);


        });

        theme2Button.setOnClickListener(v -> {

            imageview_check_1.setVisibility(View.GONE);
            imageview_check_2.setVisibility(View.VISIBLE);
            imageview_check_3.setVisibility(View.GONE);
            imageview_check_4.setVisibility(View.GONE);
            imageview_check_5.setVisibility(View.GONE);
            imageview_check_6.setVisibility(View.GONE);
            imageview_check_7.setVisibility(View.GONE);
            imageview_check_8.setVisibility(View.GONE);
            imageview_check_9.setVisibility(View.GONE);
            imageview_check_10.setVisibility(View.GONE);
            imageview_check_11.setVisibility(View.GONE);
            imageview_check_12.setVisibility(View.GONE);


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                body.setTextColor(getColor(R.color.theme_colorblue));
            }
            SharedPreferences.Editor editor = sharedPreferences.edit();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                editor.putInt("textColor", getColor(R.color.theme_colorblue));
            }
            editor.putBoolean("imageview_check_1_visibility", false);
            editor.putBoolean("imageview_check_2_visibility", true);
            editor.putBoolean("imageview_check_3_visibility", false);
            editor.putBoolean("imageview_check_4_visibility", false);
            editor.putBoolean("imageview_check_5_visibility", false);
            editor.putBoolean("imageview_check_6_visibility", false);
            editor.putBoolean("imageview_check_7_visibility", false);
            editor.putBoolean("imageview_check_8_visibility", false);
            editor.putBoolean("imageview_check_9_visibility", false);
            editor.putBoolean("imageview_check_10_visibility", false);
            editor.putBoolean("imageview_check_11_visibility", false);
            editor.putBoolean("imageview_check_12_visibility", false);

            editor.apply();

        });

        theme3Button.setOnClickListener(v -> {
            imageview_check_1.setVisibility(View.GONE);
            imageview_check_2.setVisibility(View.GONE);
            imageview_check_3.setVisibility(View.VISIBLE);
            imageview_check_4.setVisibility(View.GONE);
            imageview_check_5.setVisibility(View.GONE);
            imageview_check_6.setVisibility(View.GONE);
            imageview_check_7.setVisibility(View.GONE);
            imageview_check_8.setVisibility(View.GONE);
            imageview_check_9.setVisibility(View.GONE);
            imageview_check_10.setVisibility(View.GONE);
            imageview_check_11.setVisibility(View.GONE);
            imageview_check_12.setVisibility(View.GONE);


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                body.setTextColor(getColor(R.color.theme_colorwhite));
            }
            SharedPreferences.Editor editor = sharedPreferences.edit();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                editor.putInt("textColor", getColor(R.color.theme_colorwhite));
            }
            editor.putBoolean("imageview_check_1_visibility", false);
            editor.putBoolean("imageview_check_2_visibility", false);
            editor.putBoolean("imageview_check_3_visibility", true);
            editor.putBoolean("imageview_check_4_visibility", false);
            editor.putBoolean("imageview_check_5_visibility", false);
            editor.putBoolean("imageview_check_6_visibility", false);
            editor.putBoolean("imageview_check_7_visibility", false);
            editor.putBoolean("imageview_check_8_visibility", false);
            editor.putBoolean("imageview_check_9_visibility", false);
            editor.putBoolean("imageview_check_10_visibility", false);
            editor.putBoolean("imageview_check_11_visibility", false);
            editor.putBoolean("imageview_check_12_visibility", false);
            editor.apply();

        });

        theme4Button.setOnClickListener(v -> {
            imageview_check_1.setVisibility(View.GONE);
            imageview_check_2.setVisibility(View.GONE);
            imageview_check_3.setVisibility(View.GONE);
            imageview_check_4.setVisibility(View.VISIBLE);
            imageview_check_5.setVisibility(View.GONE);
            imageview_check_6.setVisibility(View.GONE);
            imageview_check_7.setVisibility(View.GONE);
            imageview_check_8.setVisibility(View.GONE);
            imageview_check_9.setVisibility(View.GONE);
            imageview_check_10.setVisibility(View.GONE);
            imageview_check_11.setVisibility(View.GONE);
            imageview_check_12.setVisibility(View.GONE);


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                body.setTextColor(getColor(R.color.theme_colorblack));
            }
            SharedPreferences.Editor editor = sharedPreferences.edit();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                editor.putInt("textColor", getColor(R.color.theme_colorblack));
            }
            editor.putBoolean("imageview_check_1_visibility", false);
            editor.putBoolean("imageview_check_2_visibility", false);
            editor.putBoolean("imageview_check_3_visibility", false);
            editor.putBoolean("imageview_check_4_visibility", true);
            editor.putBoolean("imageview_check_5_visibility", false);
            editor.putBoolean("imageview_check_6_visibility", false);
            editor.putBoolean("imageview_check_7_visibility", false);
            editor.putBoolean("imageview_check_8_visibility", false);
            editor.putBoolean("imageview_check_9_visibility", false);
            editor.putBoolean("imageview_check_10_visibility", false);
            editor.putBoolean("imageview_check_11_visibility", false);
            editor.putBoolean("imageview_check_12_visibility", false);
            editor.apply();

        });

        theme5Button.setOnClickListener(v -> {

            imageview_check_1.setVisibility(View.GONE);
            imageview_check_2.setVisibility(View.GONE);
            imageview_check_3.setVisibility(View.GONE);
            imageview_check_4.setVisibility(View.GONE);
            imageview_check_5.setVisibility(View.VISIBLE);
            imageview_check_6.setVisibility(View.GONE);
            imageview_check_7.setVisibility(View.GONE);
            imageview_check_8.setVisibility(View.GONE);
            imageview_check_9.setVisibility(View.GONE);
            imageview_check_10.setVisibility(View.GONE);
            imageview_check_11.setVisibility(View.GONE);
            imageview_check_12.setVisibility(View.GONE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                body.setTextColor(getColor(R.color.theme_colorred));
            }
            SharedPreferences.Editor editor = sharedPreferences.edit();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                editor.putInt("textColor", getColor(R.color.theme_colorred));
            }
            editor.putBoolean("imageview_check_1_visibility", false);
            editor.putBoolean("imageview_check_2_visibility", false);
            editor.putBoolean("imageview_check_3_visibility", false);
            editor.putBoolean("imageview_check_4_visibility", false);
            editor.putBoolean("imageview_check_5_visibility", true);
            editor.putBoolean("imageview_check_6_visibility", false);
            editor.putBoolean("imageview_check_7_visibility", false);
            editor.putBoolean("imageview_check_8_visibility", false);
            editor.putBoolean("imageview_check_9_visibility", false);
            editor.putBoolean("imageview_check_10_visibility", false);
            editor.putBoolean("imageview_check_11_visibility", false);
            editor.putBoolean("imageview_check_12_visibility", false);
            editor.apply();

        });

        theme6Button.setOnClickListener(v -> {

            imageview_check_1.setVisibility(View.GONE);
            imageview_check_2.setVisibility(View.GONE);
            imageview_check_3.setVisibility(View.GONE);
            imageview_check_4.setVisibility(View.GONE);
            imageview_check_5.setVisibility(View.GONE);
            imageview_check_6.setVisibility(View.VISIBLE);
            imageview_check_7.setVisibility(View.GONE);
            imageview_check_8.setVisibility(View.GONE);
            imageview_check_9.setVisibility(View.GONE);
            imageview_check_10.setVisibility(View.GONE);
            imageview_check_11.setVisibility(View.GONE);
            imageview_check_12.setVisibility(View.GONE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                body.setTextColor(getColor(R.color.theme_coloryellow));
            }
            SharedPreferences.Editor editor = sharedPreferences.edit();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                editor.putInt("textColor", getColor(R.color.theme_coloryellow));
            }
            editor.putBoolean("imageview_check_1_visibility", false);
            editor.putBoolean("imageview_check_2_visibility", false);
            editor.putBoolean("imageview_check_3_visibility", false);
            editor.putBoolean("imageview_check_4_visibility", false);
            editor.putBoolean("imageview_check_5_visibility", false);
            editor.putBoolean("imageview_check_6_visibility", true);
            editor.putBoolean("imageview_check_7_visibility", false);
            editor.putBoolean("imageview_check_8_visibility", false);
            editor.putBoolean("imageview_check_9_visibility", false);
            editor.putBoolean("imageview_check_10_visibility", false);
            editor.putBoolean("imageview_check_11_visibility", false);
            editor.putBoolean("imageview_check_12_visibility", false);
            editor.apply();

        });

        theme7Button.setOnClickListener(v -> {

            imageview_check_1.setVisibility(View.GONE);
            imageview_check_2.setVisibility(View.GONE);
            imageview_check_3.setVisibility(View.GONE);
            imageview_check_4.setVisibility(View.GONE);
            imageview_check_5.setVisibility(View.GONE);
            imageview_check_6.setVisibility(View.GONE);
            imageview_check_7.setVisibility(View.VISIBLE);
            imageview_check_8.setVisibility(View.GONE);
            imageview_check_9.setVisibility(View.GONE);
            imageview_check_10.setVisibility(View.GONE);
            imageview_check_11.setVisibility(View.GONE);
            imageview_check_12.setVisibility(View.GONE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                body.setTextColor(getColor(R.color.theme_colororenge));
            }
            SharedPreferences.Editor editor = sharedPreferences.edit();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                editor.putInt("textColor", getColor(R.color.theme_colororenge));
            }
            editor.putBoolean("imageview_check_1_visibility", false);
            editor.putBoolean("imageview_check_2_visibility", false);
            editor.putBoolean("imageview_check_3_visibility", false);
            editor.putBoolean("imageview_check_4_visibility", false);
            editor.putBoolean("imageview_check_5_visibility", false);
            editor.putBoolean("imageview_check_6_visibility", false);
            editor.putBoolean("imageview_check_7_visibility", true);
            editor.putBoolean("imageview_check_8_visibility", false);
            editor.putBoolean("imageview_check_9_visibility", false);
            editor.putBoolean("imageview_check_10_visibility", false);
            editor.putBoolean("imageview_check_11_visibility", false);
            editor.putBoolean("imageview_check_12_visibility", false);
            editor.apply();

        });

        theme8Button.setOnClickListener(v -> {
            imageview_check_1.setVisibility(View.GONE);
            imageview_check_2.setVisibility(View.GONE);
            imageview_check_3.setVisibility(View.GONE);
            imageview_check_4.setVisibility(View.GONE);
            imageview_check_5.setVisibility(View.GONE);
            imageview_check_6.setVisibility(View.GONE);
            imageview_check_7.setVisibility(View.GONE);
            imageview_check_8.setVisibility(View.VISIBLE);
            imageview_check_9.setVisibility(View.GONE);
            imageview_check_10.setVisibility(View.GONE);
            imageview_check_11.setVisibility(View.GONE);
            imageview_check_12.setVisibility(View.GONE);

            body.setTextColor(getColor(R.color.theme_colorgreen));
            SharedPreferences.Editor editor = sharedPreferences.edit();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                editor.putInt("textColor", getColor(R.color.theme_colorgreen));
            }
            editor.putBoolean("imageview_check_1_visibility", false);
            editor.putBoolean("imageview_check_2_visibility", false);
            editor.putBoolean("imageview_check_3_visibility", false);
            editor.putBoolean("imageview_check_4_visibility", false);
            editor.putBoolean("imageview_check_5_visibility", false);
            editor.putBoolean("imageview_check_6_visibility", false);
            editor.putBoolean("imageview_check_7_visibility", false);
            editor.putBoolean("imageview_check_8_visibility", true);
            editor.putBoolean("imageview_check_9_visibility", false);
            editor.putBoolean("imageview_check_10_visibility", false);
            editor.putBoolean("imageview_check_11_visibility", false);
            editor.putBoolean("imageview_check_12_visibility", false);
            editor.apply();

        });

        theme9Button.setOnClickListener(v -> {

            imageview_check_1.setVisibility(View.GONE);
            imageview_check_2.setVisibility(View.GONE);
            imageview_check_3.setVisibility(View.GONE);
            imageview_check_4.setVisibility(View.GONE);
            imageview_check_5.setVisibility(View.GONE);
            imageview_check_6.setVisibility(View.GONE);
            imageview_check_7.setVisibility(View.GONE);
            imageview_check_8.setVisibility(View.GONE);
            imageview_check_9.setVisibility(View.VISIBLE);
            imageview_check_10.setVisibility(View.GONE);
            imageview_check_11.setVisibility(View.GONE);
            imageview_check_12.setVisibility(View.GONE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                body.setTextColor(getColor(R.color.theme_colorpurple));
            }
            SharedPreferences.Editor editor = sharedPreferences.edit();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                editor.putInt("textColor", getColor(R.color.theme_colorpurple));
            }
            editor.putBoolean("imageview_check_1_visibility", false);
            editor.putBoolean("imageview_check_2_visibility", false);
            editor.putBoolean("imageview_check_3_visibility", false);
            editor.putBoolean("imageview_check_4_visibility", false);
            editor.putBoolean("imageview_check_5_visibility", false);
            editor.putBoolean("imageview_check_6_visibility", false);
            editor.putBoolean("imageview_check_7_visibility", false);
            editor.putBoolean("imageview_check_8_visibility", false);
            editor.putBoolean("imageview_check_9_visibility", true);
            editor.putBoolean("imageview_check_10_visibility", false);
            editor.putBoolean("imageview_check_11_visibility", false);
            editor.putBoolean("imageview_check_12_visibility", false);
            editor.apply();

        });

        theme10Button.setOnClickListener(v -> {

            imageview_check_1.setVisibility(View.GONE);
            imageview_check_2.setVisibility(View.GONE);
            imageview_check_3.setVisibility(View.GONE);
            imageview_check_4.setVisibility(View.GONE);
            imageview_check_5.setVisibility(View.GONE);
            imageview_check_6.setVisibility(View.GONE);
            imageview_check_7.setVisibility(View.GONE);
            imageview_check_8.setVisibility(View.GONE);
            imageview_check_9.setVisibility(View.GONE);
            imageview_check_10.setVisibility(View.VISIBLE);
            imageview_check_11.setVisibility(View.GONE);
            imageview_check_12.setVisibility(View.GONE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                body.setTextColor(getColor(R.color.theme_colorpink));
            }
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("textColor", getColor(R.color.theme_colorpink));
            editor.putBoolean("imageview_check_1_visibility", false);
            editor.putBoolean("imageview_check_2_visibility", false);
            editor.putBoolean("imageview_check_3_visibility", false);
            editor.putBoolean("imageview_check_4_visibility", false);
            editor.putBoolean("imageview_check_5_visibility", false);
            editor.putBoolean("imageview_check_6_visibility", false);
            editor.putBoolean("imageview_check_7_visibility", false);
            editor.putBoolean("imageview_check_8_visibility", false);
            editor.putBoolean("imageview_check_9_visibility", false);
            editor.putBoolean("imageview_check_10_visibility", true);
            editor.putBoolean("imageview_check_11_visibility", false);
            editor.putBoolean("imageview_check_12_visibility", false);
            editor.apply();

        });

        theme11Button.setOnClickListener(v -> {

            imageview_check_1.setVisibility(View.GONE);
            imageview_check_2.setVisibility(View.GONE);
            imageview_check_3.setVisibility(View.GONE);
            imageview_check_4.setVisibility(View.GONE);
            imageview_check_5.setVisibility(View.GONE);
            imageview_check_6.setVisibility(View.GONE);
            imageview_check_7.setVisibility(View.GONE);
            imageview_check_8.setVisibility(View.GONE);
            imageview_check_9.setVisibility(View.GONE);
            imageview_check_10.setVisibility(View.GONE);
            imageview_check_11.setVisibility(View.VISIBLE);
            imageview_check_12.setVisibility(View.GONE);
            body.setTextColor(getColor(R.color.theme_colorbrowen));
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("textColor", getColor(R.color.theme_colorbrowen));
            editor.putBoolean("imageview_check_1_visibility", false);
            editor.putBoolean("imageview_check_2_visibility", false);
            editor.putBoolean("imageview_check_3_visibility", false);
            editor.putBoolean("imageview_check_4_visibility", false);
            editor.putBoolean("imageview_check_5_visibility", false);
            editor.putBoolean("imageview_check_6_visibility", false);
            editor.putBoolean("imageview_check_7_visibility", false);
            editor.putBoolean("imageview_check_8_visibility", false);
            editor.putBoolean("imageview_check_9_visibility", false);
            editor.putBoolean("imageview_check_10_visibility", false);
            editor.putBoolean("imageview_check_11_visibility", true);
            editor.putBoolean("imageview_check_12_visibility", false);
            editor.apply();

        });

        theme12Button.setOnClickListener(v -> {
            imageview_check_1.setVisibility(View.GONE);
            imageview_check_2.setVisibility(View.GONE);
            imageview_check_3.setVisibility(View.GONE);
            imageview_check_4.setVisibility(View.GONE);
            imageview_check_5.setVisibility(View.GONE);
            imageview_check_6.setVisibility(View.GONE);
            imageview_check_7.setVisibility(View.GONE);
            imageview_check_8.setVisibility(View.GONE);
            imageview_check_9.setVisibility(View.GONE);
            imageview_check_10.setVisibility(View.GONE);
            imageview_check_11.setVisibility(View.GONE);
            imageview_check_12.setVisibility(View.VISIBLE);

            body.setTextColor(getColor(R.color.theme_colorbeige));
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("textColor", getColor(R.color.theme_colorbeige));
            editor.putBoolean("imageview_check_1_visibility", false);
            editor.putBoolean("imageview_check_2_visibility", false);
            editor.putBoolean("imageview_check_3_visibility", false);
            editor.putBoolean("imageview_check_4_visibility", false);
            editor.putBoolean("imageview_check_5_visibility", false);
            editor.putBoolean("imageview_check_6_visibility", false);
            editor.putBoolean("imageview_check_7_visibility", false);
            editor.putBoolean("imageview_check_8_visibility", false);
            editor.putBoolean("imageview_check_9_visibility", false);
            editor.putBoolean("imageview_check_10_visibility", false);
            editor.putBoolean("imageview_check_11_visibility", false);
            editor.putBoolean("imageview_check_12_visibility", true);
            editor.apply();

        });


    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void brightness() {

        LinearLayout background_theme1_button = findViewById(R.id.background_theme1_button);
        LinearLayout background_theme2_button = findViewById(R.id.background_theme2_button);
        LinearLayout background_theme3_button = findViewById(R.id.background_theme3_button);
        LinearLayout background_theme4_button = findViewById(R.id.background_theme4_button);
        LinearLayout background_theme5_button = findViewById(R.id.background_theme5_button);
        LinearLayout background_theme6_button = findViewById(R.id.background_theme6_button);
        LinearLayout background_theme7_button = findViewById(R.id.background_theme7_button);
        LinearLayout background_theme8_button = findViewById(R.id.background_theme8_button);
        LinearLayout background_theme9_button = findViewById(R.id.background_theme9_button);
        LinearLayout background_theme10_button = findViewById(R.id.background_theme10_button);
        LinearLayout background_theme11_button = findViewById(R.id.background_theme11_button);
        LinearLayout background_theme12_button = findViewById(R.id.background_theme12_button);


        ImageView background_imageview_check_1 = findViewById(R.id.background_imageview_check_1);
        ImageView background_imageview_check_2 = findViewById(R.id.background_imageview_check_2);
        ImageView background_imageview_check_3 = findViewById(R.id.background_imageview_check_3);
        ImageView background_imageview_check_4 = findViewById(R.id.background_imageview_check_4);
        ImageView background_imageview_check_5 = findViewById(R.id.background_imageview_check_5);
        ImageView background_imageview_check_6 = findViewById(R.id.background_imageview_check_6);
        ImageView background_imageview_check_7 = findViewById(R.id.background_imageview_check_7);
        ImageView background_imageview_check_8 = findViewById(R.id.background_imageview_check_8);
        ImageView background_imageview_check_9 = findViewById(R.id.background_imageview_check_9);
        ImageView background_imageview_check_10 = findViewById(R.id.background_imageview_check_10);
        ImageView background_imageview_check_11 = findViewById(R.id.background_imageview_check_11);
        ImageView background_imageview_check_12 = findViewById(R.id.background_imageview_check_12);

        boolean isImageviewCheck1Visible = sharedPreferences.getBoolean("background_imageview_check_1_visibility", false);
        if (isImageviewCheck1Visible) {
            background_imageview_check_1.setVisibility(View.VISIBLE);

        } else {
            background_imageview_check_1.setVisibility(View.GONE);


        }

        boolean isImageviewCheck2Visible = sharedPreferences.getBoolean("background_imageview_check_2_visibility", false);
        if (isImageviewCheck2Visible) {
            background_imageview_check_2.setVisibility(View.VISIBLE);
        } else {
            background_imageview_check_2.setVisibility(View.GONE);
        }
        boolean isImageviewCheck3Visible = sharedPreferences.getBoolean("background_imageview_check_3_visibility", false);
        if (isImageviewCheck3Visible) {
            background_imageview_check_3.setVisibility(View.VISIBLE);
        } else {
            background_imageview_check_3.setVisibility(View.GONE);
        }
        boolean isImageviewCheck4Visible = sharedPreferences.getBoolean("background_imageview_check_4_visibility", false);
        if (isImageviewCheck4Visible) {
            background_imageview_check_4.setVisibility(View.VISIBLE);
        } else {
            background_imageview_check_4.setVisibility(View.GONE);
        }
        boolean isImageviewCheck5Visible = sharedPreferences.getBoolean("background_imageview_check_5_visibility", false);
        if (isImageviewCheck5Visible) {
            background_imageview_check_5.setVisibility(View.VISIBLE);
        } else {
            background_imageview_check_5.setVisibility(View.GONE);
        }
        boolean isImageviewCheck6Visible = sharedPreferences.getBoolean("background_imageview_check_6_visibility", false);
        if (isImageviewCheck6Visible) {
            background_imageview_check_6.setVisibility(View.VISIBLE);
        } else {
            background_imageview_check_6.setVisibility(View.GONE);
        }
        boolean isImageviewCheck7Visible = sharedPreferences.getBoolean("background_imageview_check_7_visibility", false);
        if (isImageviewCheck7Visible) {
            background_imageview_check_7.setVisibility(View.VISIBLE);
        } else {
            background_imageview_check_7.setVisibility(View.GONE);
        }
        boolean isImageviewCheck8Visible = sharedPreferences.getBoolean("background_imageview_check_8_visibility", false);
        if (isImageviewCheck8Visible) {
            background_imageview_check_8.setVisibility(View.VISIBLE);
        } else {
            background_imageview_check_8.setVisibility(View.GONE);
        }
        boolean isImageviewCheck9Visible = sharedPreferences.getBoolean("background_imageview_check_9_visibility", false);
        if (isImageviewCheck9Visible) {
            background_imageview_check_9.setVisibility(View.VISIBLE);
        } else {
            background_imageview_check_9.setVisibility(View.GONE);
        }
        boolean isImageviewCheck10Visible = sharedPreferences.getBoolean("background_imageview_check_10_visibility", false);
        if (isImageviewCheck10Visible) {
            background_imageview_check_10.setVisibility(View.VISIBLE);
        } else {
            background_imageview_check_10.setVisibility(View.GONE);
        }
        boolean isImageviewCheck11Visible = sharedPreferences.getBoolean("background_imageview_check_11_visibility", false);
        if (isImageviewCheck11Visible) {
            background_imageview_check_11.setVisibility(View.VISIBLE);
        } else {
            background_imageview_check_11.setVisibility(View.GONE);
        }
        boolean isImageviewCheck12Visible = sharedPreferences.getBoolean("background_imageview_check_12_visibility", false);
        if (isImageviewCheck12Visible) {
            background_imageview_check_12.setVisibility(View.VISIBLE);
        } else {
            background_imageview_check_12.setVisibility(View.GONE);
        }


        background_theme1_button.setOnClickListener(v -> {


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                content_linear_background_layout.setBackgroundColor(getColor(R.color.theme_colorgrey));
            }
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("backgroundColor", getColor(R.color.theme_colorgrey));
            editor.putBoolean("imageview_check_1_visibility", true);
            editor.putBoolean("imageview_check_2_visibility", false);
            editor.putBoolean("imageview_check_3_visibility", false);
            editor.putBoolean("imageview_check_4_visibility", false);
            editor.putBoolean("imageview_check_5_visibility", false);
            editor.putBoolean("imageview_check_6_visibility", false);
            editor.putBoolean("imageview_check_7_visibility", false);
            editor.putBoolean("imageview_check_8_visibility", false);
            editor.putBoolean("imageview_check_9_visibility", false);
            editor.putBoolean("imageview_check_10_visibility", false);
            editor.putBoolean("imageview_check_11_visibility", false);
            editor.putBoolean("imageview_check_12_visibility", false);

            editor.apply();

            background_imageview_check_1.setVisibility(View.VISIBLE);
            background_imageview_check_2.setVisibility(View.GONE);
            background_imageview_check_3.setVisibility(View.GONE);
            background_imageview_check_4.setVisibility(View.GONE);
            background_imageview_check_5.setVisibility(View.GONE);
            background_imageview_check_6.setVisibility(View.GONE);
            background_imageview_check_7.setVisibility(View.GONE);
            background_imageview_check_8.setVisibility(View.GONE);
            background_imageview_check_9.setVisibility(View.GONE);
            background_imageview_check_10.setVisibility(View.GONE);
            background_imageview_check_11.setVisibility(View.GONE);
            background_imageview_check_12.setVisibility(View.GONE);


        });

        background_theme2_button.setOnClickListener(v -> {


            content_linear_background_layout.setBackgroundColor(getColor(R.color.theme_colorblue));
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("backgroundColor", getColor(R.color.theme_colorblue));
            editor.putBoolean("background_imageview_check_1_visibility", false);
            editor.putBoolean("background_imageview_check_2_visibility", true);
            editor.putBoolean("background_imageview_check_3_visibility", false);
            editor.putBoolean("background_imageview_check_4_visibility", false);
            editor.putBoolean("background_imageview_check_5_visibility", false);
            editor.putBoolean("background_imageview_check_6_visibility", false);
            editor.putBoolean("background_imageview_check_7_visibility", false);
            editor.putBoolean("background_imageview_check_8_visibility", false);
            editor.putBoolean("background_imageview_check_9_visibility", false);
            editor.putBoolean("background_imageview_check_10_visibility", false);
            editor.putBoolean("background_imageview_check_11_visibility", false);
            editor.putBoolean("background_imageview_check_12_visibility", false);

            editor.apply();


            background_imageview_check_1.setVisibility(View.GONE);
            background_imageview_check_2.setVisibility(View.VISIBLE);
            background_imageview_check_3.setVisibility(View.GONE);
            background_imageview_check_4.setVisibility(View.GONE);
            background_imageview_check_5.setVisibility(View.GONE);
            background_imageview_check_6.setVisibility(View.GONE);
            background_imageview_check_7.setVisibility(View.GONE);
            background_imageview_check_8.setVisibility(View.GONE);
            background_imageview_check_9.setVisibility(View.GONE);
            background_imageview_check_10.setVisibility(View.GONE);
            background_imageview_check_11.setVisibility(View.GONE);
            background_imageview_check_12.setVisibility(View.GONE);

        });

        background_theme3_button.setOnClickListener(v -> {


            content_linear_background_layout.setBackgroundColor(getColor(R.color.theme_colorwhite));
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("backgroundColor", getColor(R.color.theme_colorwhite));
            editor.putBoolean("background_imageview_check_1_visibility", false);
            editor.putBoolean("background_imageview_check_2_visibility", false);
            editor.putBoolean("background_imageview_check_3_visibility", true);
            editor.putBoolean("background_imageview_check_4_visibility", false);
            editor.putBoolean("background_imageview_check_5_visibility", false);
            editor.putBoolean("background_imageview_check_6_visibility", false);
            editor.putBoolean("background_imageview_check_7_visibility", false);
            editor.putBoolean("background_imageview_check_8_visibility", false);
            editor.putBoolean("background_imageview_check_9_visibility", false);
            editor.putBoolean("background_imageview_check_10_visibility", false);
            editor.putBoolean("background_imageview_check_11_visibility", false);
            editor.putBoolean("background_imageview_check_12_visibility", false);
            editor.apply();


            background_imageview_check_1.setVisibility(View.GONE);
            background_imageview_check_2.setVisibility(View.GONE);
            background_imageview_check_3.setVisibility(View.VISIBLE);
            background_imageview_check_4.setVisibility(View.GONE);
            background_imageview_check_5.setVisibility(View.GONE);
            background_imageview_check_6.setVisibility(View.GONE);
            background_imageview_check_7.setVisibility(View.GONE);
            background_imageview_check_8.setVisibility(View.GONE);
            background_imageview_check_9.setVisibility(View.GONE);
            background_imageview_check_10.setVisibility(View.GONE);
            background_imageview_check_11.setVisibility(View.GONE);
            background_imageview_check_12.setVisibility(View.GONE);

        });

        background_theme4_button.setOnClickListener(v -> {


            content_linear_background_layout.setBackgroundColor(getColor(R.color.theme_colorblack));
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("backgroundColor", getColor(R.color.theme_colorblack));
            editor.putBoolean("background_imageview_check_1_visibility", false);
            editor.putBoolean("background_imageview_check_2_visibility", false);
            editor.putBoolean("background_imageview_check_3_visibility", false);
            editor.putBoolean("background_imageview_check_4_visibility", true);
            editor.putBoolean("background_imageview_check_5_visibility", false);
            editor.putBoolean("background_imageview_check_6_visibility", false);
            editor.putBoolean("background_imageview_check_7_visibility", false);
            editor.putBoolean("background_imageview_check_8_visibility", false);
            editor.putBoolean("background_imageview_check_9_visibility", false);
            editor.putBoolean("background_imageview_check_10_visibility", false);
            editor.putBoolean("background_imageview_check_11_visibility", false);
            editor.putBoolean("background_imageview_check_12_visibility", false);
            editor.apply();


            background_imageview_check_1.setVisibility(View.GONE);
            background_imageview_check_2.setVisibility(View.GONE);
            background_imageview_check_3.setVisibility(View.GONE);
            background_imageview_check_4.setVisibility(View.VISIBLE);
            background_imageview_check_5.setVisibility(View.GONE);
            background_imageview_check_6.setVisibility(View.GONE);
            background_imageview_check_7.setVisibility(View.GONE);
            background_imageview_check_8.setVisibility(View.GONE);
            background_imageview_check_9.setVisibility(View.GONE);
            background_imageview_check_10.setVisibility(View.GONE);
            background_imageview_check_11.setVisibility(View.GONE);
            background_imageview_check_12.setVisibility(View.GONE);

        });

        background_theme5_button.setOnClickListener(v -> {


            content_linear_background_layout.setBackgroundColor(getColor(R.color.theme_colorred));
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("backgroundColor", getColor(R.color.theme_colorred));
            editor.putBoolean("background_imageview_check_1_visibility", false);
            editor.putBoolean("background_imageview_check_2_visibility", false);
            editor.putBoolean("background_imageview_check_3_visibility", false);
            editor.putBoolean("background_imageview_check_4_visibility", false);
            editor.putBoolean("background_imageview_check_5_visibility", true);
            editor.putBoolean("background_imageview_check_6_visibility", false);
            editor.putBoolean("background_imageview_check_7_visibility", false);
            editor.putBoolean("background_imageview_check_8_visibility", false);
            editor.putBoolean("background_imageview_check_9_visibility", false);
            editor.putBoolean("background_imageview_check_10_visibility", false);
            editor.putBoolean("background_imageview_check_11_visibility", false);
            editor.putBoolean("background_imageview_check_12_visibility", false);
            editor.apply();


            background_imageview_check_1.setVisibility(View.GONE);
            background_imageview_check_2.setVisibility(View.GONE);
            background_imageview_check_3.setVisibility(View.GONE);
            background_imageview_check_4.setVisibility(View.GONE);
            background_imageview_check_5.setVisibility(View.VISIBLE);
            background_imageview_check_6.setVisibility(View.GONE);
            background_imageview_check_7.setVisibility(View.GONE);
            background_imageview_check_8.setVisibility(View.GONE);
            background_imageview_check_9.setVisibility(View.GONE);
            background_imageview_check_10.setVisibility(View.GONE);
            background_imageview_check_11.setVisibility(View.GONE);
            background_imageview_check_12.setVisibility(View.GONE);

        });

        background_theme6_button.setOnClickListener(v -> {


            content_linear_background_layout.setBackgroundColor(getColor(R.color.theme_coloryellow));
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("backgroundColor", getColor(R.color.theme_coloryellow));
            editor.putBoolean("background_imageview_check_1_visibility", false);
            editor.putBoolean("background_imageview_check_2_visibility", false);
            editor.putBoolean("background_imageview_check_3_visibility", false);
            editor.putBoolean("background_imageview_check_4_visibility", false);
            editor.putBoolean("background_imageview_check_5_visibility", false);
            editor.putBoolean("background_imageview_check_6_visibility", true);
            editor.putBoolean("background_imageview_check_7_visibility", false);
            editor.putBoolean("background_imageview_check_8_visibility", false);
            editor.putBoolean("background_imageview_check_9_visibility", false);
            editor.putBoolean("background_imageview_check_10_visibility", false);
            editor.putBoolean("background_imageview_check_11_visibility", false);
            editor.putBoolean("background_imageview_check_12_visibility", false);
            editor.apply();


            background_imageview_check_1.setVisibility(View.GONE);
            background_imageview_check_2.setVisibility(View.GONE);
            background_imageview_check_3.setVisibility(View.GONE);
            background_imageview_check_4.setVisibility(View.GONE);
            background_imageview_check_5.setVisibility(View.GONE);
            background_imageview_check_6.setVisibility(View.VISIBLE);
            background_imageview_check_7.setVisibility(View.GONE);
            background_imageview_check_8.setVisibility(View.GONE);
            background_imageview_check_9.setVisibility(View.GONE);
            background_imageview_check_10.setVisibility(View.GONE);
            background_imageview_check_11.setVisibility(View.GONE);
            background_imageview_check_12.setVisibility(View.GONE);

        });

        background_theme7_button.setOnClickListener(v -> {


            content_linear_background_layout.setBackgroundColor(getColor(R.color.theme_colororenge));
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("backgroundColor", getColor(R.color.theme_colororenge));
            editor.putBoolean("background_imageview_check_1_visibility", false);
            editor.putBoolean("background_imageview_check_2_visibility", false);
            editor.putBoolean("background_imageview_check_3_visibility", false);
            editor.putBoolean("background_imageview_check_4_visibility", false);
            editor.putBoolean("background_imageview_check_5_visibility", false);
            editor.putBoolean("background_imageview_check_6_visibility", false);
            editor.putBoolean("background_imageview_check_7_visibility", true);
            editor.putBoolean("background_imageview_check_8_visibility", false);
            editor.putBoolean("background_imageview_check_9_visibility", false);
            editor.putBoolean("background_imageview_check_10_visibility", false);
            editor.putBoolean("background_imageview_check_11_visibility", false);
            editor.putBoolean("background_imageview_check_12_visibility", false);
            editor.apply();


            background_imageview_check_1.setVisibility(View.GONE);
            background_imageview_check_2.setVisibility(View.GONE);
            background_imageview_check_3.setVisibility(View.GONE);
            background_imageview_check_4.setVisibility(View.GONE);
            background_imageview_check_5.setVisibility(View.GONE);
            background_imageview_check_6.setVisibility(View.GONE);
            background_imageview_check_7.setVisibility(View.VISIBLE);
            background_imageview_check_8.setVisibility(View.GONE);
            background_imageview_check_9.setVisibility(View.GONE);
            background_imageview_check_10.setVisibility(View.GONE);
            background_imageview_check_11.setVisibility(View.GONE);
            background_imageview_check_12.setVisibility(View.GONE);

        });

        background_theme8_button.setOnClickListener(v -> {


            content_linear_background_layout.setBackgroundColor(getColor(R.color.theme_colorgreen));
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("backgroundColor", getColor(R.color.theme_colorgreen));
            editor.putBoolean("background_imageview_check_1_visibility", false);
            editor.putBoolean("background_imageview_check_2_visibility", false);
            editor.putBoolean("background_imageview_check_3_visibility", false);
            editor.putBoolean("background_imageview_check_4_visibility", false);
            editor.putBoolean("background_imageview_check_5_visibility", false);
            editor.putBoolean("background_imageview_check_6_visibility", false);
            editor.putBoolean("background_imageview_check_7_visibility", false);
            editor.putBoolean("background_imageview_check_8_visibility", true);
            editor.putBoolean("background_imageview_check_9_visibility", false);
            editor.putBoolean("background_imageview_check_10_visibility", false);
            editor.putBoolean("background_imageview_check_11_visibility", false);
            editor.putBoolean("background_imageview_check_12_visibility", false);
            editor.apply();


            background_imageview_check_1.setVisibility(View.GONE);
            background_imageview_check_2.setVisibility(View.GONE);
            background_imageview_check_3.setVisibility(View.GONE);
            background_imageview_check_4.setVisibility(View.GONE);
            background_imageview_check_5.setVisibility(View.GONE);
            background_imageview_check_6.setVisibility(View.GONE);
            background_imageview_check_7.setVisibility(View.GONE);
            background_imageview_check_8.setVisibility(View.VISIBLE);
            background_imageview_check_9.setVisibility(View.GONE);
            background_imageview_check_10.setVisibility(View.GONE);
            background_imageview_check_11.setVisibility(View.GONE);
            background_imageview_check_12.setVisibility(View.GONE);

        });

        background_theme9_button.setOnClickListener(v -> {


            content_linear_background_layout.setBackgroundColor(getColor(R.color.theme_colorpurple));
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("backgroundColor", getColor(R.color.theme_colorpurple));
            editor.putBoolean("background_imageview_check_1_visibility", false);
            editor.putBoolean("background_imageview_check_2_visibility", false);
            editor.putBoolean("background_imageview_check_3_visibility", false);
            editor.putBoolean("background_imageview_check_4_visibility", false);
            editor.putBoolean("background_imageview_check_5_visibility", false);
            editor.putBoolean("background_imageview_check_6_visibility", false);
            editor.putBoolean("background_imageview_check_7_visibility", false);
            editor.putBoolean("background_imageview_check_8_visibility", false);
            editor.putBoolean("background_imageview_check_9_visibility", true);
            editor.putBoolean("background_imageview_check_10_visibility", false);
            editor.putBoolean("background_imageview_check_11_visibility", false);
            editor.putBoolean("background_imageview_check_12_visibility", false);
            editor.apply();


            background_imageview_check_1.setVisibility(View.GONE);
            background_imageview_check_2.setVisibility(View.GONE);
            background_imageview_check_3.setVisibility(View.GONE);
            background_imageview_check_4.setVisibility(View.GONE);
            background_imageview_check_5.setVisibility(View.GONE);
            background_imageview_check_6.setVisibility(View.GONE);
            background_imageview_check_7.setVisibility(View.GONE);
            background_imageview_check_8.setVisibility(View.GONE);
            background_imageview_check_9.setVisibility(View.VISIBLE);
            background_imageview_check_10.setVisibility(View.GONE);
            background_imageview_check_11.setVisibility(View.GONE);
            background_imageview_check_12.setVisibility(View.GONE);

        });

        background_theme10_button.setOnClickListener(v -> {


            content_linear_background_layout.setBackgroundColor(getColor(R.color.theme_colorpink));
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("backgroundColor", getColor(R.color.theme_colorpink));
            editor.putBoolean("background_imageview_check_1_visibility", false);
            editor.putBoolean("background_imageview_check_2_visibility", false);
            editor.putBoolean("background_imageview_check_3_visibility", false);
            editor.putBoolean("background_imageview_check_4_visibility", false);
            editor.putBoolean("background_imageview_check_5_visibility", false);
            editor.putBoolean("background_imageview_check_6_visibility", false);
            editor.putBoolean("background_imageview_check_7_visibility", false);
            editor.putBoolean("background_imageview_check_8_visibility", false);
            editor.putBoolean("background_imageview_check_9_visibility", false);
            editor.putBoolean("background_imageview_check_10_visibility", true);
            editor.putBoolean("background_imageview_check_11_visibility", false);
            editor.putBoolean("background_imageview_check_12_visibility", false);
            editor.apply();


            background_imageview_check_1.setVisibility(View.GONE);
            background_imageview_check_2.setVisibility(View.GONE);
            background_imageview_check_3.setVisibility(View.GONE);
            background_imageview_check_4.setVisibility(View.GONE);
            background_imageview_check_5.setVisibility(View.GONE);
            background_imageview_check_6.setVisibility(View.GONE);
            background_imageview_check_7.setVisibility(View.GONE);
            background_imageview_check_8.setVisibility(View.GONE);
            background_imageview_check_9.setVisibility(View.GONE);
            background_imageview_check_10.setVisibility(View.VISIBLE);
            background_imageview_check_11.setVisibility(View.GONE);
            background_imageview_check_12.setVisibility(View.GONE);

        });

        background_theme11_button.setOnClickListener(v -> {


            content_linear_background_layout.setBackgroundColor(getColor(R.color.theme_colorbrowen));
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("backgroundColor", getColor(R.color.theme_colorbrowen));
            editor.putBoolean("background_imageview_check_1_visibility", false);
            editor.putBoolean("background_imageview_check_2_visibility", false);
            editor.putBoolean("background_imageview_check_3_visibility", false);
            editor.putBoolean("background_imageview_check_4_visibility", false);
            editor.putBoolean("background_imageview_check_5_visibility", false);
            editor.putBoolean("background_imageview_check_6_visibility", false);
            editor.putBoolean("background_imageview_check_7_visibility", false);
            editor.putBoolean("background_imageview_check_8_visibility", false);
            editor.putBoolean("background_imageview_check_9_visibility", false);
            editor.putBoolean("background_imageview_check_10_visibility", false);
            editor.putBoolean("background_imageview_check_11_visibility", true);
            editor.putBoolean("background_imageview_check_12_visibility", false);
            editor.apply();


            background_imageview_check_1.setVisibility(View.GONE);
            background_imageview_check_2.setVisibility(View.GONE);
            background_imageview_check_3.setVisibility(View.GONE);
            background_imageview_check_4.setVisibility(View.GONE);
            background_imageview_check_5.setVisibility(View.GONE);
            background_imageview_check_6.setVisibility(View.GONE);
            background_imageview_check_7.setVisibility(View.GONE);
            background_imageview_check_8.setVisibility(View.GONE);
            background_imageview_check_9.setVisibility(View.GONE);
            background_imageview_check_10.setVisibility(View.GONE);
            background_imageview_check_11.setVisibility(View.VISIBLE);
            background_imageview_check_12.setVisibility(View.GONE);

        });

        background_theme12_button.setOnClickListener(v -> {


            content_linear_background_layout.setBackgroundColor(getColor(R.color.theme_colorbeige));
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("backgroundColor", getColor(R.color.theme_colorbeige));
            editor.putBoolean("background_imageview_check_1_visibility", false);
            editor.putBoolean("background_imageview_check_2_visibility", false);
            editor.putBoolean("background_imageview_check_3_visibility", false);
            editor.putBoolean("background_imageview_check_4_visibility", false);
            editor.putBoolean("background_imageview_check_5_visibility", false);
            editor.putBoolean("background_imageview_check_6_visibility", false);
            editor.putBoolean("background_imageview_check_7_visibility", false);
            editor.putBoolean("background_imageview_check_8_visibility", false);
            editor.putBoolean("background_imageview_check_9_visibility", false);
            editor.putBoolean("background_imageview_check_10_visibility", false);
            editor.putBoolean("background_imageview_check_11_visibility", false);
            editor.putBoolean("background_imageview_check_12_visibility", true);
            editor.apply();


            background_imageview_check_1.setVisibility(View.GONE);
            background_imageview_check_2.setVisibility(View.GONE);
            background_imageview_check_3.setVisibility(View.GONE);
            background_imageview_check_4.setVisibility(View.GONE);
            background_imageview_check_5.setVisibility(View.GONE);
            background_imageview_check_6.setVisibility(View.GONE);
            background_imageview_check_7.setVisibility(View.GONE);
            background_imageview_check_8.setVisibility(View.GONE);
            background_imageview_check_9.setVisibility(View.GONE);
            background_imageview_check_10.setVisibility(View.GONE);
            background_imageview_check_11.setVisibility(View.GONE);
            background_imageview_check_12.setVisibility(View.VISIBLE);

        });

    }


    private void Copy() {

        String title = titel_textview.getText().toString();
        String content = body.getText().toString();
        String link = "* Download more our apps from the link given below :  https://play.google.com/store/apps/developer?id=Yeasin+Rabbi";

        String copy = title.concat("\n\n".concat(content.concat("\n\n".concat(link))));
        ContentActivity contentActivity = ContentActivity.this;
        ContentActivity.this.getApplicationContext();
        ((ClipboardManager) contentActivity.getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("clipboard", copy));
        Toast.makeText(getApplicationContext(), "কপি সম্পন্ন হয়েছে", Toast.LENGTH_SHORT).show();


    }

    private void Share() {
        String title = titel_textview.getText().toString();
        String content = body.getText().toString();
        String link = "* Download more our apps from the link given below :  https://play.google.com/store/apps/developer?id=Yeasin+Rabbi";

        String share = title.concat("\n\n".concat(content.concat("\n\n".concat(link))));
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("text/plain");
        intent.putExtra("android.intent.extra.TEXT", share);
        intent.putExtra("android.intent.extra.SUBJECT", "Share Quotation from Humayon Ahmed Samagra Yeasin Rabbi");
        startActivity(Intent.createChooser(intent, "Share text using"));

    }

    // Auto-scroll the text view


    private void rvpanel() {

        RecyclerView rv;
        List<ListModel> listModelList;
        ListAdapter adapter;


        rv = findViewById(R.id.crv);
        listModelList = new ArrayList<>();


        String json = Utils.loadJsonfromcachdir(this, Utils.getJson(type));


        if (json != null) {

            try {


                JSONObject jsonObject = new JSONObject(json);

                JSONArray array = jsonObject.getJSONArray("data");


                for (int i = 0; i < array.length(); i++) {


                    JSONObject object = array.getJSONObject(i);

                    if (object.getString("title").equals(category)) {


                        JSONArray list = object.getJSONArray("content");

                        for (int j = 0; j < list.length(); j++) {
                            int n = j + 1;
                            JSONObject ob = list.getJSONObject(j);

                            ListModel model = new ListModel(
                                    Utils.conNum(String.valueOf(n)),
                                    ob.getString("title"),
                                    ""
                            );

                            listModelList.add(model);


                        }

                    }


                }


                adapter = new ListAdapter(listModelList, 3, type);
                adapter.setTitle(title);

                LinearLayoutManager manager = new LinearLayoutManager(this);

                rv.setLayoutManager(manager);
                rv.setAdapter(adapter);


                for (int j = 0; j < listModelList.size(); j++) {

                    if (listModelList.get(j).getTitle().equals(title)) {
                        manager.scrollToPosition(j);
                    }

                }


                adapter.addCb(new ListAdapter.onClick() {
                    @Override
                    public void click(String title1, int type1) {

                        title = title1;
                        type = type1;
                        left_panel.animate().translationX(-width).setDuration(300);
                        loadContent();
                        isFav();


                    }
                });


            } catch (JSONException e) {
                throw new RuntimeException(e);
            }


        }


    }

    @Override
    public void onBackPressed() {

        if (left_panel.getTranslationX() == 0) {
            left_panel.animate().translationX(-width).setDuration(300);
        } else {

            super.onBackPressed();

        }


    }

    private void ratingDialog() {

        SaveData saveData = new SaveData(this);
        if (saveData.getInt("rating") >= Constant.ratingShow && !saveData.getBoolean("rating_show")) {


            RatingDialog ratingDialog = new RatingDialog(this);
            ratingDialog.show();
            ratingDialog.setCancelable(true);


            saveData.save("rating", 0);


            TextView rateing_moreappsID = ratingDialog.findViewById(R.id.rateing_moreappsID);
            TextView rateting_cancel_ID = ratingDialog.findViewById(R.id.rateting_cancel_ID);
            TextView rateingPossitiveID = ratingDialog.findViewById(R.id.rateingPossitiveID);


            rateing_moreappsID.setOnClickListener(v -> {


                saveData.save("rating_show", true);

                ratingDialog.cancel();


            });

            rateting_cancel_ID.setOnClickListener(v -> {

                ratingDialog.cancel();

            });


            rateingPossitiveID.setOnClickListener(v -> {

                Rate_Apps();
                ratingDialog.cancel();
                saveData.save("rating_show", true);

            });

        } else {

            saveData.save("rating", saveData.getInt("rating") + 1);

        }


    }

    public void More_Apps() {

        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.setData(Uri.parse("https://play.google.com/store/apps/developer?id=" + Constant.developer_id));
        startActivity(intent);
    }


    public void Rate_Apps() {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + Constant.packageName));
        startActivity(intent);
    }


}