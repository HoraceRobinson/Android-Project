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
import com.example.ticketpurchase.StarItemActivity;
import com.example.ticketpurchase.room.DBEngine;
import com.example.ticketpurchase.room.Idiom;

import java.util.List;

public class StarAdapter extends RecyclerView.Adapter<StarAdapter.MyViewHolder> {

    private List<Idiom> idioms;
    private StarItemActivity context;
    private DBEngine dbEngine;

    public StarAdapter(List<Idiom> idioms, StarItemActivity context) {
        this.idioms = idioms;
        this.context = context;
        this.dbEngine = new DBEngine(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.recycle_idiom_item,null);
        MyViewHolder holder = new MyViewHolder(view);
        holder.linearLayout.setOnClickListener(v -> {
            Intent intent = new Intent(context, IdiomActivity.class);
            intent.putExtra("idiom_text", holder.content.getText());
            context.startActivity(intent);
        });
        holder.button.setOnClickListener(v -> {
            dbEngine.deleteTheIdiom((String) holder.content.getText());
            dbEngine.getAllIdioms(context);
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.content.setText(idioms.get(position).getIdiom());
        holder.pinyin.setText(idioms.get(position).getPinyin());
        holder.shiyi.setText(idioms.get(position).getMeaning());
    }

    @Override
    public int getItemCount() {
        return idioms == null ? 0 : idioms.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView content;
        private TextView pinyin;
        private TextView shiyi;
        private Button button;
        private LinearLayout linearLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.idiom_content);
            pinyin = itemView.findViewById(R.id.pinyin);
            shiyi = itemView.findViewById(R.id.shiyi);
            button = itemView.findViewById(R.id.remove_btn);
            linearLayout = itemView.findViewById(R.id.linear_cont);
        }
    }
}
