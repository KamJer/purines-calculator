package com.kamjer.purinescalculator.adapters.ingredientsrecyclierviewadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kamjer.purinescalculator.R;
import com.kamjer.purinescalculator.model.Ingredient;

import java.util.List;

public class IngredientsListAdapter extends RecyclerView.Adapter<IngredientsListViewHolder> {

    private List<Ingredient> ingredients;
    private LayoutInflater mInflater;

    private int selectedPosition = 0;

    public IngredientsListAdapter(Context context, List<Ingredient> ingredients) {
        this.ingredients = ingredients;
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public IngredientsListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.ing_list_view, parent, false);
        return new IngredientsListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsListViewHolder holder, int position) {
        holder.getTextViewItem().setText(ingredients.get(position).getName());
        String weightValue = ingredients.get(position).getWeight() + " g";
        holder.getTextViewWeight().setText(weightValue);
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
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
