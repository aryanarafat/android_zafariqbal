package com.yeasinrabbee.zafariqbal.Beans;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yeasinrabbee.zafariqbal.Beans.HistoryModel;
import com.yeasinrabbee.zafariqbal.Cm.ContentActivity;
import com.yeasinrabbee.zafariqbal.Db.Helper;
import com.yeasinrabbee.zafariqbal.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.VH> {
    private final List<HistoryModel> historyModelList;
    private Context context;

    private int previous = -1;


    public HistoryAdapter(List<HistoryModel> historyModelList) {
        this.historyModelList = historyModelList;

    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_history_item,parent,false);




        return new VH(view);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull VH holder, @SuppressLint("RecyclerView") int position) {



        holder.title.setText(historyModelList.get(position).getTitle());
        holder.category.setText(historyModelList.get(position).getCategory());
        holder.time.setText(new Date(historyModelList.get(position).getTime()).toLocaleString());

        holder.history_background.setBackgroundResource(Category.getBg(context,historyModelList.get(position).getMainCategory()));
        holder.history_icon.setImageResource(Category.getIcon(context, historyModelList.get(position).getMainCategory()));






        holder.del.setImageResource(R.drawable.delete);
/*
                holder.item.setBackgroundResource(R.drawable.dark_item_background);
*/



        holder.del.setOnClickListener(v-> new AlertDialog.Builder(context)
                .setTitle("Delete history")
                .setMessage("Are you sure you want to delete this history?")
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {

                    if (historyModelList.size()>0) {
                        new Helper(context).delete(historyModelList.get(position).getId(), () -> {
                            historyModelList.remove(position);
                            notifyDataSetChanged();
                        });

                    }

                })

                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show());

        holder.item.setOnLongClickListener(v -> {

            new AlertDialog.Builder(context)
                    .setTitle("Delete history")
                    .setMessage("Are you sure you want to delete this history?")
                    .setPositiveButton(android.R.string.yes, (dialog, which) -> {

                        if (historyModelList.size()>0) {
                            new Helper(context).delete(historyModelList.get(position).getId(), () -> {
                                historyModelList.remove(position);
                                notifyDataSetChanged();

                            });
                        }
                    })

                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();


            return true;
        });


        holder.item.setOnClickListener(v->{

            Intent intent = new Intent(context, ContentActivity.class);
            intent.putExtra("title",historyModelList.get(position).getTitle());
            intent.putExtra("type", Integer.parseInt(historyModelList.get(position).getContent()));
            intent.putExtra("name", historyModelList.get(position).getMainCategory());
            context.startActivity(intent);

        });

    }


    @Override
    public int getItemCount() {
        return historyModelList.size();
    }

    public static class VH extends RecyclerView.ViewHolder {
        TextView title,category,time;
        LinearLayout item;
        ImageView del;
        ImageView history_icon;
        LinearLayout history_background;
        LinearLayout bookview_layout1;

        public VH(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.adapter_title);
            category = itemView.findViewById(R.id.category);

            time = itemView.findViewById(R.id.time);
            item = itemView.findViewById(R.id.item);
            del = itemView.findViewById(R.id.del);
            history_icon = itemView.findViewById(R.id.history_icon);
            history_background = itemView.findViewById(R.id.history_background);
            bookview_layout1 = itemView.findViewById(R.id.bookview_layout1);







        }



    }


    public List<Integer> getColor() {

        List <Integer> colored = new ArrayList<>();

        colored.add(R.color.arsenic);
        colored.add(R.color.blue);
        colored.add(R.color.black);

        return colored;

    }



}
