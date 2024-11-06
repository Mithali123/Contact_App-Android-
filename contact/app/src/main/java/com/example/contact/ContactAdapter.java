// ContactAdapter.java
package com.example.contact;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Contact> contacts;
    private Context context;

    public ContactAdapter(Context context, List<Contact> contacts) {
        this.context = context;
        this.contacts = contacts;
    }

    public void updateContacts(List<Contact> newContacts) {
        this.contacts.clear();
        this.contacts.addAll(newContacts);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        // Determine if the item is a header or a contact
        // This can be expanded based on your header logic
        return (position == 0 || contacts.get(position).getName().charAt(0) != contacts.get(position - 1).getName().charAt(0)) ? 0 : 1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_header, parent, false);
            return new HeaderViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item, parent, false);
            return new ContactViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {
            HeaderViewHolder headerHolder = (HeaderViewHolder) holder;
            headerHolder.bind(contacts.get(position).getName());
        } else if (holder instanceof ContactViewHolder) {
            ContactViewHolder contactHolder = (ContactViewHolder) holder;
            contactHolder.bind(contacts.get(position));
            contactHolder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, ProfileActivity.class);
                intent.putExtra("ID", String.valueOf(contacts.get(position).getId()));
                context.startActivity(intent);
            });
        }
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView headerText;

        HeaderViewHolder(View itemView) {
            super(itemView);
            headerText = itemView.findViewById(R.id.header_text);
        }

        void bind(String header) {
            headerText.setText(header.substring(0, 1)); // Display first letter as header
        }
    }

    static class ContactViewHolder extends RecyclerView.ViewHolder {
        TextView contactName;

        ContactViewHolder(View itemView) {
            super(itemView);
            contactName = itemView.findViewById(R.id.contact_name);
        }

        void bind(Contact contact) {
            contactName.setText(contact.getName());
        }
    }
}
