package com.example.ticketpurchase;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PatternFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PatternFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public PatternFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static PatternFragment newInstance() {
        PatternFragment fragment = new PatternFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pattern, container, false);
        Button btn1 = view.findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                btn1.setText("Clicked");
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
}