package com.example.ticketpurchase;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ticketpurchase.adapter.IdiomAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StarIdiomFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StarIdiomFragment extends Fragment {

    public JSONArray jsonArray = new JSONArray();

    public StarIdiomFragment() {
        // Required empty public constructor
    }

    public static StarIdiomFragment newInstance() {
        StarIdiomFragment fragment = new StarIdiomFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            initData();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initData() throws JSONException {
        for(int i = 0; i < 5; i++){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("idiom", "贪生怕死");
            jsonObject.put("pinyin", "[ tān shēng pà sǐ ]");
            jsonObject.put("shiyi", "贪恋生存，害怕死亡。 ");
            jsonArray.put(jsonObject);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_star_idiom, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.idiom_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        IdiomAdapter idiomAdapter = new IdiomAdapter(jsonArray, getContext());
        recyclerView.setAdapter(idiomAdapter);
        // Inflate the layout for this fragment
        return view;
    }
}