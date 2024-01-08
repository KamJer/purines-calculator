package com.kamjer.purinescalculator.adapters.ingredientsrecyclierviewadapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kamjer.purinescalculator.R;

public class IngredientsListViewHolder extends RecyclerView.ViewHolder{

    private TextView textViewItem;
    private TextView textViewWeight;

    public IngredientsListViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewItem = itemView.findViewById(R.id.textViewItem);
        textViewWeight = itemView.findViewById(R.id.textViewWeight);
    }

    public TextView getTextViewItem() {
        return textViewItem;
    }

    public TextView getTextViewWeight() {
        return textViewWeight;
    }
}
