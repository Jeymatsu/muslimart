package com.example.muslimart2.Model;

public class Products {
    public String productdescription,category,date,image,productname,productprice,time,pid;
    public Products(){

    }

    public String getProductdescription() {
        return productdescription;
    }

    public void setProductdescription(String productdescription) {
        this.productdescription = productdescription;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getProductprice() {
        return productprice;
    }

    public void setProductprice(String productprice) {
        this.productprice = productprice;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public Products(String productdescription, String category, String date, String image, String productname, String productprice, String time, String pid) {
        this.productdescription = productdescription;
        this.category = category;
        this.date = date;
        this.image = image;
        this.productname = productname;
        this.productprice = productprice;
        this.time = time;
        this.pid = pid;
    }
}
