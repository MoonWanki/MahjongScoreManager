package com.octopusfantasy.mahjongscoremanager.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.octopusfantasy.mahjongscoremanager.R;
import com.octopusfantasy.mahjongscoremanager.model.Yaku;

import java.util.ArrayList;

public class SatisfiedYakuRecyclerAdapter extends RecyclerView.Adapter<SatisfiedYakuRecyclerAdapter.ItemViewHolder> {

    private ArrayList<Yaku> satisfiedYakuList;
    private boolean isClosed;

    public SatisfiedYakuRecyclerAdapter(ArrayList<Yaku> arr, boolean isClosed) {
        satisfiedYakuList = arr;
        this.isClosed = isClosed;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView yakuNameText;
        private TextView yakuHanText;

        public ItemViewHolder(View itemView) {
            super(itemView);
            yakuNameText = itemView.findViewById(R.id.satisfiedYakuName);
            yakuHanText = itemView.findViewById(R.id.satisfiedYakuHan);
        }
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_satisfied_yaku, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        int han;
        holder.yakuNameText.setText(satisfiedYakuList.get(position).getName().toString());
        if(isClosed) han = satisfiedYakuList.get(position).getHan();
        else han = satisfiedYakuList.get(position).getOpenedHan();
        holder.yakuHanText.setText(String.valueOf(han) + "판");
    }

    @Override
    public int getItemCount() {
        return satisfiedYakuList.size();
    }
}
