package com.example.ticketpurchase.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ticketpurchase.IdiomActivity;
import com.example.ticketpurchase.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class IdiomAdapter extends RecyclerView.Adapter<IdiomAdapter.ViewHolder>{

    private JSONArray jsonArray = new JSONArray();
    private Context context;

    public IdiomAdapter(JSONArray jsonArray, Context context){
        this.jsonArray = jsonArray;
        this.context = context;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView content;
        private TextView pinyin;
        private TextView shiyi;
        private LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.idiom_content);
            pinyin = itemView.findViewById(R.id.pinyin);
            shiyi = itemView.findViewById(R.id.shiyi);
            linearLayout = itemView.findViewById(R.id.linear_cont);
        }

        public TextView getContent() {
            return content;
        }

        public TextView getPinyin() {
            return pinyin;
        }

        public TextView getShiyi() {
            return shiyi;
        }

        public LinearLayout getLinearLayout() {
            return linearLayout;
        }
    }

    @NonNull
    @Override
    public IdiomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.recycle_idiom_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IdiomAdapter.ViewHolder holder, int position) {
        try{
            JSONObject jsonObject = jsonArray.getJSONObject(position);
            holder.getContent().setText((String)jsonObject.get("idiom"));
            holder.getPinyin().setText((String)jsonObject.get("pinyin"));
            holder.getShiyi().setText((String)jsonObject.get("shiyi"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        holder.getLinearLayout().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, IdiomActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return jsonArray == null ? 0 : jsonArray.length();
    }
}
