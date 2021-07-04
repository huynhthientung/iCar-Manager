package com.example.icar.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.icar.R;
import com.example.icar.model.Bookings;
import com.example.icar.model.Car;
import com.example.icar.model.Customer;
import com.example.icar.model.Driver;
import com.example.icar.model.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ProcessTransactionActivity extends AppCompatActivity {

    private final String TAG = "BOOKINGS";
    private String bookingKey;
    private DatabaseReference root;
    private static Bookings bookings;
    private ListView lvDrivers, lvCars;
    private TextView txtDetails, txtPickedCar, txtPickedDriver;
    private Button btnConfirm;
    private ArrayList<Car> carArrayList;
    private ArrayList<Driver> driverArrayList;
    private ArrayAdapter adapterCar, adapterDriver;
    private ArrayList<String> cars, drivers;
    private boolean FLAG_CAR_CHANGED = false;
    private boolean FLAG_DRIVER_CHANGED = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_transaction);
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_close);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        root = FirebaseDatabase.getInstance().getReference();
//        String jsonBookings;
//        Bundle extras = getIntent().getExtras();
//        if (extras != null) {
//            jsonBookings = extras.getString(TAG);
//            bookings = new Gson().fromJson(jsonBookings, Bookings.class);
//        }
        bookingKey = getIntent().getExtras().getString(TAG);
//        Toast.makeText(this, "" + bookings.toString(), Toast.LENGTH_SHORT).show();
        Utils.getInstance().setCarArrayList(null);
        Utils.getInstance().setDriverArrayList(null);
        carArrayList = Utils.getInstance().getCarArrayList();
        driverArrayList = Utils.getInstance().getDriverArrayList();
        txtDetails = findViewById(R.id.textView_car_details);
        txtPickedCar = findViewById(R.id.textView_PickedCar);
        txtPickedDriver = findViewById(R.id.textView_PickedDriver);
        btnConfirm = findViewById(R.id.button_confirm);
        lvCars = findViewById(R.id.listView_car);
        lvDrivers = findViewById(R.id.listView_driver);

        // get available cars and drivers only
        // TODO: constraint the weight of car to show on listview
        cars = new ArrayList<>();
        drivers = new ArrayList<>();

        adapterCar = new ArrayAdapter(this, android.R.layout.simple_list_item_1, cars);
        adapterDriver = new ArrayAdapter(this, android.R.layout.simple_list_item_1, drivers);
        lvDrivers.setAdapter(adapterDriver);
        lvCars.setAdapter(adapterCar);
        lvCars.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                txtPickedCar.setText(cars.get(position));
                FLAG_CAR_CHANGED = true;
            }
        });
        lvDrivers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                txtPickedDriver.setText(drivers.get(position));
                FLAG_DRIVER_CHANGED = true;
            }
        });
        root.child("Bookings").child(bookingKey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                bookings = snapshot.getValue(Bookings.class);
                txtDetails.setText(bookings.toString());
                for (Car car : carArrayList) {
                    if (!car.TrangThai && car.Loaixe.TrongTai == bookings.TrongTaiXe) {
                        cars.add(car.BienSo);
                    }
                }
                adapterCar.notifyDataSetChanged();
                for (Driver driver : driverArrayList) {
                    if (!driver.TrangThai) {
                        drivers.add(driver.full_name + "-" + driver.uid);
                    }
                }
                adapterDriver.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (FLAG_CAR_CHANGED && FLAG_DRIVER_CHANGED) {
                    // TODO: set preference of driver and car
                    bookings.setStatus(true);
                    bookings.setCarId(txtPickedCar.getText().toString());
                    String[] spliter = txtPickedDriver.getText().toString().split("-");
                    bookings.setDriverId(spliter[1]);
                    root.child("Bookings").child(bookings.bookingKey).setValue(bookings)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        root.child("Drivers").child(spliter[1]).child("TrangThai").setValue(true);
                                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                        finish();
                                    } else {
                                        Toast.makeText(ProcessTransactionActivity.this, "Lỗi, vui lòng thử lại ", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                    
                } else {
                    Toast.makeText(ProcessTransactionActivity.this, "Vui lòng chọn xe và tài xế !", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}