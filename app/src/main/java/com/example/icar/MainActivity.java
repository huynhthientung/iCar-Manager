package com.example.icar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.icar.activity.ForgetPasswordActivity;
import com.example.icar.activity.HomeActivity;
import com.example.icar.activity.SignUpActivity;
import com.example.icar.model.Car;
import com.example.icar.model.CarModel;
import com.example.icar.model.ExtraService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final int TIME_INTERVAL = 2000;
    private long backPressed;
    private FirebaseAuth mAuth;
    private EditText edtEmail, edtPassword;
    private CheckBox checkBox;
    private ProgressBar progressBar;
    private FloatingActionButton btnEnter;
    private ArrayList<CarModel> models = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        edtEmail = findViewById(R.id.editTextEmail);
        edtPassword = findViewById(R.id.editTextPassword);
        btnEnter = findViewById(R.id.floatingButtonEnter);
        checkBox = findViewById(R.id.checkboxShowPassword);
        progressBar = findViewById(R.id.progressBar);

        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSignIn();
            }
        });

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    edtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                } else {
                    edtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
                }
            }
        });
    }

//    private void test2() {
//        DatabaseReference root = FirebaseDatabase.getInstance().getReference();
//        ArrayList<Car> cars = new ArrayList<>();
//        Car car1 = new Car("51A-11111", models.get(2), false, "Đen", "Toyota", "https://leanhtien.net/wp-content/uploads/2020/11/xe-tai-do-thanh-iz49.jpg");
//        Car car2 = new Car("51A-22222", models.get(6), false, "Trắng", "Hyundai", "https://data.thegioixetai.vn/product/hinh-dai-dien-3/xe-tai-hino-fg8jt7a-utl-thung-bat-dai-10m.jpg");
//        Car car3 = new Car("51A-33333", models.get(4), false, "Xanh Dương", "Isuzu", "https://product.hstatic.net/1000259555/product/xe_ngang_da_nen_345x194_2bb3d81a49934b2eaeacccd98e438197.png");
//        Car car4 = new Car("51A-44444", models.get(9), false, "Đen", "Thaco", "https://xetai-hyundai.com/public/userfiles/products/xe-tai-hyundai-iz49-2t4.jpg");
//        Car car5 = new Car("51A-55555", models.get(1), false, "Trắng", "Kia", "https://top10tphcm.com/wp-content/uploads/2021/01/gia-xe-tai-10-tan.jpg");
//        Car car6 = new Car("51A-66666", models.get(0), false, "Đen", "Hino", "https://xetaisaigon.com/wp-content/uploads/2019/12/xe-tai-cho-xe-may-HINO-3t5.jpg");
//        Car car7 = new Car("51A-77777", models.get(4), false, "Xanh Dương", "Toyota", "https://leanhtien.net/wp-content/uploads/2020/11/xe-tai-do-thanh-iz49.jpg");
//        Car car8 = new Car("51A-88888", models.get(2), false, "Đen", "Toyota", "https://leanhtien.net/wp-content/uploads/2020/11/xe-tai-do-thanh-iz49.jpg");
//        Car car9 = new Car("51A-99999", models.get(1), false, "Trắng", "Hyundai", "https://xetai-hyundai.com/public/userfiles/products/xe-tai-hyundai-iz49-2t4.jpg");
//        Car car10 = new Car("51A-01234", models.get(3), false, "Xanh Dương", "Hino", "https://data.thegioixetai.vn/product/hinh-dai-dien-3/xe-tai-hino-fg8jt7a-utl-thung-bat-dai-10m.jpg");
//        Car car11 = new Car("51A-12345", models.get(7), false, "Trắng", "Thaco", "https://top10tphcm.com/wp-content/uploads/2021/01/gia-xe-tai-10-tan.jpg");
//        Car car12 = new Car("51A-23456", models.get(8), false, "Trắng", "Isuzu", "https://top10tphcm.com/wp-content/uploads/2021/01/gia-xe-tai-10-tan.jpg");
//        Car car13 = new Car("51A-34567", models.get(8), false, "Xanh Dương", "Hino", "https://leanhtien.net/wp-content/uploads/2020/11/xe-tai-do-thanh-iz49.jpg");
//        Car car14 = new Car("51A-45678", models.get(6), false, "Đen", "Kia", "https://xetaisaigon.com/wp-content/uploads/2019/12/xe-tai-cho-xe-may-HINO-3t5.jpg");
//        Car car15 = new Car("51A-56789", models.get(1), false, "Xanh Dương", "Toyota", "https://top10tphcm.com/wp-content/uploads/2021/01/gia-xe-tai-10-tan.jpg");
//        Car car16 = new Car("51A-18122", models.get(6), false, "Trắng", "Hyundai", "https://leanhtien.net/wp-content/uploads/2020/11/xe-tai-do-thanh-iz49.jpg");
//        Car car17 = new Car("51A-13133", models.get(4), false, "Xanh Dương", "Toyota", "https://data.thegioixetai.vn/product/hinh-dai-dien-3/xe-tai-hino-fg8jt7a-utl-thung-bat-dai-10m.jpg");
//        Car car18 = new Car("51A-11144", models.get(3), false, "Đen", "Isuzu", "https://leanhtien.net/wp-content/uploads/2020/11/xe-tai-do-thanh-iz49.jpg");
//        Car car19 = new Car("51A-22133", models.get(5), false, "Xanh Dương", "Hino", "https://xetaisaigon.com/wp-content/uploads/2019/12/xe-tai-cho-xe-may-HINO-3t5.jpg");
//
//        cars.add(car1);
//        cars.add(car2);
//        cars.add(car3);
//        cars.add(car4);
//        cars.add(car5);
//        cars.add(car6);
//        cars.add(car7);
//        cars.add(car8);
//        cars.add(car9);
//        cars.add(car10);
//        cars.add(car11);
//        cars.add(car12);
//        cars.add(car13);
//        cars.add(car14);
//        cars.add(car15);
//        cars.add(car16);
//        cars.add(car17);
//        cars.add(car18);
//        cars.add(car19);
//
//
//        for (Car car : cars) {
//            root.child("Car").child(car.BienSo).setValue(car).addOnCompleteListener(new OnCompleteListener<Void>() {
//                @Override
//                public void onComplete(@NonNull @NotNull Task<Void> task) {
//                    if (task.isSuccessful()) {
//                        Toast.makeText(MainActivity.this, "Thanh cong", Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(MainActivity.this, "That bai", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
//        }
//    }

