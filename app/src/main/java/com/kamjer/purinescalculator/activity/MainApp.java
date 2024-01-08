package com.kamjer.purinescalculator.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kamjer.purinescalculator.R;
import com.kamjer.purinescalculator.adapters.ingredientsrecyclierviewadapter.IngredientsListAdapter;
import com.kamjer.purinescalculator.datarepository.DataRepository;
import com.kamjer.purinescalculator.model.Ingredient;
import com.kamjer.purinescalculator.viewmodel.IngredientViewModel;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class MainApp extends AppCompatActivity {

    private IngredientViewModel ingredientViewModel;

    @Override
    protected void onStart() {
        super.onStart();
        setContentView(R.layout.main_activity_layout);

        try {
            try {
                DataRepository repository = new DataRepository(this);
                ingredientViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(IngredientViewModel.class);
                ingredientViewModel.setDataRepository(repository);

//            creating array for an adapter (it can not take hashMap as parameter)
                HashMap<String, Integer> purinesLookUpTable = ingredientViewModel.getDataRepository().getPurinesLookUpTable();
                String[] autocompleteData = new String[purinesLookUpTable.size()];
                purinesLookUpTable.keySet().toArray(autocompleteData);
//            Handling autoCompleteText
                AutoCompleteTextView autoCompleteTextView = findViewById(R.id.editTextIngredient);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.select_dialog_item, autocompleteData);
                autoCompleteTextView.setAdapter(adapter);
//            Handling editTexts
                EditText editTextWeight = findViewById(R.id.editTextWeight);
//            Handling View Texts
                TextView sumText = findViewById(R.id.textSum);
////            Handling list
                RecyclerView recyclerView = findViewById(R.id.ingredientList);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                IngredientsListAdapter ingredientsListAdapter = new IngredientsListAdapter(this, new ArrayList<>());
                recyclerView.setAdapter(ingredientsListAdapter);

//            Handling Buttons and actions for them
                Button addButton = findViewById(R.id.addButton);
                addButton.setOnClickListener(v -> addIngAction(v, autoCompleteTextView, editTextWeight, sumText, ingredientsListAdapter));

                Button removeButton = findViewById(R.id.removeButton);
                removeButton.setOnClickListener(v -> removeIngButtonAction(v, sumText, ingredientsListAdapter));

                Button settingButton = findViewById(R.id.settingButton);
                settingButton.setOnClickListener(this::settingButtonAction);

                ingredientViewModel.getIngredients().observe(this, newData -> {
                    ingredientsListAdapter.setIngredients(newData);
                    ingredientsListAdapter.notifyDataSetChanged();
                });
            } catch (Resources.NotFoundException | XmlPullParserException | IOException e) {
//                if there is an issue show it to the user, if lookup table does not load there is no point in app working, it relies on it working
                Toast.makeText(this, "Nie można załadować tabeli, problem z danymi", Toast.LENGTH_LONG).show();
                finishAndRemoveTask();
            }
        } catch (Exception e) {
//            if there is an issue show it to the user
            Toast.makeText(this, "error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void addIngAction(View view, AutoCompleteTextView autoCompleteTextView, EditText weightText, TextView sumView, IngredientsListAdapter adapter){
//        checking if data exists in a lookup table, without it no calculation can be done
        String ingName = String.valueOf(autoCompleteTextView.getText());

        if (ingredientViewModel.validateExistance(ingName)) {
//            adding data to the list
//            validating on a xml level
            int weightTemp = 100;
//            still validating to be sure, if string is not integer add default value of a 100
            try {
                weightTemp = Integer.parseInt(String.valueOf(weightText.getText()));
            } catch (NumberFormatException ignored) {}

//            creating ingredient
            Ingredient ingTemp = new Ingredient(ingName, weightTemp);
            ingredientViewModel.addIngredients(ingTemp);

//            calculating uric acid
            int sum = ingredientViewModel.calculateUricAcid();
//            showing calculations
            sumView.setText(String.valueOf(sum));
//            clearing texts on a screen
            autoCompleteTextView.setText("");
            weightText.setText("");
//            setting position to be the last element (the last added element will be a default do delete)
            adapter.setSelectedPosition(adapter.getIngredientsSize() - 1);
        }
    }

    public void removeIngButtonAction(View view, TextView textView, IngredientsListAdapter adapter) {
//        checking if item on a list was selected (there is no situation when this happens but for safety is here)
        if (adapter.getSelectedPosition() != AdapterView.INVALID_POSITION) {
//            removing items from a list
            ingredientViewModel.removeIngredient(adapter.getSelectedPosition());
//            recalculating uric acid
            int sum = ingredientViewModel.calculateUricAcid();
//            displaying new uric acid
            textView.setText(String.valueOf(sum));
//            selecting new position on a listView if a last element was deleted, if not, selected position remains
            if (adapter.isLastSelected()){
                adapter.setSelectedPosition(adapter.getSelectedPosition() - 1);
            }
        }
    }

    private void settingButtonAction(View v) {
        Intent myIntent = new Intent(this, SettingsActivity.class);
        myIntent.putExtra("key", ingredientViewModel.getDataRepository().getPurinesLookUpTable());
        this.startActivity(myIntent);
    }

    private void addIngredientToTheList(Ingredient ingredient) {
        ingredientViewModel.addIngredients(ingredient);
    }
}
