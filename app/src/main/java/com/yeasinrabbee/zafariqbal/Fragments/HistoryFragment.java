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


public class HistoryFragment extends Fragment {

    private List<HistoryModel> historyModelList;
    private HistoryAdapter historyAdapter;

    private  Cursor cursor;
    private AlertDialog builder;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
         View view = inflater.inflate(R.layout.fragment_history, container, false);

        RecyclerView history_rv = view.findViewById(R.id.history_rv);

        ImageView history_all_delete_imageview = view.findViewById(R.id.history_all_delete_imageview);
        history_all_delete_imageview.setOnClickListener(v -> history_delete_dialog());


        historyModelList = new ArrayList<>();
        historyAdapter = new HistoryAdapter(historyModelList);
        history_rv.setLayoutManager(new LinearLayoutManager(requireActivity()));
        history_rv.setAdapter(historyAdapter);

        return view;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
    public void loadHistory() {

        cursor = new Helper(requireActivity()).getAllHistory();

        if (cursor != null && cursor.getCount() > 0) {

            historyModelList.clear();

            while (cursor.moveToNext()) {

                HistoryModel model = new HistoryModel(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getLong(5),
                        2
                );


                historyModelList.add(model);

            }


            historyAdapter.notifyDataSetChanged();


        }

    }

    private void history_delete_dialog() {

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

            new Helper(requireActivity()).deleteHis();
            historyModelList.clear();
            historyAdapter.notifyDataSetChanged();


        });
        textView4.setOnClickListener(v -> builder.dismiss());
        textView5.setOnClickListener(v -> {

            new Helper(requireActivity()).deleteHis();
            historyModelList.clear();
            historyAdapter.notifyDataSetChanged();
            builder.dismiss();
            Toast.makeText(requireActivity(), "Deleted successfully", Toast.LENGTH_SHORT).show();


        });



        builder = alertDialog.create();
        builder.show();

    }

    @Override
    public void onResume() {
        super.onResume();
        loadHistory();

    }
}