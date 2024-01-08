package com.kamjer.purinescalculator.adapters.allingredientsrecyclierviewadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kamjer.purinescalculator.R;
import com.kamjer.purinescalculator.model.Ingredient;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class AllIngredientsListAdapter extends RecyclerView.Adapter<AllIngredientsListViewHolder> {

    private List<Object> ingredients;
    private List<Object> acidContent;
    private LayoutInflater mInflater;

    private int selectedPosition = 0;

    public AllIngredientsListAdapter(Context context, HashMap<String, Integer> ingredients) {
//        does not hold an order by this does not matter so i am not fixing it, values of acidContent and there keys will be in the same order
        this.ingredients = Arrays.asList(ingredients.keySet().toArray());
        this.acidContent = Arrays.asList((ingredients.values().toArray()));
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public AllIngredientsListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.all_ing_recycler_view, parent, false);
        return new AllIngredientsListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllIngredientsListViewHolder holder, int position) {
        holder.getTextViewItem().setText(ingredients.get(position).toString());
        String weightValue = acidContent.get(position).toString();
        holder.getTextViewAcidContent().setText(weightValue);
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    public int getIngredientsSize() {
        return ingredients.size();
    }
    
    public boolean isLastSelected() {
        return selectedPosition == ingredients.size();
    }
}
