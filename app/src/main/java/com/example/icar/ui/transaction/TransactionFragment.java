package com.example.icar.ui.transaction;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.icar.databinding.FragmentTransactionBinding;
import com.example.icar.model.Bookings;
import com.example.icar.model.Utils;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class TransactionFragment extends Fragment {

    private FragmentTransactionBinding binding;
    private ListView lvTransaction;
    private ArrayAdapter adapter = null;
    private ArrayList<String> transactions;
    private DatabaseReference mRoot;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentTransactionBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mRoot = FirebaseDatabase.getInstance().getReference();
        lvTransaction = binding.listViewTransaction;
        transactions = new ArrayList<String>();
        adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, transactions);
        lvTransaction.setAdapter(adapter);
        mRoot.child("Bookings").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                Bookings booking = snapshot.getValue(Bookings.class);
                if (booking.uid.equals(Utils.getInstance().getUid())) {
                    transactions.add(booking.toString());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {
                Bookings bookings = snapshot.getValue(Bookings.class);
                String removed = bookings.toString();
                for (String s : transactions) {
                    if (s.equals(removed)) {
                        transactions.remove(s);
                        break;
                    }
                }
                adapter.notifyDataSetChanged();
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