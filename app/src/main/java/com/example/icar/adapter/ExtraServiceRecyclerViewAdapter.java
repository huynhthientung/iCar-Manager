package com.example.icar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.icar.R;
import com.example.icar.model.ExtraService;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ExtraServiceRecyclerViewAdapter extends RecyclerView.Adapter<ExtraServiceRecyclerViewAdapter.ViewHolder> {

    private ArrayList<ExtraService> extraServices = new ArrayList<>();
    private Context context;

    public ExtraServiceRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    public void setExtraServices(ArrayList<ExtraService> extraServices) {
        this.extraServices = extraServices;
//        notifyDataSetChanged();
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View viewHolder = LayoutInflater.from(context).inflate(R.layout.list_item_extra, parent, false);
        return new ViewHolder(viewHolder);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ExtraServiceRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.txtExtraServiceKey.setText(extraServices.get(position).extraServiceKey);
        holder.txtExtraName.setText("Tên loại dịch vụ: " + extraServices.get(position).extraServiceName);
        holder.txtPrice.setText("Giá: " + String.valueOf(extraServices.get(position).price) + "đ");

    }

    @Override
    public int getItemCount() {
        return extraServices.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtExtraServiceKey, txtExtraName, txtPrice;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            txtExtraServiceKey = itemView.findViewById(R.id.textView_ExtraServiceKey);
            txtExtraName = itemView.findViewById(R.id.textView_extraName);
            txtPrice = itemView.findViewById(R.id.textView_price);
        }
    }
}
