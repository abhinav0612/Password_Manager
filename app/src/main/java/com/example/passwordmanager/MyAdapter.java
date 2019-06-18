package com.example.passwordmanager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.passwordmanager.Room.PasswordDatabase;
import com.example.passwordmanager.Room.PasswordEntity;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.PasswordHolder> {
    List<PasswordEntity> pass_list = new ArrayList<>();
    onitemclicklistner listner;

    @NonNull
    @Override
    public PasswordHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .custom_layout, parent, false);
        return new PasswordHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PasswordHolder holder, int position) {
        PasswordEntity passwordEntity = pass_list.get(position);
        holder.title.setText(passwordEntity.getTitle());

    }

    @Override
    public int getItemCount() {
        return pass_list.size();
    }

    class PasswordHolder extends RecyclerView.ViewHolder {
        private TextView title;

        public PasswordHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.textview_title);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listner!=null && position!=RecyclerView.NO_POSITION)
                    {
                        listner.onitemclick(pass_list.get(position));
                    }
                }
            });
        }


    }

    void setnotes(List<PasswordEntity> passwordEntities) {
        this.pass_list = passwordEntities;
        notifyDataSetChanged();
    }
    PasswordEntity getpassAt(int position)
    {
        return pass_list.get(position);
    }

    public interface onitemclicklistner
    {
        public void onitemclick(PasswordEntity passwordEntity);
    }
    public void setonitemclicklistner(onitemclicklistner listner)
    {
     this.listner = listner;
    }
}
