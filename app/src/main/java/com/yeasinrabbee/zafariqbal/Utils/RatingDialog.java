package com.yeasinrabbee.zafariqbal.Utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.yeasinrabbee.zafariqbal.R;


public class RatingDialog extends Dialog {


    public RatingDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rating_dialog);

        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


        TextView rateing_moreappsID = findViewById(R.id.rateing_moreappsID);
        TextView rateting_cancel_ID = findViewById(R.id.rateting_cancel_ID);
        TextView rateingPossitiveID = findViewById(R.id.rateingPossitiveID);


        rateing_moreappsID.setOnClickListener(v -> {



        });

        rateting_cancel_ID.setOnClickListener(v -> {


        });


        rateingPossitiveID.setOnClickListener(v -> {


        });

    }
}
