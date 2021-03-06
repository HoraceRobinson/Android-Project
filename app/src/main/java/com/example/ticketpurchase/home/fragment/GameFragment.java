package com.example.ticketpurchase.home.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ticketpurchase.home.fragment.game.ChainActivity;
import com.example.ticketpurchase.home.fragment.game.FindActivity;
import com.example.ticketpurchase.R;
import com.example.ticketpurchase.home.fragment.game.ReverseChainActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GameFragment extends Fragment {

    public static GameFragment newInstance() {
        GameFragment fragment = new GameFragment();
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
        View view = inflater.inflate(R.layout.fragment_game, container, false);
        View chain = view.findViewById(R.id.chain);
        chain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ChainActivity.class));
            }
        });
        View reverseChain = view.findViewById(R.id.chain_reverse);
        reverseChain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ReverseChainActivity.class));
            }
        });
        View findIdiom = view.findViewById(R.id.find_idiom);
        findIdiom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), FindActivity.class));
            }
        });
        return view;
    }
}