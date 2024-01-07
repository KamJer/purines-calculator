package com.kamjer.purinescalculator.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.kamjer.purinescalculator.R;
import com.kamjer.purinescalculator.datarepository.DataRepository;
import com.kamjer.purinescalculator.model.Ingredient;
import com.kamjer.purinescalculator.viewmodel.IngredientViewModel;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainApp extends AppCompatActivity {

    private IngredientViewModel ingredientViewModel;
    private int position = 0;

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
//            Handling list
                ListView ingListView = findViewById(R.id.listViewIng);
                ArrayAdapter<Ingredient> arrayAdapter = new ArrayAdapter<>(this, R.layout.ing_list_view, R.id.textViewItem, ingredientViewModel.getIng());
                ingListView.setAdapter(arrayAdapter);
                ingListView.setOnItemClickListener(onClickItemList());
//            Handling Buttons and actions for them
                Button addButton = findViewById(R.id.addButton);
                addButton.setOnClickListener(v -> addIngAction(v, autoCompleteTextView, editTextWeight, sumText));

                Button removeButton = findViewById(R.id.removeButton);
                removeButton.setOnClickListener(v -> removeIngButtonAction(v, arrayAdapter, sumText));

                Button settingButton = findViewById(R.id.settingButton);
                settingButton.setOnClickListener(this::settingButtonAction);
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

    public void addIngAction(View view, AutoCompleteTextView autoCompleteTextView, EditText weightText, TextView sumView){
//        checking if data exists in a lookup table, without it no calculation can be done
        String ingName = String.valueOf(autoCompleteTextView.getText());

        if (ingredientViewModel.validateExistance(ingName)) {
//            adding data to the list
//            validating on a xml level
            int weightTemp = 100;
//            still validating to be sure, if string is not integer add default value of a 100
            try {
                weightTemp = Integer.parseInt(String.valueOf(weightText.getText()));
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Wprowadz liczbę", Toast.LENGTH_LONG).show();
            }

//            creating ingredient
            Ingredient ingTemp = new Ingredient(ingName, weightTemp);
            ingredientViewModel.addIngredients(ingTemp);
//            calculating uric acid
            int sum = ingredientViewModel.calculateUricAcid();
//            showing calculations
            sumView.setText(String.valueOf(sum));
//            clearing texts on a screen (comfort of using the app increased)
            autoCompleteTextView.setText("");
            weightText.setText("");
//            setting position to be the last element (the last added element will be a default do delete)
            position = ingredientViewModel.ingredientsSize() - 1;
        }
    }

    public void removeIngButtonAction(View view, ArrayAdapter<Ingredient> adapter, TextView textView) {
//        checking if item on a list was selected (there is no situation when this happens but for safety is here)
        if (position != AdapterView.INVALID_POSITION) {
//            removing items from a list
            ingredientViewModel.removeIngredient(position);
            adapter.notifyDataSetChanged();
//            recalculating uric acid
            int sum = ingredientViewModel.calculateUricAcid();
//            displaying new uric acid
            textView.setText(String.valueOf(sum));
//            selecting new position on a listView if a last element was deleted, if not, selected position remains
            if (position == ingredientViewModel.getIng().size()){
                position --;
            }
        }
    }

    private void settingButtonAction(View v) {
        Intent myIntent = new Intent(this, SettingsActivity.class);
        myIntent.putExtra("key", ingredientViewModel.getDataRepository().getPurinesLookUpTable());
        this.startActivity(myIntent);
    }

    public AdapterView.OnItemClickListener onClickItemList() {
//        creating listener so that list knows what position on a list was selected
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainApp.this.position = position;
            }
        };
    }
}
