package com.kamjer.purinescalculator.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.kamjer.purinescalculator.datarepository.DataRepository;
import com.kamjer.purinescalculator.model.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class IngredientViewModel extends ViewModel {
    private final MutableLiveData<List<Ingredient>> ingredients = new MutableLiveData<>();
    private DataRepository dataRepository;

    public IngredientViewModel() {
        createObserverList();
    }

    public MutableLiveData<List<Ingredient>> getIngredients() {
        return ingredients;
    }
    public DataRepository getDataRepository() {
        return dataRepository;
    }

    public int calculateUricAcid() {
//        creating fields
        int uricAcid = 0;
//        looping thru list of ingredients
        for (int i = 0; i < ingredients.getValue().size(); i++) {
//            checking if ingredient and weight is null for whatever reason so that nullPointer does not happen
            if (ingredients.getValue().get(i) != null) {
//                checking if data exists in a lookup table so that nullPointer does not happen
                Integer uricValForIng = dataRepository.getPurinesLookUpTable().get(ingredients.getValue().get(i).getName());
                if (uricValForIng != null) {
//                    calculating uric acid
                    uricAcid += (Double.valueOf(uricValForIng) / 100 * ingredients.getValue().get(i).getWeight());
                }
            }
        }
        return uricAcid;
    }

    public boolean validateExistance(String key) {
        return dataRepository.getPurinesLookUpTable().get(key) != null;
    }

    public void setDataRepository(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    public void addIngredients(Ingredient ingredient) {
        ingredients.getValue().add(ingredient);
    }

    public void removeIngredient(int position){
        ingredients.getValue().remove(position);
    }

    public int ingredientsSize() {
        if (ingredients.getValue() == null) {
            createObserverList();
            return 0;
        }
        return ingredients.getValue().size();
    }

    public void addIngredients(List<Ingredient> ingredients) {
        this.ingredients.setValue(ingredients);
    }

    private void createObserverList() {
        ingredients.setValue(new ArrayList<>());
    }
}
