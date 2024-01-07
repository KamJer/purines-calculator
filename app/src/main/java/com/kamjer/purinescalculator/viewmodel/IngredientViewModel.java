package com.kamjer.purinescalculator.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.kamjer.purinescalculator.datarepository.DataRepository;
import com.kamjer.purinescalculator.model.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class IngredientViewModel extends ViewModel {
    private final List<Ingredient> ing = new ArrayList<>();
    private DataRepository dataRepository;

    public List<Ingredient> getIng() {
        return ing;
    }
    public DataRepository getDataRepository() {
        return dataRepository;
    }

    public int calculateUricAcid() {
//        creating fields
        int uricAcid = 0;
//        looping thru list of ingredients
        for (int i = 0; i < ing.size(); i++) {
//            checking if ingredient and weight is null for what ever reason so that nullPointer does not happen
            if (ing.get(i) != null) {
//                checking if data exists in a lookup table so that nullPointer does not happen
                if (dataRepository.getPurinesLookUpTable().get(ing.get(i).getName()) != null) {
//                    calculating uric acid
                    uricAcid += (int) (Double.valueOf(dataRepository.getPurinesLookUpTable().get(ing.get(i).getName())) / 100 * ing.get(i).getWeight());
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
        ing.add(ingredient);
    }

    public void removeIngredient(int position){
        ing.remove(position);
    }

    public int ingredientsSize() {
        return ing.size();
    }
}
