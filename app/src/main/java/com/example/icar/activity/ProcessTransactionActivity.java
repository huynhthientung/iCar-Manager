package com.example.icar.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
    private DatabaseReference root;
    private static Bookings bookings;
    private ListView lvDrivers, lvCars;
    private TextView txtDetails, txtPickedCar, txtPickedDriver;
    private Button btnConfirm;
    private ArrayList<Car> carArrayList;
    private ArrayList<Driver> driverArrayList;
    private ArrayAdapter adapterCar, adapterDriver;
    private ArrayList<String> cars, drivers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_transaction);
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_close);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        root = FirebaseDatabase.getInstance().getReference();
        String jsonBookings;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            jsonBookings = extras.getString(TAG);
            bookings = new Gson().fromJson(jsonBookings, Bookings.class);
        }
//        Toast.makeText(this, "" + bookings.toString(), Toast.LENGTH_SHORT).show();
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
        for (Car car : carArrayList) {
            if (!car.TrangThai && car.Loaixe.TrongTai == bookings.TrongTaiXe) {
                cars.add(car.BienSo);
            }
        }
        for (Driver driver : driverArrayList) {
            if (!driver.TrangThai) {
                drivers.add(driver.full_name + "-" + driver.uid);
            }
        }
        adapterCar = new ArrayAdapter(this, android.R.layout.simple_list_item_1, cars);
        adapterDriver = new ArrayAdapter(this, android.R.layout.simple_list_item_1, drivers);
        lvDrivers.setAdapter(adapterDriver);
        lvCars.setAdapter(adapterCar);
        lvCars.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                txtPickedCar.setText(cars.get(position));
            }
        });
        lvDrivers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                txtPickedDriver.setText(drivers.get(position));
            }
        });
        txtDetails.setText(bookings.toString());
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ProcessTransactionActivity.this, "TODO: LATER", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}