package com.example.icar.model;

public class CarModel {
    public String MaLoaiXe;
    public int TrongTai;
    public String ChieuDai;
    public String ChieuRong;
    public String ChieuCao;

    public CarModel() {
    }

    public CarModel(String maLoaiXe, int trongTai, String chieuDai, String chieuRong, String chieuCao) {
        MaLoaiXe = maLoaiXe;
        TrongTai = trongTai;
        ChieuDai = chieuDai;
        ChieuRong = chieuRong;
        ChieuCao = chieuCao;
    }

    @Override
    public String toString() {
        return "CarModel{" +
                "MaLoaiXe='" + MaLoaiXe + '\'' +
                ", TrongTai=" + TrongTai +
                ", ChieuDai='" + ChieuDai + '\'' +
                ", ChieuRong='" + ChieuRong + '\'' +
                ", ChieuCao='" + ChieuCao + '\'' +
                '}';
    }
}
