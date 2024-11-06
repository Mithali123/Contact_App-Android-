package com.example.contact;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {

    TextView name,phone,date,duration;
    TextView initial,day,time,call_type;
    ImageView call_button;

    public MyViewHolder(@NonNull View item_view) {
        super(item_view);
        name=item_view.findViewById(R.id.contact_name);
        phone=item_view.findViewById(R.id.phone_number);
        day= item_view.findViewById(R.id.call_day);
        initial= item_view.findViewById(R.id.initial);
        time= item_view.findViewById(R.id.call_time);
        call_button= item_view.findViewById(R.id.call_button);

        date= item_view.findViewById(R.id.call_date);
        duration=item_view.findViewById(R.id.call_duration);



    }
}
