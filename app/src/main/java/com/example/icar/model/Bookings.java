package com.example.icar.model;

public class Bookings {
    public String bookingKey;
    public String uid;
    public String driverId;
    public String carId; // bien so xe
    public String dateCreated;
    public String source;
    public String destination;
    public String dateDepart;
    public String dateArrival;
    public String receiverName;
    public String receiverPhone;
    public String receiverNote;
    public boolean isESK01;
    public boolean isESK02;
    public boolean isESK03;
    public boolean isESK04;
    public int total;
    public boolean status;

    public Bookings() {
    }

    public Bookings(String bookingKey, String uid, String dateCreated, String source, String destination,
                    String dateDepart, String dateArrival, String receiverName, String receiverPhone,
                    String receiverNote, boolean isESK01, boolean isESK02, boolean isESK03, boolean isESK04,
                    int total) {
        this.bookingKey = bookingKey;
        this.uid = uid;
        this.dateCreated = dateCreated;
        this.source = source;
        this.destination = destination;
        this.dateDepart = dateDepart;
        this.dateArrival = dateArrival;
        this.receiverName = receiverName;
        this.receiverPhone = receiverPhone;
        this.receiverNote = receiverNote;
        this.isESK01 = isESK01;
        this.isESK02 = isESK02;
        this.isESK03 = isESK03;
        this.isESK04 = isESK04;
        this.total = total;

        this.status = false;
        this.carId = "";
        this.driverId = "";
    }

    @Override
    public String toString() {
        return "Tài xế: " + Utils.getInstance().getDriverName(driverId) + "\n" +
                "Biển số xe: " + carId + '\n' +
                "Ngày đặt xe: " + dateCreated + '\n' +
                "Nơi đi: " + source + '\n' +
                "Nơi đến: " + destination + '\n' +
                "Ngày đi: " + dateDepart + '\n' +
                "Ngày đến: " + dateArrival + '\n' +
                "Người nhận: " + receiverName + '\n' +
                "Số điện thoại người nhận: " + receiverPhone + '\n' +
                "Ghi chú: " + receiverNote + '\n' +
                "Tổng cộng: " + total + " vnd " + "\n";
    }
}
