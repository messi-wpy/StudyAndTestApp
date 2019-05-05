package com.example.studyandtestapp.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.studyandtestapp.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static com.example.studyandtestapp.fragment.BaseFragment.TAG;

public class MainRecycleviewAdapter extends RecyclerView.Adapter<MainRecycleviewAdapter.MainHolder> {


    private List<String>dataList;
    private onItemClickPresenter presenter;

    public void setDataList(){
        dataList=new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            dataList.add("item   "+i);
        }

    }

    public MainRecycleviewAdapter(onItemClickPresenter presenter){
        setDataList();

        this.presenter=presenter;
    }
    @NonNull
    @Override
    public MainHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_item,parent,false);
        MainHolder holder=new MainHolder(v);
        Log.i(TAG, "onCreateViewHolder: viewType(getItemViewType() return 0)="+viewType);
        return holder;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(@NonNull MainHolder holder, int position) {
        holder.textView.setText(dataList.get(position));
        holder.v.setOnClickListener(v -> {

            presenter.click(position);
        });
        Log.i(TAG, "onBindViewHolder: position="+position+"     layoutPosition="+holder.getLayoutPosition());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    class MainHolder extends RecyclerView.ViewHolder{

        View v;
        TextView textView;
        public MainHolder(@NonNull View itemView) {
            super(itemView);
            v=itemView;
            textView=itemView.findViewById(R.id.item_text);

        }
    }
    public interface onItemClickPresenter{
        void click(int positon);
    }
}
