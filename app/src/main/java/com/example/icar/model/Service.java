package com.example.icar.model;

public class Service {
    public String serviceKey;
    public String carName;
    public int pricePerKm;

    public Service(String serviceKey, String carName, int pricePerKm) {
        this.serviceKey = serviceKey;
        this.carName = carName;
        this.pricePerKm = pricePerKm;
    }

    public Service() {
    }

    @Override
    public String toString() {
        return "Service{" +
                "serviceKey='" + serviceKey + '\'' +
                ", carName='" + carName + '\'' +
                ", pricePerKm=" + pricePerKm +
                '}';
    }

    public String getServiceKey() {
        return serviceKey;
    }

    public void setServiceKey(String serviceKey) {
        this.serviceKey = serviceKey;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public int getPricePerKm() {
        return pricePerKm;
    }

    public void setPricePerKm(int pricePerKm) {
        this.pricePerKm = pricePerKm;
    }
}
