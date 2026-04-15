package com.yeasinrabbee.zafariqbal.Beans;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yeasinrabbee.zafariqbal.Activities.ListOfContentActivity;
import com.yeasinrabbee.zafariqbal.Cm.ContentActivity;
import com.yeasinrabbee.zafariqbal.Db.Helper;
import com.yeasinrabbee.zafariqbal.R;
import com.yeasinrabbee.zafariqbal.Utils.SaveData;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ListModel> listModelList;
    private Context context;
    private int type = 1;

    private int bookType = 0;

    private String[] colors = {"#6F51FE", "#FEAE03", "#4CAF50", "#9C27B0", "#EC1E79", "#03A9F4", "#FF5722", "#009688", "#00BCD4"};
    private onClick cb;

    private String title = "";
    private int colorCount = 0;

    private int previous = -1;


    public static int view_1 = 1;
    public static int view_2 = 2;

    private int view = 1;

    private Helper helper;

    private onAdClick onAD;


    public ListAdapter(List<ListModel> listModelList, int type, int bookType) {
        this.listModelList = listModelList;
        this.type = type;
        this.bookType = bookType;

    }

    public ListAdapter(List<ListModel> listModelList, int type, int bookType, int view) {
        this.listModelList = listModelList;
        this.type = type;
        this.bookType = bookType;
        this.view = view;


    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        context = parent.getContext();
        helper = new Helper(context);

        if (view == view_1) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sample, parent, false);
            return new VH(view);
        }

        if (view == view_2) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.second_sample, parent, false);
            return new VH2(view);
        }

        return null;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof VH) {
            vh((VH) holder, position);
        }

        if (holder instanceof VH2) {
            vh2((VH2) holder, position);
        }

    }


    public void vh(VH holder, int p) {

        int position = holder.getAdapterPosition();

        holder.textview1.setText(listModelList.get(position).getTitle());
        holder.textview2.setText(listModelList.get(position).getPosition());
        holder.textview3.setText(listModelList.get(position).getTotal());

        holder.item.setOnClickListener(v -> {


            if (type == 1) {


                Intent intent = new Intent(context, ListOfContentActivity.class);
                intent.putExtra("title", listModelList.get(position).getTitle());
                intent.putExtra("type", bookType);
                intent.putExtra("name", listModelList.get(position).getCategory());
                context.startActivity(intent);

            }

            if (type == 2) {


                if (onAD != null)
                    onAD.click(2, listModelList.get(position).getTitle(), listModelList.get(position).getCategory(), bookType);

            }


            if (type == 3) {

                if (cb != null) cb.click(listModelList.get(position).getTitle(), bookType);

            }

            saveData(listModelList.get(position).getTitle(), position);


        });


        if (listModelList.get(position).getTotal().isEmpty()) {
            holder.textview3.setVisibility(View.GONE);
        } else {
            holder.textview3.setVisibility(View.VISIBLE);
        }


        holder.linear_circle.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(colors[colorCount])));

        colorCount += 1;

        if (colorCount == colors.length - 1) colorCount = 0;


        if (title.equals(listModelList.get(position).getTitle())) {
            holder.item.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#3B4CAF50")));
        } else {
            holder.item.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ffffff")));
        }


        if (getTitle(listModelList.get(position).getTitle())) {

            holder.bookview_layout1.setBackgroundResource(R.drawable.cardview_background);
            previous = position;

        } else {

            holder.bookview_layout1.setBackgroundResource(R.drawable.cardview_background_s);


        }

        holder.category_icon.setImageResource(Category.getIcon(context, listModelList.get(position).getCategory()));


    }

    public void vh2(VH2 holder, int p) {

        int position = holder.getAdapterPosition();

        holder.textview1.setText(listModelList.get(position).getTitle());
        holder.textview2.setText(listModelList.get(position).getPosition());
        holder.textview3.setText(listModelList.get(position).getTotal());

        holder.item.setOnClickListener(v -> {


            if (type == 1) {

                if (onAD != null)
                    onAD.click(1, listModelList.get(position).getTitle(), listModelList.get(position).getCategory(), bookType);
            }

            if (type == 2) {
                if (onAD != null)
                    onAD.click(2, listModelList.get(position).getTitle(), listModelList.get(position).getCategory(), bookType);
            }

              /*if (type == 1) {
                    Intent intent = new Intent(context, ListOfContentActivity.class);
                    intent.putExtra("title",listModelList.get(position).getTitle());
                    intent.putExtra("type",bookType);
                    context.startActivity(intent);
                }
                Intent intent = new Intent(context, ContentActivity.class);
                intent.putExtra("title",listModelList.get(position).getTitle());
                intent.putExtra("type",bookType);
                intent.putExtra("name",listModelList.get(position).getCategory());
                context.startActivity(intent);*/


            if (type == 3) {

                if (cb != null) cb.click(listModelList.get(position).getTitle(), bookType);

            }

            saveData(listModelList.get(position).getTitle(), position);


        });


        if (listModelList.get(position).getTotal().isEmpty()) {
            holder.textview3.setVisibility(View.GONE);
        } else {
            holder.textview3.setVisibility(View.VISIBLE);
        }


        holder.linear_circle.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(colors[colorCount])));

        colorCount += 1;

        if (colorCount == colors.length - 1) colorCount = 0;


        if (title.equals(listModelList.get(position).getTitle())) {
            holder.item.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#3B4CAF50")));
        } else {
            holder.item.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ffffff")));
        }


        if (getTitle(listModelList.get(position).getTitle())) {

            holder.bookview_layout1.setBackgroundResource(R.drawable.cardview_background);
            previous = position;

        } else {

            holder.bookview_layout1.setBackgroundResource(R.drawable.cardview_background_s);


        }

        holder.category_icon.setImageResource(Category.getIcon(context, listModelList.get(position).getCategory()));
        holder.square.setBackgroundResource(Category.getBg(context, listModelList.get(position).getCategory()));


        Cursor cursor = helper.getAt(listModelList.get(position).getTitle(), Helper.TYPE_FAV);

        if (cursor != null && cursor.getCount() > 0) {
            holder.is_fav.setVisibility(View.VISIBLE);

        } else {
            holder.is_fav.setVisibility(View.GONE);
        }


    }

    private void saveData(String title, int p) {

        new SaveData(context).save("active_title", title);

        notifyItemChanged(previous);
        notifyItemChanged(p);

        previous = p;


    }

    private boolean getTitle(String title) {

        String t = new SaveData(context).getData("active_title");

        if (t.equals(title))
            return true;
        else
            return false;


    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int getItemCount() {
        return listModelList.size();
    }

    public static class VH extends RecyclerView.ViewHolder {
        LinearLayout item;
        TextView textview1, textview2, textview3;
        LinearLayout linear_circle;

        LinearLayout bookview_layout1;
        ImageView category_icon;

        public VH(@NonNull View itemView) {


            super(itemView);
            textview1 = itemView.findViewById(R.id.textview1);
            textview2 = itemView.findViewById(R.id.textview2);
            textview3 = itemView.findViewById(R.id.textview3);
            item = itemView.findViewById(R.id.item);
            linear_circle = item.findViewById(R.id.linear_circle);
            bookview_layout1 = item.findViewById(R.id.bookview_layout1);
            category_icon = itemView.findViewById(R.id.category_icon);
        }
    }


    public static class VH2 extends RecyclerView.ViewHolder {
        LinearLayout item;
        TextView textview1, textview2, textview3;
        LinearLayout linear_circle;

        LinearLayout bookview_layout1;

        ImageView category_icon;
        LinearLayout square;
        ImageView is_fav;

        public VH2(@NonNull View itemView) {


            super(itemView);
            textview1 = itemView.findViewById(R.id.textview1);
            textview2 = itemView.findViewById(R.id.textview2);
            textview3 = itemView.findViewById(R.id.textview3);
            item = itemView.findViewById(R.id.item);
            linear_circle = item.findViewById(R.id.linear_circle);
            bookview_layout1 = item.findViewById(R.id.bookview_layout1);
            category_icon = itemView.findViewById(R.id.category_icon);
            square = item.findViewById(R.id.square);
            is_fav = item.findViewById(R.id.is_fav);

        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void addData(List<ListModel> listModelList) {
        this.listModelList = listModelList;
        notifyDataSetChanged();

    }

    public void addCb(onClick cb) {
        this.cb = cb;
    }

    public void cb(onAdClick cb) {

        this.onAD = cb;

    }

    public interface onClick {
        void click(String title, int type);
    }

    public interface onAdClick {
        void click(int type, String title, String name, int booktype);
    }


}
