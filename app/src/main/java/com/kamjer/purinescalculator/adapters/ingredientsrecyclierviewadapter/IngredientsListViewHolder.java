package com.kamjer.purinescalculator.adapters.ingredientsrecyclierviewadapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kamjer.purinescalculator.R;

import org.w3c.dom.Text;

public class IngredientsListViewHolder extends RecyclerView.ViewHolder {

    private TextView textViewItem;
    private TextView textViewWeight;
    private TextView textViewAcidContentForWeight;
    private TextView textViewUricAcidContent;

    public IngredientsListViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewItem = itemView.findViewById(R.id.textViewItem);
        textViewWeight = itemView.findViewById(R.id.textViewWeight);
        textViewAcidContentForWeight = itemView.findViewById(R.id.textViewUricAcidContentForWeight);
        textViewUricAcidContent = itemView.findViewById(R.id.textViewUricAcidContent);

    }

    public TextView getTextViewItem() {
        return textViewItem;
    }

    public TextView getTextViewWeight() {
        return textViewWeight;
    }

    public TextView getTextViewAcidContentForWeight() {
        return textViewAcidContentForWeight;
    }

    public TextView getTextViewUricAcidContent() {
        return textViewUricAcidContent;
    }
}
