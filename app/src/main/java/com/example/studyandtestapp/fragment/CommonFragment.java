package com.example.studyandtestapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.studyandtestapp.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CommonFragment extends BaseFragment {



    public static CommonFragment newInstance(String name) {

        Bundle args = new Bundle();
        args.putString("name",name);
        CommonFragment fragment = new CommonFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_common,container,false);
        TextView textView=view.findViewById(R.id.fragment_text);
        textView.setText(getArguments().getString("name"));
        return view;
    }
}
