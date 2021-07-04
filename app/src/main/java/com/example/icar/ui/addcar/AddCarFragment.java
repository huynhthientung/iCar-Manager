package com.example.icar.ui.addcar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.icar.activity.HomeActivity;
import com.example.icar.activity.UpdateProfileActivity;
import com.example.icar.databinding.FragmentAddCarBinding;
import com.example.icar.model.Car;
import com.example.icar.model.CarModel;
import com.example.icar.model.Customer;
import com.example.icar.model.Utils;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;


public class AddCarFragment extends Fragment {

    private FragmentAddCarBinding binding;
    private final int REQUEST_CODE_IMAGE = 1;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private String name;

    private ImageView imgView;
    private Button btnAdd, btnPickPhoto;
    private EditText edtBienSo, edtMauSac, edtHangXe, edtTrongTai;
    private ProgressBar progressBar;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private String urlCar;
    private Calendar calendar = Calendar.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAddCarBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference("cars");
        initializeViews();
        btnPickPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_CODE_IMAGE);
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                saveImage();
            }
        });
        return root;
    }

    private void initializeViews() {
        imgView = binding.imageView;
        btnAdd = binding.buttonAdd;
        btnPickPhoto = binding.buttonPick;
        edtBienSo = binding.editTextTextBienSo;
        edtMauSac = binding.editTextMauSac;
        edtHangXe = binding.editTextHangXe;
        edtTrongTai = binding.editTextTrongTai;
        progressBar = binding.progressBar;
    }

    private void updateProfile() {
        String bienSo = edtBienSo.getText().toString();
        String mauSac = edtMauSac.getText().toString();
        String hangXe = edtHangXe.getText().toString();
        int trongTai;
        try {
            trongTai = Integer.parseInt(edtTrongTai.getText().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (bienSo.isEmpty() || mauSac.isEmpty() || hangXe.isEmpty()) {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(getContext(), "All fields are required!", Toast.LENGTH_SHORT).show();
        } else {
            CarModel carModel =  new CarModel(String.valueOf(calendar.getTimeInMillis()),  Integer.parseInt(edtTrongTai.getText().toString()), "5m", "5m", "5m");
            Car car = new Car(bienSo, carModel, false, mauSac, hangXe, urlCar);
            databaseReference = FirebaseDatabase.getInstance().getReference();
            databaseReference.child("Car").child(car.BienSo).setValue(car)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(getContext(), "Update profile successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getContext(), HomeActivity.class));
//                                ArrayList<Car> cars = Utils.getInstance().getCarArrayList();
//                                cars.add(car);
//                                Utils.getInstance().setCarArrayList(cars);
                            } else {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(getContext(), "Update profile unsuccessfully", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    }

    /**
     * save image to store database
     */
    private void saveImage() {

        final StorageReference carReference = storageReference.child(calendar.getTimeInMillis()+ ".png");

        // Get the data from an ImageView as bytes
        imgView.setDrawingCacheEnabled(true);
        imgView.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imgView.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = carReference.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Try again", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
                progressBar.setVisibility(View.GONE);
                //Toast.makeText(UpdateProfileActivity.this, "Upload image successfully", Toast.LENGTH_SHORT).show();
            }
        });
        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return carReference.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
//                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//                    UserProfileChangeRequest profileChangeRequest;
//                    profileChangeRequest = new UserProfileChangeRequest.Builder()
//                            .setDisplayName(edtName.getText().toString())
//                            .setPhotoUri(downloadUri).build();
//                    user.updateProfile(profileChangeRequest);
                    urlCar = downloadUri.toString();
                    updateProfile();
                    Log.d("URL", downloadUri + "");
                } else {
                    // Handle failures
                    // ...
                    Log.d("AAA", ": fail at continueWithTask");
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}