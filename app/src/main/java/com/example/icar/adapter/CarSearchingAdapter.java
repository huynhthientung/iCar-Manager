package com.example.icar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.icar.R;
import com.example.icar.model.Car;
import com.example.icar.model.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CarSearchingAdapter extends RecyclerView.Adapter<CarSearchingAdapter.ViewHolder>{

    private Context context;
    private ArrayList<Car> carArrayList;
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference();
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
        holder.layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
//                Toast.makeText(context, "Long clicked " + position, Toast.LENGTH_SHORT).show();
                holder.txtDelete.setVisibility(View.VISIBLE);
                return false;
            }
        });
        holder.txtDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                root.child("Car").child(carArrayList.get(position).BienSo).removeValue()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    ArrayList<Car> cars = Utils.getInstance().getCarArrayList();
                                    carArrayList.remove(position);
                                    Utils.getInstance().setCarArrayList(carArrayList);
                                    notifyDataSetChanged();
                                }
                            }
                        });
            }
        });
    }

    @Override
    public int getItemCount() {
        return carArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgCar, imgStatus;
        private TextView txtCarDetails, txtDelete;
        private LinearLayout layout;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imgCar = itemView.findViewById(R.id.imageView_car);
            txtCarDetails = itemView.findViewById(R.id.textView_car_details);
            imgStatus = itemView.findViewById(R.id.imageView_status);
            layout = itemView.findViewById(R.id.mLayout);
            txtDelete = itemView.findViewById(R.id.textView_Delete);
        }
    }
}
