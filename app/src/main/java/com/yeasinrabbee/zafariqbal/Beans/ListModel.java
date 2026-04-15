package com.yeasinrabbee.zafariqbal.Beans;

public class ListModel {

    String position,title,total,category;

    public ListModel(String position, String title, String total) {
        this.position = position;
        this.title = title;
        this.total = total;
    }

    public ListModel(String position, String title, String total, String category) {
        this.position = position;
        this.title = title;
        this.total = total;
        this.category = category;
    }


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
