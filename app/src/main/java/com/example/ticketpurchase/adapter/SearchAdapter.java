package com.example.ticketpurchase.adapter;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ticketpurchase.IdiomActivity;
import com.example.ticketpurchase.R;
import com.example.ticketpurchase.SearchActivity;
import com.example.ticketpurchase.StarItemActivity;
import com.example.ticketpurchase.room.DBEngine;
import com.example.ticketpurchase.room.Idiom;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {

    private List<String> idioms;
    private SearchActivity context;

    public SearchAdapter(List<String> idioms, SearchActivity context) {
        this.idioms = idioms;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.recycle_result_list,null);
        MyViewHolder holder = new MyViewHolder(view);
        holder.linearLayout.setOnClickListener(v -> {
            Intent intent = new Intent(context, IdiomActivity.class);
            intent.putExtra("idiom_text", holder.idiom.getText());
            context.startActivity(intent);
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.idiom.setText(idioms.get(position));
    }

    @Override
    public int getItemCount() {
        return idioms == null ? 0 : idioms.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView idiom;
        private LinearLayout linearLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            idiom = itemView.findViewById(R.id.idiom_result);
            linearLayout = itemView.findViewById(R.id.layout_cont);
        }
    }
}
