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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllIngredientsListAdapter extends RecyclerView.Adapter<AllIngredientsListViewHolder> {

    private List<String> ingredients;
    private List<Integer> acidContent;
    private LayoutInflater mInflater;

    public AllIngredientsListAdapter(Context context, Map<String, Integer> ingredients) {
        this.ingredients = new ArrayList<>(ingredients.keySet());
        this.acidContent = new ArrayList<>();
        Collections.sort(this.ingredients);
        for (int i = 0; i < ingredients.size(); i++) {
            acidContent.add(ingredients.get(this.ingredients.get(i)));
        }
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
        String acidContentValue = acidContent.get(position).toString() + " mg/100g";
        holder.getTextViewAcidContent().setText(acidContentValue);
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public int getIngredientsSize() {
        return ingredients.size();
    }
}
