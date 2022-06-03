package com.example.ticketpurchase;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class IdiomListFragment extends Fragment {

    private FragmentManager fragmentManager;
    private List<String> idioms;

    public IdiomListFragment() {
    }

    public IdiomListFragment(FragmentManager fragmentManager, ArrayList<String> idioms) {
        this.fragmentManager = fragmentManager;
        this.idioms = idioms;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RecyclerView recyclerView = getActivity().findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new MyAdapter(idioms, getActivity()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_collect, container, false);
    }


    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

        private List<String> idioms;
        private Context context;

        public MyAdapter(List<String> idioms, Context context) {
            this.idioms = idioms;
            this.context = context;
        }

        @NonNull
        @Override
        public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = View.inflate(context,R.layout.collect_idiom,null);
            MyViewHolder holder = new MyViewHolder(view);
            holder.idiom.setOnClickListener(v -> {
//                获取成语信息
                Intent intent = new Intent(getContext(),IdiomContentFragment.class);
                intent.putExtra("content",holder.idiom.getText());
                startActivity(intent);
            });
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
            holder.idiom.setText(idioms.get(position));
        }

        @Override
        public int getItemCount() {
            return 0;
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            private TextView idiom;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                idiom = itemView.findViewById(R.id.collect_idiom);
            }
        }
    }

}