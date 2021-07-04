package com.example.icar.model;

public class Car {
    public String BienSo;
    public CarModel Loaixe;
    public boolean TrangThai;
    public String MauSac;
    public String HangXe;
    public String CarPhotoUrl;

    public Car() {
    }

    public Car(String bienSo, CarModel loaixe, boolean trangThai, String mauSac, String hangXe, String carPhotoUrl) {
        BienSo = bienSo;
        Loaixe = loaixe;
        TrangThai = trangThai;
        MauSac = mauSac;
        HangXe = hangXe;
        CarPhotoUrl = carPhotoUrl;
    }

    @Override
    public String toString() {
        return "Car{" +
                "BienSo='" + BienSo + '\'' +
                ", Loaixe=" + Loaixe +
                ", TrangThai=" + TrangThai +
                ", MauSac='" + MauSac + '\'' +
                ", HangXe='" + HangXe + '\'' +
                ", CarPhotoUrl='" + CarPhotoUrl + '\'' +
                '}';
    }
}
