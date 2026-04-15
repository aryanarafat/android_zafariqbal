package com.yeasinrabbee.zafariqbal.Fragments;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.yeasinrabbee.zafariqbal.Beans.HistoryAdapter;
import com.yeasinrabbee.zafariqbal.Beans.HistoryModel;
import com.yeasinrabbee.zafariqbal.Db.Helper;
import com.yeasinrabbee.zafariqbal.R;

import java.util.ArrayList;
import java.util.List;


public class FavoriteFragment extends Fragment {


    private List<HistoryModel> favModelList;
    private HistoryAdapter favAdapter;

    ImageView favorite_all_delete_imageview;
    private AlertDialog builder;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        TextView mTextView = view.findViewById(R.id.Favorite_Titeltextview); // Replace text_view_id with the actual ID of the TextView


        mTextView.setOnClickListener(v -> requireActivity().onBackPressed());

        favorite_all_delete_imageview = view.findViewById(R.id.favorite_all_delete_imageview);


        favorite_all_delete_imageview.setOnClickListener(v -> favorite_delete_dialog());


        RecyclerView fav_rv = view.findViewById(R.id.fav_rv);



        favModelList = new ArrayList<>();
        favAdapter = new HistoryAdapter(favModelList);
        fav_rv.setLayoutManager(new LinearLayoutManager(requireActivity()));
        fav_rv.setAdapter(favAdapter);


        return view;
    }

    private void favorite_delete_dialog() {

        LayoutInflater inflater = getLayoutInflater();
        View dialoglayout = inflater.inflate(R.layout.dialog_history, null);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(requireActivity());
        alertDialog.setView(dialoglayout);


        TextView textView2 = dialoglayout.findViewById(R.id.textView2);
        TextView textView3 = dialoglayout.findViewById(R.id.textView3);
        TextView textView4 = dialoglayout.findViewById(R.id.textView4);
        TextView textView5 = dialoglayout.findViewById(R.id.textView5);

        textView2.setText(R.string.delete_dailog);

        textView3.setOnClickListener(v -> {

            new Helper(requireActivity()).deleteFav();
            favModelList.clear();
            favAdapter.notifyDataSetChanged();


        });
        textView4.setOnClickListener(v -> builder.dismiss());
        textView5.setOnClickListener(v -> {

            new Helper(requireActivity()).deleteFav();
            favModelList.clear();
            favAdapter.notifyDataSetChanged();

            builder.dismiss();
            Toast.makeText(requireActivity(), "Deleted successfully", Toast.LENGTH_SHORT).show();


        });



        builder = alertDialog.create();
        builder.show();

    }

    public void loadFav() {

        Cursor cursor = new Helper(requireActivity()).getAllFav();

        if (cursor != null && cursor.getCount() > 0) {

            favModelList.clear();

            while (cursor.moveToNext()) {

                HistoryModel model = new HistoryModel(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getLong(5),
                        1
                );


                favModelList.add(model);

            }


            favAdapter.notifyDataSetChanged();


        }

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("tagd","fav distory");
    }

    @Override
    public void onResume() {
        super.onResume();
        loadFav();
    }

}