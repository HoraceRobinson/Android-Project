package com.example.ticketpurchase;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.xuexiang.xui.widget.banner.widget.banner.BannerItem;
import com.xuexiang.xui.widget.banner.widget.banner.SimpleImageBanner;

import java.util.ArrayList;
import java.util.List;

public class HomeFragement extends Fragment {

    public View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragement_home, container, false);
        initFragment();
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
