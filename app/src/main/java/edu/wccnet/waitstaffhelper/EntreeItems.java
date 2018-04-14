package edu.wccnet.waitstaffhelper;

public class EntreeItems {
    private String eItemName;
    private String eItemPrice;
    private String eItemUrl;

    public EntreeItems() {} //Needed for firebase

    public EntreeItems(String eItemName, String eItemPrice, String eItemUrl) {
        this.eItemName = eItemName;
        this.eItemPrice = eItemPrice;
        this.eItemUrl = eItemUrl;
    }

    public String geteItemName() {
        return eItemName;
    }

    public void seteItemName(String eItemName) {
        this.eItemName = eItemName;
    }

    public String geteItemPrice() {
        return eItemPrice;
    }

    public void seteItemPrice(String eItemPrice) {
        this.eItemPrice = eItemPrice;
    }

    public String geteItemUrl() {
        return eItemUrl;
    }

    public void seteItemUrl(String eItemUrl) {
        this.eItemUrl = eItemUrl;
    }
}
