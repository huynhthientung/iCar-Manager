package com.example.icar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.icar.R;
import com.example.icar.model.Customer;
import com.example.icar.model.Feedback;
import com.example.icar.model.Utils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class GetFeedbackAdapter extends RecyclerView.Adapter<GetFeedbackAdapter.ViewHolder>{

    private Context context;
    private ArrayList<Feedback> feedbacks;

    public GetFeedbackAdapter(Context context) {
        this.context = context;
    }

    public void setFeedbacks(ArrayList<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View viewHolder = LayoutInflater.from(context).inflate(R.layout.list_item_feedback, parent, false);
        return new ViewHolder(viewHolder);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull GetFeedbackAdapter.ViewHolder holder, int position) {

        holder.txtName.setText(uid_to_name(feedbacks.get(position).uid));
        holder.txtDate.setText(feedbacks.get(position).date);
        holder.txtContent.setText(feedbacks.get(position).text);

    }

    public String uid_to_name(String uid) {
        ArrayList<Customer> customers = Utils.getInstance().getCustomerArrayList();
        for (Customer c : customers) {
            if (c.uid.equals(uid)) {
                return c.full_name + " (" + uid + ")";
            }
        }
        return "";
    }
    @Override
    public int getItemCount() {
        return feedbacks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtName, txtDate, txtContent;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.textView_feedback_name);
            txtDate = itemView.findViewById(R.id.textView_feedback_date);
            txtContent = itemView.findViewById(R.id.textView_feedback_content);
        }
    }
}
