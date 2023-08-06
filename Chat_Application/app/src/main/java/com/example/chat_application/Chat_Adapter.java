package com.example.chat_application;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Chat_Adapter extends RecyclerView.Adapter<Chat_Adapter.ViewHolder> {
    private List<String> listC;
    private Context context;

    public Chat_Adapter(List<String> listC) {
        this.listC = listC;
    }

    public void setMessage(String message) {
        listC.add(message);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvContent.setText(listC.get(position));
    }

    @Override
    public int getItemCount() {
        return listC.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvContent;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvContent = itemView.findViewById(R.id.itemTv);
        }
    }
}
