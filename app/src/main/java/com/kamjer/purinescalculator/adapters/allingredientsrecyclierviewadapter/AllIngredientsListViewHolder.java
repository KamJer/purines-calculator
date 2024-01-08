package com.kamjer.purinescalculator.adapters.allingredientsrecyclierviewadapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kamjer.purinescalculator.R;

public class AllIngredientsListViewHolder extends RecyclerView.ViewHolder{

    private TextView textViewItem;
    private TextView textViewAcidContent;

    public AllIngredientsListViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewItem = itemView.findViewById(R.id.textViewItem);
        textViewAcidContent = itemView.findViewById(R.id.textViewWeight);
    }

    public TextView getTextViewItem() {
        return textViewItem;
    }

    public TextView getTextViewAcidContent() {
        return textViewAcidContent;
    }
}
