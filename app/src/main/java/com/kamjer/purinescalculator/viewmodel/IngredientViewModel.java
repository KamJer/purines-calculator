package com.kamjer.purinescalculator.viewmodel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.kamjer.purinescalculator.datarepository.DataRepository;
import com.kamjer.purinescalculator.model.Ingredient;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
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
//        looping through list of ingredients
        if (ingredients.getValue() != null) {
            for (int i = 0; i < ingredients.getValue().size(); i++) {
//            checking if ingredient and weight is null for whatever reason so that nullPointer does not happen
                if (ingredients.getValue().get(i) != null) {
//                checking if data exists in a lookup table so that nullPointer does not happen
                    uricAcid += calculateUricAcidForIngredient(ingredients.getValue().get(i).getName(), ingredients.getValue().get(i).getWeight());
                }
            }
        }
        return uricAcid;
    }

    public int calculateUricAcidForIngredient(String name, int weight) {
        Integer uricValForIng = dataRepository.getPurinesLookUpTable().get(name);
        if (uricValForIng != null) {
            Double doubleUricValForIng = Double.valueOf(uricValForIng);
            return (int) ((double) (doubleUricValForIng / 100) * weight);
        }
        return 0;
    }

    public boolean validateExistence(String key) {
        return dataRepository.getPurinesLookUpTable().get(key) != null;
    }

    public void createDataRepository(Context context) throws XmlPullParserException, IOException {
        if (dataRepository == null) {
            dataRepository = new DataRepository(context);
        }
    }

    public void addIngredients(Ingredient ingredient) {
        List<Ingredient> listTemp = ingredients.getValue();
        listTemp.add(ingredient);
        ingredients.setValue(listTemp);
    }

    public void removeIngredient(int position) {
        List<Ingredient> listTemp = ingredients.getValue();
        listTemp.remove(position);
        ingredients.setValue(listTemp);
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
