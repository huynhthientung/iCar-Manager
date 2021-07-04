package com.example.icar.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.icar.R;
import com.example.icar.model.Bookings;
import com.example.icar.model.ExtraService;
import com.example.icar.model.Service;
import com.example.icar.model.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;
import java.util.Random;

public class BookingActivity extends AppCompatActivity {
    private Spinner spinnerSource, spinnerDestination;
    private Spinner spinnerCarModel;
    private EditText edtSourceAddress, edtDestinationAddress;
    private EditText edtReceiverName, edtReceiverPhone, edtReceiverNote;
    private EditText edtDateDepart, edtDateArrival;
    private CheckBox checkBoxESK01, checkBoxESK02, checkBoxESK03, checkBoxESK04;
    private TextView txtDistance, txtTotal;
    private Button btnCancel, btnConfirm;
    private ProgressBar progressBar;
    private int distance = 10, total = 0, price = 0;

    private boolean IS_SOURCE_ADDRESS_FILLED = false;
    private boolean IS_DESTINATION_ADDRESS_FILLED = false;
    private boolean IS_CAR_MODEL_PICKED = false;
    private ArrayList<Service> serviceArrayList = Utils.getInstance().getServiceArrayList();
    private ArrayList<ExtraService> extraServiceArrayList = Utils.getInstance().getExtraServiceArrayList();
    private String[] provinces;
    private int idSource, idDestination;

    private DatabaseReference root;

    @Override
    protected void onStart() {
        super.onStart();
        root = FirebaseDatabase.getInstance().getReference();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        Objects.requireNonNull(this.getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        initViews();
        ArrayAdapter<CharSequence> adapter;
        adapter = ArrayAdapter.createFromResource(this, R.array.provinces, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<CharSequence> carAdapter;
        carAdapter = ArrayAdapter.createFromResource(this, R.array.carModels, android.R.layout.simple_spinner_item);
        carAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSource.setAdapter(adapter);
        spinnerDestination.setAdapter(adapter);
        spinnerCarModel.setAdapter(carAdapter);

        spinnerSource.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(BookingActivity.this, provinces[position], Toast.LENGTH_SHORT).show();
                idSource = position;
                updateDistance();
                total = distance * price;
                updateTotalAndDistance();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerDestination.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idDestination = position;
                updateDistance();
                total = distance * price;
                updateTotalAndDistance();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerCarModel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                price = serviceArrayList.get(position).pricePerKm;
                total = distance * price;
                updateTotalAndDistance();
//                Toast.makeText(BookingActivity.this, "" + price, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        edtDateDepart.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    total = distance * price;
                    updateTotalAndDistance();
                }
            }
        });
        checkBoxESK01.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    total += extraServiceArrayList.get(0).price;
                } else {
                    total -= extraServiceArrayList.get(0).price;
                }
                updateTotalAndDistance();
            }
        });
        checkBoxESK02.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    total += extraServiceArrayList.get(1).price;
                } else {
                    total -= extraServiceArrayList.get(1).price;
                }
                updateTotalAndDistance();
            }
        });
        checkBoxESK03.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    total += extraServiceArrayList.get(2).price;
                } else {
                    total -= extraServiceArrayList.get(2).price;
                }
                updateTotalAndDistance();
            }
        });
        checkBoxESK04.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                boolean isChanged = false;
                if (isChecked) {
                    isChanged = true;
                    total += extraServiceArrayList.get(3).price;
                } else {
                    total -= extraServiceArrayList.get(3).price;
                }
                updateTotalAndDistance();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                booking();
            }
        });
    }

    private void booking() {
        progressBar.setVisibility(View.VISIBLE);
        Calendar calendar = Calendar.getInstance();
        String bookingKey = String.valueOf(calendar.getTimeInMillis());
        String uid = Utils.getInstance().getUid();
        String dateCreated = calendar.getTime().toString();
        String source = edtSourceAddress.getText().toString();
        String destination = edtDestinationAddress.getText().toString();
        String departure = edtDateDepart.getText().toString();
        String arrival = edtDateArrival.getText().toString();
        String receiverName = edtReceiverName.getText().toString();
        String receiverPhone = edtReceiverPhone.getText().toString();
        String receiverNote = edtReceiverNote.getText().toString();

        if (source.isEmpty() || destination.isEmpty() || departure.isEmpty() || arrival.isEmpty()
                || receiverName.isEmpty() || receiverPhone.isEmpty() || receiverNote.isEmpty()) {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
        } else {
            source += ", " + provinces[idSource];
            destination += ", " + provinces[idDestination];
            Bookings booking = new Bookings(bookingKey, uid, dateCreated, source, destination, departure, arrival,
                    receiverName, receiverPhone, receiverNote, checkBoxESK01.isChecked(), checkBoxESK02.isChecked(),
                    checkBoxESK03.isChecked(), checkBoxESK04.isChecked(), total);
            root.child("Bookings").child(bookingKey).setValue(booking)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(BookingActivity.this, "Đặt xe thành công!", Toast.LENGTH_SHORT).show();
                            } else {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(BookingActivity.this, "Đặt xe thất bại!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }


    }


    private void updateTotalAndDistance() {
        txtTotal.setText(String.valueOf(total));
        txtDistance.setText(String.valueOf(distance));
    }

    private void initViews() {
        provinces = getResources().getStringArray(R.array.provinces);
        spinnerSource = findViewById(R.id.spinner_source);
        spinnerDestination = findViewById(R.id.spinner_destination);
        spinnerCarModel = findViewById(R.id.spinner_carModel);

        edtSourceAddress = findViewById(R.id.editText_source);
        edtDestinationAddress = findViewById(R.id.editText_destination);
        edtReceiverName = findViewById(R.id.editText_receicerName);
        edtReceiverPhone = findViewById(R.id.editTextPhone_recieverPhone);
        edtReceiverNote = findViewById(R.id.editTextTextMultiLine_Note);
        edtDateDepart = findViewById(R.id.editTextDate_ngayDi);
        edtDateArrival = findViewById(R.id.editTextDate_ngayDen);

        checkBoxESK01 = findViewById(R.id.checkBox1);
        checkBoxESK02 = findViewById(R.id.checkBox2);
        checkBoxESK03 = findViewById(R.id.checkBox3);
        checkBoxESK04 = findViewById(R.id.checkBox4);

        txtTotal = findViewById(R.id.textView_total);
        txtDistance = findViewById(R.id.textView_distance);

        btnCancel = findViewById(R.id.button_cancel);
        btnConfirm = findViewById(R.id.button_confirm);

        progressBar = findViewById(R.id.progressBar_Loading);

    }

    private void updateDistance() {
        Random random = new Random();
        if (idDestination == idSource) {
            distance = random.nextInt((30 - 10) + 1) + 10; // random [10, 30]
        } else {
            distance = random.nextInt((1500 - 150) + 1) + 150; // random [150, 1500]
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}