package com.example.ticketpurchase;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.SearchView;

import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.xuexiang.xui.widget.banner.widget.banner.BannerItem;
import com.xuexiang.xui.widget.banner.widget.banner.SimpleImageBanner;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    public View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragement_home, container, false);
        initFragment();
        SearchView searchView = view.findViewById(R.id.searchView);
        EditText txtSearch = (searchView.findViewById(androidx.appcompat.R.id.search_src_text));
        txtSearch.setTextColor(getActivity().getResources().getColor(R.color.rou));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // query result
                Intent intent = new Intent(getContext(), SearchActivity.class);
                intent.putExtra("search_key", txtSearch.getText().toString());
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // ambiguous query
                return false;
            }
        });
        return view;
    }

    private void initFragment() {
        List<BannerItem> bannerItems = new ArrayList<>();
        for(int i = 0; i < 2; i++){
            BannerItem bannerItem = new BannerItem();
            bannerItem.imgUrl = "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fbkimg.cdn.bcebos.com%2Fpic%2F3b87e950352ac65c65598e78f5f2b21193138a59&refer=http%3A%2F%2Fbkimg.cdn.bcebos.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1657007475&t=9ad857ca1880440f93ae4d712625d7d9";
            bannerItem.title = "成语接龙";
            bannerItems.add(bannerItem);
        }
        SimpleImageBanner banner = view.findViewById(R.id.banner_intro);
        banner.setSource(bannerItems);
    }


}
