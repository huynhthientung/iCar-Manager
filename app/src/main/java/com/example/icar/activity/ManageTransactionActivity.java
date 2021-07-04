package com.example.icar.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.icar.R;
import com.example.icar.model.Bookings;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ManageTransactionActivity extends AppCompatActivity {

    private final String TAG = "BOOKINGS";
    private ListView lvBookings;
    private ArrayList<String> bookingsArrayList;
    private DatabaseReference root;
    private ArrayAdapter adapter;
    private Bookings bookings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_transaction);
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_close);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        root = FirebaseDatabase.getInstance().getReference();
        bookingsArrayList = new ArrayList<>();
        lvBookings = findViewById(R.id.listView_bookings);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, bookingsArrayList);
        lvBookings.setAdapter(adapter);
        root.child("Bookings").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                bookings = snapshot.getValue(Bookings.class);
                if (bookings.driverId.equals("") || bookings.carId.equals("") || !bookings.status) {
                    bookingsArrayList.add(bookings.bookingKey);
                    adapter.notifyDataSetChanged();
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
        lvBookings.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(ManageTransactionActivity.this, "" + bookingsArrayList.get(position), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ManageTransactionActivity.this, ProcessTransactionActivity.class);
//                intent.putExtra(TAG, new Gson().toJson(bookings));
                intent.putExtra(TAG, bookingsArrayList.get(position));
                startActivity(intent);
                finish();
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