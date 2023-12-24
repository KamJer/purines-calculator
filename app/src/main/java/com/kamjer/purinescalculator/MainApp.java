package com.kamjer.purinescalculator;

import android.app.Activity;
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

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainApp extends Activity {

    private final HashMap<String, Integer> purinesLookUpTable = new HashMap<>();
    private final List<Ingredient> ing = new ArrayList<>();
//    private final List<String> ing = new ArrayList<>();
//    private final List<Integer> weight = new ArrayList<>();

    private int position = 0;

    @Override
    protected void onStart() {
        super.onStart();
        setContentView(R.layout.main_activity_layout);
        try {
            try {
                loadPurinLookUpTable(purinesLookUpTable);

//            creating array for an adapter (it can not take as parameter hashMap)
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
                ArrayAdapter<Ingredient> arrayAdapter = new ArrayAdapter<>(this, R.layout.ing_list_view, R.id.textView, ing);
                ingListView.setAdapter(arrayAdapter);
                ingListView.setOnItemClickListener(onClickItemList());
//            Handling Buttons and actions for them
                Button addButton = findViewById(R.id.addButton);
                addButton.setOnClickListener(v -> addIngAction(v, autoCompleteTextView, editTextWeight, sumText, ing));

                Button removeButton = findViewById(R.id.removeButton);
                removeButton.setOnClickListener(v -> removeIngButtonAction(v, arrayAdapter, ingListView, sumText, ing));
            } catch (Resources.NotFoundException | XmlPullParserException | IOException e) {
//                if there is an issue show it to the user, if lookup table does not load there is no point i app working, it relies on it working
                Toast.makeText(this, "Nie można załadować tabeli, problem danymi", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
//            if there is an issue show it to the user
            Toast.makeText(this, "error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void loadPurinLookUpTable(HashMap<String, Integer> hashMap) throws Resources.NotFoundException, XmlPullParserException, IOException{
//        loading lookup table from xml file
        XmlResourceParser xpp = getResources().getXml(R.xml.purin_look_up_table);
//        looping thru the file
        while (xpp.getEventType()!=XmlPullParser.END_DOCUMENT) {
//            looking on a tags on a file
            if (xpp.getEventType()==XmlResourceParser.START_TAG) {
                if (xpp.getName().equals(getResources().getString(R.string.product))) {
//                    loading data if tag matches
                    hashMap.put(xpp.getAttributeValue(1), Integer.valueOf(xpp.getAttributeValue(2)));
                }
            }
            xpp.next();
        }
    }

    public int calculateUricAcid(List<Ingredient> ingredients) {
//        creating fields
        int uricAcid = 0;
//        looping thru list of ingredients
        for (int i = 0; i < ingredients.size(); i++) {
//            checking if ingredient and weight is null for what ever reason so that nullPointer does not happen
            if (ingredients.get(i) != null) {
//                checking if data exists in a lookup table so that nullPointer does not happen
                if (purinesLookUpTable.get(ingredients.get(i).getName()) != null) {
//                    calculating uric acid
                    uricAcid += (int) (Double.valueOf(purinesLookUpTable.get(ingredients.get(i).getName())) / 100 * ingredients.get(i).getWeight());
                }
            }
        }
        return uricAcid;
    }

    public void addIngAction(View view, AutoCompleteTextView autoCompleteTextView, EditText weightText, TextView textView, List<Ingredient> ing ){
//        checking if data exists in a lookup table, without it no calculation can be done
        if (purinesLookUpTable.get(String.valueOf(autoCompleteTextView.getText())) != null) {
//            adding data to the list
            String ingName = String.valueOf(autoCompleteTextView.getText());
//            validating on a xml level
            int weightTemp = 100;
//            still validating to be sure, if string is not integer add default value of a 100
            try {
                weightTemp = Integer.parseInt(String.valueOf(weightText.getText()));
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Wprowadz liczbę", Toast.LENGTH_LONG).show();
            }

            Ingredient ingTemp = new Ingredient(String.valueOf(autoCompleteTextView.getText()), weightTemp);
            ing.add(ingTemp);
//            calculating uric acid
            int sum = calculateUricAcid(ing);
//            showing calculations
            textView.setText(String.valueOf(sum));
//            clearing texts on a screen (comfort of using the app increased)
            autoCompleteTextView.setText("");
            weightText.setText("");
//            setting position to be the last element (the last added element will be a default do delete)
            position = ing.size() - 1;
        }
    }

    public void removeIngButtonAction(View view, ArrayAdapter<Ingredient> adapter, ListView ingListView, TextView textView, List<Ingredient> ing) {
//        checking if item on a list was selected
        if (position != AdapterView.INVALID_POSITION) {
//            removing items from a lists
            adapter.remove(ing.get(position));
//            recalculating uric acid
            int sum = calculateUricAcid(ing);
//            displaying new uric acid
            textView.setText(String.valueOf(sum));
//            selecting new position on a list if a last element was deleted, if not, selected position remains
            if (position == ing.size()){
                position --;
            }
        }
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
