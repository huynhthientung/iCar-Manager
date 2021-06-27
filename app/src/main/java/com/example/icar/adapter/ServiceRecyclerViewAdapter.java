package com.example.icar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.icar.R;
import com.example.icar.model.Service;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ServiceRecyclerViewAdapter extends RecyclerView.Adapter<ServiceRecyclerViewAdapter.ViewHolder>{

    private ArrayList<Service> services = new ArrayList<>();
    private Context context;

    public ServiceRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    public void setServices(ArrayList<Service> services) {
        this.services = services;
//        notifyDataSetChanged();
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_service, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ServiceRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.txtServiceKey.setText("" +services.get(position).getServiceKey());
        holder.txtCarName.setText("Loại xe: " + services.get(position).getCarName());
        holder.txtPricePerKm.setText("Giá: " + String.valueOf(services.get(position).getPricePerKm()) + "đ/km");
    }

    @Override
    public int getItemCount() {
        return services.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtServiceKey, txtCarName, txtPricePerKm;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            txtServiceKey = itemView.findViewById(R.id.textView_serviceKey);
            txtCarName = itemView.findViewById(R.id.textView_carName);
            txtPricePerKm = itemView.findViewById(R.id.textView_pricePerKm);
        }
    }

}
