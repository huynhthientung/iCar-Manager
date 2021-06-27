package com.example.icar.home_ui.transaction;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.icar.databinding.FragmentTransactionBinding;

public class TransactionFragment extends Fragment {

    private FragmentTransactionBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentTransactionBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        /*
            write code here
         */

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}