package com.example.studyandtestapp.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.studyandtestapp.R;
import com.example.studyandtestapp.adapter.MainRecycleviewAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Mainfragment extends BaseFragment {


    private RecyclerView recyclerView;
    private FragmentManager fragmentManager;
    private Button button;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        View view=inflater.inflate(R.layout.fragment_recycleview,container,false);
        recyclerView=view.findViewById(R.id.project_recycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        fragmentManager=getChildFragmentManager();
        button=view.findViewById(R.id.bt_back);

        button.setOnClickListener(v -> {
        fragmentManager.popBackStack();

        });
        recyclerView.setAdapter(new MainRecycleviewAdapter(new MainRecycleviewAdapter.onItemClickPresenter() {
            @Override
            public void click(int position) {
               if (fragmentManager.findFragmentByTag("fragment"+position)==null){
                   CommonFragment fragment=CommonFragment.newInstance(String.valueOf(position));
                   FragmentTransaction transaction=fragmentManager.beginTransaction();
                   if (position==7){
                       transaction
                               .replace(R.id.main_fragment_contain, fragment, "fragment" + position)
                               .addToBackStack("name");
                   }else {
                       transaction
                               .add(R.id.main_fragment_contain, fragment, "fragment" + position);
                   }
                   transaction
                           .commit();
                if (fragmentManager.findFragmentById(R.id.main_fragment_contain)!=null)
                   Log.i(TAG, "click: "+fragmentManager.findFragmentById(R.id.main_fragment_contain).getClass().getSimpleName());
               }else {
                   Fragment fragment=fragmentManager.findFragmentByTag("fragment"+position);
                   if (fragment.isHidden()||fragment.isVisible()) {
                       fragmentManager.beginTransaction()
                               .show(fragment)
                               .commit();
                   }else {
                       fragmentManager.beginTransaction()
                               .hide(fragment)
                               .commit();
                   }

               }
            }
        }));


        return  view;
    }




}
