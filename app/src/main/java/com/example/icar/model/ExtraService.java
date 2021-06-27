package com.example.icar.model;

public class ExtraService {
    public String extraServiceKey;
    public String extraServiceName;
    public int price;

    public ExtraService() {
    }

    public ExtraService(String extraServiceKey, String extraServiceName, int price) {
        this.extraServiceKey = extraServiceKey;
        this.extraServiceName = extraServiceName;
        this.price = price;
    }

    public String getExtraServiceKey() {
        return extraServiceKey;
    }

    public void setExtraServiceKey(String extraServiceKey) {
        this.extraServiceKey = extraServiceKey;
    }

    public String getExtraServiceName() {
        return extraServiceName;
    }

    public void setExtraServiceName(String extraServiceName) {
        this.extraServiceName = extraServiceName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "ExtraService{" +
                "extraServiceKey='" + extraServiceKey + '\'' +
                ", extraServiceName='" + extraServiceName + '\'' +
                ", price=" + price +
                '}';
    }
}
