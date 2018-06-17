package com.example.asad.seric.madproj;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    String KEY;

    List<Passwords> passwordsList;
    Activity storedAct;

    MyAdapter(List<Passwords> p, Activity a, String key){
        KEY = key;
        passwordsList = p;
        storedAct = a;
    }

    @Override
    public int getItemCount() {
        return passwordsList.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_cell, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Passwords p = passwordsList.get(position);
        holder.display(p);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        Passwords currentPair;

        public MyViewHolder(final View itemView) {
            super(itemView);

            name = (itemView.findViewById(R.id.name));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                new PasswordOutputDialogClass(storedAct,
                currentPair, KEY).show();
                }
            });
        }

        public void display(Passwords p) {
            currentPair = p;
            name.setText(p.SiteName);
        }
    }
}