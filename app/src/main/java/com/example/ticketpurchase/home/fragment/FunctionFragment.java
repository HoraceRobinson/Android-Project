package com.example.ticketpurchase.home.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ticketpurchase.home.fragment.function.MeaningFragment;
import com.example.ticketpurchase.home.fragment.function.PatternFragment;
import com.example.ticketpurchase.home.fragment.function.PronounceFragment;
import com.example.ticketpurchase.R;
import com.google.android.material.tabs.TabLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FunctionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FunctionFragment extends Fragment {

    private TabLayout tabLayout = null;
    private ViewPager viewPager;
    private Fragment[] fragments = new Fragment[3];
    private String[] tabList = new String[3];

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FunctionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FunctionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FunctionFragment newInstance(String param1, String param2) {
        FunctionFragment fragment = new FunctionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_function, container, false);
        tabLayout = view.findViewById(R.id.tab_layout1);
        viewPager = view.findViewById(R.id.view_pager1);
        initView();
        return view;
    }

    private void initView() {
        tabList[0] = "形";
        tabList[1] = "音";
        tabList[2] = "义";
        fragments[0] = new PatternFragment();
        fragments[1] = new PronounceFragment();
        fragments[2] = new MeaningFragment();
        MyAdapter myAdapter = new MyAdapter(getChildFragmentManager());
        viewPager.setAdapter(myAdapter);
//        预加载三个fragment
        viewPager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(viewPager);
    }

    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fragmentManager){
            super(fragmentManager);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments[position];
        }

        @Override
        public int getCount() {
            return fragments.length;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return tabList[position];
        }


    }
}