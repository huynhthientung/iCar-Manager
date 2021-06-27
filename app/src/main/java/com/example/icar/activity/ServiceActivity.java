package com.example.icar.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.icar.R;
import com.example.icar.adapter.ExtraServiceRecyclerViewAdapter;
import com.example.icar.adapter.ServiceRecyclerViewAdapter;
import com.example.icar.model.ExtraService;
import com.example.icar.model.Service;
import com.example.icar.model.Utils;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ServiceActivity extends AppCompatActivity {

    private Spinner spinner;
    private RecyclerView recViewService, recViewExtra;
    public  ArrayList<Service> services = new ArrayList<>();
    private ArrayList<ExtraService> extraServices = new ArrayList<>();
    private DatabaseReference root;
    private ServiceRecyclerViewAdapter serviceRecyclerViewAdapter;
    private ExtraServiceRecyclerViewAdapter extraServiceRecyclerViewAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_close);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        spinner = findViewById(R.id.spinnerOptions);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.options_service_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        recViewService = findViewById(R.id.recyclerService);
        recViewExtra = findViewById(R.id.recyclerExtra);

        services = Utils.getInstance().getServiceArrayList();
        extraServices = Utils.getInstance().getExtraServiceArrayList();

        serviceRecyclerViewAdapter = new ServiceRecyclerViewAdapter(this);
        serviceRecyclerViewAdapter.setServices(services);
        recViewService.setLayoutManager(new LinearLayoutManager(this));
        recViewService.setAdapter(serviceRecyclerViewAdapter);

        extraServiceRecyclerViewAdapter = new ExtraServiceRecyclerViewAdapter(this);
        extraServiceRecyclerViewAdapter.setExtraServices(extraServices);
        recViewExtra.setLayoutManager(new LinearLayoutManager(this));
        recViewExtra.setAdapter(extraServiceRecyclerViewAdapter);


//        getDataToArrayList();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(ServiceActivity.this, "Position: " + position + "Id: " + id, Toast.LENGTH_SHORT).show();
                if (id == 1) {
                    recViewService.setVisibility(View.GONE);
                    recViewExtra.setVisibility(View.VISIBLE);
                } else if (id == 0) {
                    recViewExtra.setVisibility(View.GONE);
                    recViewService.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    private void getDataToArrayList() {
        root = FirebaseDatabase.getInstance().getReference();
        root.child("Services").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                Service service = snapshot.getValue(Service.class);
                services.add(service);
                serviceRecyclerViewAdapter.notifyDataSetChanged();
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
        root.child("ExtraServices").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                ExtraService extraService = snapshot.getValue(ExtraService.class);
                extraServices.add(extraService);
                extraServiceRecyclerViewAdapter.notifyDataSetChanged();
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}