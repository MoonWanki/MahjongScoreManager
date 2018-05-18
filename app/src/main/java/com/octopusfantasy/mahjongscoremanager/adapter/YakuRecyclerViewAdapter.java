package com.octopusfantasy.mahjongscoremanager.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.octopusfantasy.mahjongscoremanager.R;
import com.octopusfantasy.mahjongscoremanager.dialog.AgariDialog;
import com.octopusfantasy.mahjongscoremanager.model.Yaku;

import java.util.ArrayList;

public class YakuRecyclerViewAdapter extends RecyclerView.Adapter<YakuRecyclerViewAdapter.ItemViewHolder> {

    ArrayList<Yaku> yakuArrayList;
    AgariDialog parent;

    public YakuRecyclerViewAdapter(ArrayList<Yaku> yakus, AgariDialog parent) {
        yakuArrayList = yakus;
        this.parent = parent;
    }

    // 커스텀 뷰홀더
    class ItemViewHolder extends RecyclerView.ViewHolder{

        private TextView yakuNameText;
        private boolean selected;

        private ItemViewHolder(View itemView) {
            super(itemView);
            yakuNameText = itemView.findViewById(R.id.yakuNameText);
            selected = false;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!selected) {
                        selected = true;
                        yakuNameText.setBackgroundColor(Color.WHITE);
                        yakuNameText.setTextColor(Color.BLACK);
                        parent.onYakuItemClick(yakuArrayList.get(getAdapterPosition()), true);
                    } else {
                        selected = false;
                        yakuNameText.setBackgroundColor(Color.TRANSPARENT);
                        yakuNameText.setTextColor(Color.WHITE);
                        parent.onYakuItemClick(yakuArrayList.get(getAdapterPosition()), false);
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler, parent,false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.yakuNameText.setText(yakuArrayList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return yakuArrayList.size();
    }
}
