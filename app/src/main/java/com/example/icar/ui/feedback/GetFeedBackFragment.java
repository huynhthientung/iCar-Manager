package com.example.icar.ui.feedback;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.icar.adapter.GetFeedbackAdapter;
import com.example.icar.databinding.FragmentGetFeedBackBinding;
import com.example.icar.model.Customer;
import com.example.icar.model.Feedback;
import com.example.icar.model.Utils;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class GetFeedBackFragment extends Fragment {

    private FragmentGetFeedBackBinding binding;
    private DatabaseReference reference;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentGetFeedBackBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        reference = FirebaseDatabase.getInstance().getReference();
        RecyclerView recyclerView = binding.recyclerFeedback;
        GetFeedbackAdapter adapter = new GetFeedbackAdapter(getContext());
        ArrayList<Feedback> feedbacks = new ArrayList<>();
        adapter.setFeedbacks(feedbacks);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        reference.child("Feedback").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                Feedback feedback = snapshot.getValue(Feedback.class);
                feedbacks.add(feedback);
                adapter.notifyDataSetChanged();
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
        return root;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}