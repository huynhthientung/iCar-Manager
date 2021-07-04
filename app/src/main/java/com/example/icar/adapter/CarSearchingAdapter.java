package com.example.icar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.icar.R;
import com.example.icar.model.Car;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CarSearchingAdapter extends RecyclerView.Adapter<CarSearchingAdapter.ViewHolder>{

    private Context context;
    private ArrayList<Car> carArrayList;

    public CarSearchingAdapter(Context context) {
        this.context = context;
    }

    public void setCarArrayList(ArrayList<Car> carArrayList) {
        this.carArrayList = carArrayList;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View viewHolder = LayoutInflater.from(context).inflate(R.layout.list_item_car, parent, false);
        return new ViewHolder(viewHolder);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CarSearchingAdapter.ViewHolder holder, int position) {
        Glide.with(context).asBitmap().load(carArrayList.get(position).CarPhotoUrl).into(holder.imgCar);
        holder.txtCarDetails.setText(carArrayList.get(position).toString());
        if (carArrayList.get(position).TrangThai) {
            holder.imgStatus.setImageResource(R.drawable.reddot);
        } else {
            holder.imgStatus.setImageResource(R.drawable.greendot);
        }
    }

    @Override
    public int getItemCount() {
        return carArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgCar, imgStatus;
        private TextView txtCarDetails;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imgCar = itemView.findViewById(R.id.imageView_car);
            txtCarDetails = itemView.findViewById(R.id.textView_car_details);
            imgStatus = itemView.findViewById(R.id.imageView_status);
        }
    }
}