//    private void test() {
//        DatabaseReference root = FirebaseDatabase.getInstance().getReference();
//
//        root.child("CarModels").addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
//                CarModel carModel = snapshot.getValue(CarModel.class);
//                models.add(carModel);
//            }
//
//            @Override
//            public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull @NotNull DatabaseError error) {
//
//            }
//        });
//
////        for (CarModel model : models) {
////            root.child("CarModels").child(model.MaLoaiXe).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
////                @Override
////                public void onComplete(@NonNull @NotNull Task<Void> task) {
////                    if (task.isSuccessful()) {
////                        Toast.makeText(MainActivity.this, "Thanh cong", Toast.LENGTH_SHORT).show();
////                    } else {
////                        Toast.makeText(MainActivity.this, "That bai", Toast.LENGTH_SHORT).show();
////                    }
////                }
////            });
////        }
//
//    }

    private void onSignIn() {
        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, "Successfully sign-in", Toast.LENGTH_SHORT).show();
                    // TODO: move on to dashboard
                    startActivity(new Intent(MainActivity.this, HomeActivity.class));
                    finish();
                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, task.getException().toString(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void OpenSignupPage(View view) {
        startActivity(new Intent(MainActivity.this, SignUpActivity.class));
    }

    public void OpenForgotPasswordPage(View view) {
        startActivity(new Intent(this, ForgetPasswordActivity.class));
    }

    @Override
    protected void onStop() {
        super.onStop();
        //mAuth.signOut();
    }


    @Override
    public void onBackPressed() {
        if (backPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            super.onBackPressed();
            return;
        } else {
            Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        backPressed = System.currentTimeMillis();
    }
}