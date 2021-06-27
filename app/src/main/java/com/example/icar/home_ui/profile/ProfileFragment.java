package com.example.icar.home_ui.profile;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.icar.model.Customer;
import com.example.icar.activity.UpdateProfileActivity;
import com.example.icar.databinding.FragmentProfileBinding;
import com.example.icar.model.Manager;
import com.example.icar.model.Utils;
import com.google.firebase.FirebaseApp;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private String name;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FirebaseApp.initializeApp(getContext());
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        final ImageView igmAvatar;
        final TextView txtName, txtEmail, txtPhone, txtAddress, txtBirthday, txtGender;
        final Button btnEdit;
        final ProgressBar progressBar;

        igmAvatar = binding.imageViewAvatarProfileFragment;
        txtName = binding.textViewNameProFrm;
        txtEmail = binding.textViewEmailProFrm;
        txtPhone = binding.textViewPhoneProFrm;
        txtAddress = binding.textViewAddressProFrm;
        txtBirthday = binding.textViewBirthdayProFrm;
        txtGender = binding.textViewGenderProFrm;
        btnEdit = binding.buttonEditProFrm;
        progressBar = binding.progressBarProFrm;

        progressBar.setVisibility(View.VISIBLE);
        String uid = Utils.getInstance().getUid();
        Manager manager = Utils.getInstance().getManager(); // Select * from Customers where UID = KEY
        name = manager.full_name;
        try {
            txtName.setText(txtName.getText().toString() + manager.full_name);
            txtEmail.setText(txtEmail.getText().toString() + manager.email);
            txtPhone.setText(txtPhone.getText().toString() + manager.phone);
            txtAddress.setText(txtAddress.getText().toString() + manager.address);
            txtBirthday.setText(txtBirthday.getText().toString() + manager.birthday);
            txtGender.setText(txtGender.getText().toString() + (manager.gender ? "Nam" : "Nu"));
            Glide.with(getContext()).asBitmap().load(Utils.getInstance().getPhotoUrl()).into(igmAvatar);
        } catch (Exception e) {
            Log.d("AAA", e.getMessage());
        }
        progressBar.setVisibility(View.GONE);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), UpdateProfileActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", name);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}