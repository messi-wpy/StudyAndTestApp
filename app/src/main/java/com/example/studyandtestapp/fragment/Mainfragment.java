package com.example.studyandtestapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.studyandtestapp.R;
import com.example.studyandtestapp.adapter.MainRecycleviewAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

public class Mainfragment extends BaseFragment {


    private RecyclerView recyclerView;
    private FragmentManager fragmentManager;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        View view=inflater.inflate(R.layout.fragment_recycleview,container,false);
        recyclerView=view.findViewById(R.id.project_recycle);
        fragmentManager=getChildFragmentManager();
        recyclerView.setAdapter(new MainRecycleviewAdapter(new MainRecycleviewAdapter.onItemClickPresenter() {
            @Override
            public void click(int position) {
               if (fragmentManager.findFragmentByTag("fragment"+position)==null){
                   CommonFragment fragment=CommonFragment.newInstance(String.valueOf(position));
                   fragmentManager.beginTransaction()
                           .add(R.id.fragment_layout,fragment,"fragment"+position)
                           .commit();


               };
            }
        }));


        return  view;
    }
}
