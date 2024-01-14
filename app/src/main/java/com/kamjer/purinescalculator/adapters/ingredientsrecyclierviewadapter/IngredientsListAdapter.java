package com.kamjer.purinescalculator.adapters.ingredientsrecyclierviewadapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.kamjer.purinescalculator.R;
import com.kamjer.purinescalculator.datarepository.DataRepository;
import com.kamjer.purinescalculator.model.Ingredient;
import com.kamjer.purinescalculator.viewmodel.IngredientViewModel;

import java.util.ArrayList;
import java.util.List;

public class IngredientsListAdapter extends RecyclerView.Adapter<IngredientsListViewHolder> {

    private static final int SELECTION_COLOR = 0xFFC4C4FC;
    public static final int INVALID_POSITION_SELECTED = -1;

    private List<Ingredient> ingredients;
    private LayoutInflater mInflater;
    private int selectedPosition = 0;

    private IngredientViewModel ingredientViewModel;


    public IngredientsListAdapter(Context context, List<Ingredient> ingredients, IngredientViewModel ingredientViewModel) {
        this.ingredients = ingredients;
        this.mInflater = LayoutInflater.from(context);
        this.ingredientViewModel = ingredientViewModel;
    }

    @NonNull
    @Override
    public IngredientsListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.ing_list_view, parent, false);
        return new IngredientsListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsListViewHolder holder, int position) {
        if (selectedPosition == -1) {
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
        } else {
            if (selectedPosition == holder.getAdapterPosition()) {
                holder.itemView.setBackgroundColor(SELECTION_COLOR);
            } else {
                holder.itemView.setBackgroundColor(Color.TRANSPARENT);
            }
        }
        String name = ingredients.get(position).getName();
        int weight = ingredients.get(position).getWeight();
        holder.getTextViewItem().setText(name);
        holder.getTextViewWeight().setText(String.valueOf(weight));
        holder.getTextViewAcidContentForWeight().setText(String.valueOf(ingredientViewModel.calculateUricAcidForIngredient(name, weight)));
        holder.getTextViewUricAcidContent().setText(ingredientViewModel.getDataRepository().getValueAsString(name));

        holder.itemView.setOnClickListener(view -> clickOnItemAction(holder, selectedPosition));
    }

    private void clickOnItemAction(IngredientsListViewHolder holder, int selectedPosition) {
        holder.itemView.setBackgroundColor(SELECTION_COLOR);
        if (selectedPosition != holder.getAdapterPosition()) {
            notifyItemChanged(selectedPosition);
            this.selectedPosition = holder.getAdapterPosition();
        }
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }


    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
        notifyDataSetChanged();
    }

    public int getSelectedPosition() {
        if (selectedPosition >= ingredients.size()) {
            selectedPosition = INVALID_POSITION_SELECTED;
        }
        return selectedPosition;
    }

    public Ingredient getSelected() {
        if (selectedPosition != INVALID_POSITION_SELECTED) {
            return ingredients.get(selectedPosition);
        }
        return null;
    }

}
