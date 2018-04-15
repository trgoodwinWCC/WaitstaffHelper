package edu.wccnet.waitstaffhelper;

import java.io.Serializable;

public class EntreeItems implements Serializable{
    private String eItemName;
    private String eItemPrice;
    private String eItemUrl;

    private EntreeItems() {} //Needed for firebase

    public EntreeItems(String name, String price, String imageUrl) {
        this.eItemName = name;
        this.eItemPrice = price;
        this.eItemUrl = imageUrl;
    }

    public String getname() {
        return eItemName;
    }

    public void setname(String name) {
        this.eItemName = name;
    }

    public String getprice() {
        return eItemPrice;
    }

    public void setprice(String price) {
        this.eItemPrice = price;
    }

    public String getimageUrl() {
        return eItemUrl;
    }

    public void setimageUrl(String imageUrl) {
        this.eItemUrl = imageUrl;
    }
}
