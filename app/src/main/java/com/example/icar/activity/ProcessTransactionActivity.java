package com.example.icar.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.icar.R;
import com.example.icar.model.Bookings;
import com.example.icar.model.Customer;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

public class ProcessTransactionActivity extends AppCompatActivity {

    private final String TAG = "BOOKING";
    private String bookingKey;
    private DatabaseReference root;
    private static Bookings bookings;


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
            jsonBookings = extras.getString("BOOKINGS");
            bookings = new Gson().fromJson(jsonBookings, Bookings.class);
        }
        Toast.makeText(this, "" + bookings.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}