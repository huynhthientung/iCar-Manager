package com.example.icar.model;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Utils {

    private static Utils instance;
    private static Customer customer;
    private static ArrayList<Service> serviceArrayList = null;
    private static ArrayList<ExtraService> extraServiceArrayList = null;
//    private static ArrayList<Car> carArrayList;
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private final FirebaseUser mUser = mAuth.getCurrentUser();
    private final DatabaseReference root = FirebaseDatabase.getInstance().getReference();
    private static String uid = null;
    private static Uri photoUrl = null;
    private static String driverName = "";

    private Utils() {
        if (null == serviceArrayList) {
            serviceArrayList = new ArrayList<>();
            initServiceArrayList();
        }
        if (null == extraServiceArrayList) {
            extraServiceArrayList = new ArrayList<>();
            initExtraServiceArrayList();
        }
        if (null == uid) {
            uid = mUser.getUid();
        }
        if (null == photoUrl) {
            photoUrl = mUser.getPhotoUrl();
        }
        if (null == customer) {
            initCustomer();
        }
        //TODO: carArrayList
    }

    private void initCustomer() {
        root.child("Customers").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                customer = snapshot.getValue(Customer.class);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private void initExtraServiceArrayList() {
        root.child("ExtraServices").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                ExtraService extraService = snapshot.getValue(ExtraService.class);
                extraServiceArrayList.add(extraService);
            }

            @Override
            public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private void initServiceArrayList() {
        root.child("Services").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                Service service = snapshot.getValue(Service.class);
                serviceArrayList.add(service);
            }

            @Override
            public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }



    public ArrayList<Service> getServiceArrayList() {
        return serviceArrayList;
    }

    public ArrayList<ExtraService> getExtraServiceArrayList() {
        return extraServiceArrayList;
    }

    public String getUid() {
        return uid;
    }

    public Uri getPhotoUrl() {
        return photoUrl;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getDriverName(String uid) {
        root.child("Drivers").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                Driver driver = snapshot.getValue(Driver.class);
                if (driver.uid.equals(uid)) {
                    driverName = driver.full_name;
                }
            }

            @Override
            public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        return driverName;
    }
    public static Utils getInstance() {
        if (null == instance) {
            instance = new Utils();
        }
        return instance;
    }
}
