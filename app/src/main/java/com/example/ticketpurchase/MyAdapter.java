package com.example.ticketpurchase;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ticketpurchase.room.DBEngine;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private List<String> idioms;
    private CollectedIdiomActivity context;
    private DBEngine dbEngine;

    public MyAdapter(List<String> idioms, CollectedIdiomActivity context) {
        this.idioms = idioms;
        this.context = context;
        this.dbEngine = new DBEngine(context);
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.idiom_text,null);
        MyViewHolder holder = new MyViewHolder(view);
        holder.idiom.setOnClickListener(v -> {
            Intent intent = new Intent(context, IdiomContentActivity.class);
            intent.putExtra("idiom_text", holder.idiom.getText());
            context.startActivity(intent);
        });
        holder.button.setOnClickListener(v -> {
            dbEngine.deleteTheIdiom((String) holder.idiom.getText());
            dbEngine.getAllIdioms(context);
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        holder.idiom.setText(idioms.get(position));
    }

    @Override
    public int getItemCount() {
        return idioms == null ? 0 : idioms.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView idiom;
        private Button button;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            idiom = itemView.findViewById(R.id.item_idiom);
            button = itemView.findViewById(R.id.item_delete);
            System.out.println(idiom);
        }
    }
}
