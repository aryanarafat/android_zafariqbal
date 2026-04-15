package com.yeasinrabbee.zafariqbal.Beans;

import android.content.Context;


import com.yeasinrabbee.zafariqbal.R;

import java.util.HashMap;

public class Category {

    private static HashMap<String,Integer> hashMap = new HashMap<>();
    private static HashMap<String,Integer> background = new HashMap<>();

    public static void add(Context context){

        hashMap.put(context.getString(R.string.title_1), R.drawable.writer);
        hashMap.put(context.getString(R.string.title_2), R.drawable.icon8);
        hashMap.put(context.getString(R.string.title_3), R.drawable.icon6);
        hashMap.put(context.getString(R.string.title_4), R.drawable.icon29);
        hashMap.put(context.getString(R.string.title_5), R.drawable.icon22);
        hashMap.put(context.getString(R.string.title_6), R.drawable.icon19);
        hashMap.put(context.getString(R.string.title_7), R.drawable.icon5);
        hashMap.put(context.getString(R.string.title_8), R.drawable.science);
        hashMap.put(context.getString(R.string.title_9), R.drawable.icon9);
        hashMap.put(context.getString(R.string.title_10), R.drawable.icon32);
        hashMap.put(context.getString(R.string.title_11), R.drawable.biography);

    }


    public static void addColor(Context context){

        background.put(context.getString(R.string.title_1), R.drawable.linear_background_1);
        background.put(context.getString(R.string.title_2),  R.drawable.linear_background_2);
        background.put(context.getString(R.string.title_3),  R.drawable.linear_background_3);
        background.put(context.getString(R.string.title_4), R.drawable.linear_background_4);
        background.put(context.getString(R.string.title_5), R.drawable.linear_background_5);
        background.put(context.getString(R.string.title_6), R.drawable.linear_background_6);
        background.put(context.getString(R.string.title_7),  R.drawable.linear_background_7);
        background.put(context.getString(R.string.title_8),  R.drawable.linear_background_8);
        background.put(context.getString(R.string.title_9),  R.drawable.linear_background_9);
        background.put(context.getString(R.string.title_10),  R.drawable.linear_background_10);
        background.put(context.getString(R.string.title_11),  R.drawable.linear_background_11);



    }

    public static int getIcon(Context context,String category){

        if (hashMap.size() == 0){
            add(context);
        }

        if (hashMap.containsKey(category)){

            return  hashMap.get(category);
        }else {
            return 0;
        }


    }

    public static int getBg(Context context,String category){

        if (background.size() == 0){
            addColor(context);
        }

        if (background.containsKey(category)){

            return background.get(category);
        }else {
            return 0;
        }
    }


}
