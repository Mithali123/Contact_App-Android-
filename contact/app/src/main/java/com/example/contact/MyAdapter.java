package com.example.contact;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {


    Context context;
    List<item> items;

    public MyAdapter(Context context, List<item> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(items.get(position).getName());
        holder.phone.setText(items.get(position).getPhone());
        holder.day.setText(items.get(position).getDay());
        holder.time.setText(items.get(position).getTime());
        //holder.call_type.setText(items.get(position).getCall_type());
        holder.date.setText(items.get(position).getDate());
        holder.duration.setText(items.get(position).getDuration());
        holder.initial.setText(items.get(position).getInitial());

        holder.call_button.setOnClickListener(v -> {
            // Create an Intent to start the new activity
            Intent intent = new Intent(context, CallWindowActivity.class);

            // Pass the necessary data to the new activity
            intent.putExtra("name", items.get(position).getName());
            intent.putExtra("phone", items.get(position).getPhone());
            intent.putExtra("call_time", items.get(position).getTime());
            intent.putExtra("call_date", items.get(position).getDate());
            intent.putExtra("call_duration", items.get(position).getDuration());

            // Start the new activity
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
