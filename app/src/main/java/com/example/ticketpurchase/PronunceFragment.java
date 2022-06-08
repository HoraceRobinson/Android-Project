package com.example.ticketpurchase;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PronunceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PronunceFragment extends Fragment {


    public PronunceFragment() {
        // Required empty public constructor
    }

    public static PronunceFragment newInstance() {
        PronunceFragment fragment = new PronunceFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pronunce, container, false);
    }
}