package com.yeasinrabbee.zafariqbal.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.yeasinrabbee.zafariqbal.Activities.ListActivity;
import com.yeasinrabbee.zafariqbal.Cm.MainActivity;
import com.yeasinrabbee.zafariqbal.R;
import com.yeasinrabbee.zafariqbal.Utils.Constant;
import com.yeasinrabbee.zafariqbal.Utils.Utils;


public class HomeFragment extends Fragment implements View.OnClickListener {

    private LinearLayout jiboni;
    private LinearLayout upanash;
    private LinearLayout science;
    private LinearLayout bises_rochona;
    private LinearLayout sada_shide_kotha;
    private LinearLayout golpo;
    private LinearLayout amar_science_mama;
    private LinearLayout aro_aktukhani_biggan;
    private LinearLayout akdojon_akjon;
    private LinearLayout aktu_khani_biggan;
    private LinearLayout kabil;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home_s, container, false);


        jiboni = view.findViewById(R.id.layout1);
        upanash = view.findViewById(R.id.layout2);
        science = view.findViewById(R.id.layout3);
        bises_rochona = view.findViewById(R.id.layout4);
        sada_shide_kotha = view.findViewById(R.id.layout5);
        golpo = view.findViewById(R.id.layout6);
        amar_science_mama = view.findViewById(R.id.layout7);
        aro_aktukhani_biggan = view.findViewById(R.id.layout8);
        akdojon_akjon = view.findViewById(R.id.layout9);
        aktu_khani_biggan = view.findViewById(R.id.layout10);
        kabil = view.findViewById(R.id.layout11);

        jiboni.setOnClickListener(this);
        upanash.setOnClickListener(this);
        science.setOnClickListener(this);
        bises_rochona.setOnClickListener(this);
        sada_shide_kotha.setOnClickListener(this);
        golpo.setOnClickListener(this);
        amar_science_mama.setOnClickListener(this);
        aro_aktukhani_biggan.setOnClickListener(this);
        akdojon_akjon.setOnClickListener(this);
        aktu_khani_biggan.setOnClickListener(this);
        kabil.setOnClickListener(this);


        view.findViewById(R.id.open_menu).setOnClickListener(v->{

            ((MainActivity) requireActivity()).open();

        });

        Utils.extract(requireActivity(), new Utils.OnComplete() {
            @Override
            public void done(String msg) {
                //  Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("tagd", "Home distory");
    }


    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.layout1) {
            Intent intent = new Intent(requireActivity().getApplication(), ListActivity.class);
            intent.putExtra("Type", Constant.Constant1);
            intent.putExtra("name", getString(R.string.title_1));
            startActivity(intent);
        }
        if (view.getId() == R.id.layout2) {
            Intent intent = new Intent(requireActivity().getApplication(), ListActivity.class);
            intent.putExtra("Type", Constant.Constant2);
            intent.putExtra("name", getString(R.string.title_2));
            startActivity(intent);
        }
        if (view.getId() == R.id.layout3) {
            Intent intent = new Intent(requireActivity().getApplication(), ListActivity.class);
            intent.putExtra("Type", Constant.Constant3);
            intent.putExtra("name", getString(R.string.title_3));
            startActivity(intent);
        }
        if (view.getId() == R.id.layout4) {
            Intent intent = new Intent(requireActivity().getApplication(), ListActivity.class);
            intent.putExtra("Type", Constant.Constant4);
            intent.putExtra("name", getString(R.string.title_4));
            startActivity(intent);
        }
        if (view.getId() == R.id.layout5) {
            Intent intent = new Intent(requireActivity().getApplication(), ListActivity.class);
            intent.putExtra("Type", Constant.Constant5);
            intent.putExtra("name", getString(R.string.title_5));
            startActivity(intent);
        }
        if (view.getId() == R.id.layout6) {
            Intent intent = new Intent(requireActivity().getApplication(), ListActivity.class);
            intent.putExtra("Type", Constant.Constant6);
            intent.putExtra("name", getString(R.string.title_6));
            startActivity(intent);
        }

        if (view.getId() == R.id.layout7) {
            Intent intent = new Intent(requireActivity().getApplication(), ListActivity.class);
            intent.putExtra("Type", Constant.Constant7);
            intent.putExtra("name", getString(R.string.title_7));
            startActivity(intent);
        }
        if (view.getId() == R.id.layout8) {
            Intent intent = new Intent(requireActivity().getApplication(), ListActivity.class);
            intent.putExtra("Type", Constant.Constant8);
            intent.putExtra("name", getString(R.string.title_8));
            startActivity(intent);
        }    if (view.getId() == R.id.layout9) {
            Intent intent = new Intent(requireActivity().getApplication(), ListActivity.class);
            intent.putExtra("Type", Constant.Constant9);
            intent.putExtra("name", getString(R.string.title_9));
            startActivity(intent);
        }    if (view.getId() == R.id.layout10) {
            Intent intent = new Intent(requireActivity().getApplication(), ListActivity.class);
            intent.putExtra("Type", Constant.Constant10);
            intent.putExtra("name", getString(R.string.title_10));
            startActivity(intent);

        }
         if (view.getId() == R.id.layout11) {
            Intent intent = new Intent(requireActivity().getApplication(), ListActivity.class);
            intent.putExtra("Type", Constant.Constant11);
            intent.putExtra("name", getString(R.string.title_11));
            startActivity(intent);
        }


    }


}