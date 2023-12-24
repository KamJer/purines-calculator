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

    private final HashMap<String, Integer> purinLookUpTable = new HashMap<>();
    private final List<String> ing = new ArrayList<>();
    private final List<Integer> weight = new ArrayList<>();

    private int position = 0;

    @Override
    protected void onStart() {
        super.onStart();
        setContentView(R.layout.main_activity_layout);
        try {
            try {
                loadPurinLookUpTable(purinLookUpTable);
            } catch (Resources.NotFoundException | XmlPullParserException | IOException e) {
                Toast.makeText(this, "Request failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                Log.d("tester", e.getMessage());
            }

            String[] autocompleteData = new String[purinLookUpTable.size()];
            purinLookUpTable.keySet().toArray(autocompleteData);
//            Handling autoCompleteText
            AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.editTextIngredient);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.select_dialog_item, autocompleteData);
            autoCompleteTextView.setAdapter(adapter);

            EditText editTextWeight = (EditText) findViewById(R.id.editTextWeight);

            TextView sumText = (TextView) findViewById(R.id.textSum);

//            Handling list
            ListView ingListView = (ListView) findViewById(R.id.listViewIng);
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.ing_list_view, R.id.textView, ing);
            ingListView.setAdapter(arrayAdapter);
            ingListView.setOnItemClickListener(onClickItemList());
//            Handling Button
            Button addButton = (Button) findViewById(R.id.addButton);
            addButton.setOnClickListener(v -> addIngAction(v, autoCompleteTextView, editTextWeight, sumText, ing, weight));

            Button removeButton = (Button) findViewById(R.id.removeButton);
            removeButton.setOnClickListener(v -> removeIngButtonAction(v,arrayAdapter, ingListView, sumText, ing, weight));
        } catch (Exception e) {
            Toast.makeText(this, "error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            Log.d("tester", e.getMessage());
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
//                    loading data if tag match
                    hashMap.put(xpp.getAttributeValue(1), Integer.valueOf(xpp.getAttributeValue(2)));
                }
            }
            xpp.next();
        }
    }

    public int calculateUricAcid(List<String> ingredients, List<Integer> wight) {
//        creating fields
        int uricAcid = 0;
//        looping thru list of ingredients
        for (int i = 0; i < ingredients.size(); i++) {
//            checking if ingredient and weight is null for what ever reason so that nullPointer does not happen
            if (ingredients.get(i) != null & wight.get(i) != null) {
//                checking if data exists in a lookup table so that nullPointer does not happen
                if (purinLookUpTable.get(ingredients.get(i)) != null) {
//                    calculating uric acid
                    uricAcid += (int) (Double.valueOf(purinLookUpTable.get(ingredients.get(i))) / 100 * wight.get(i));
                }
            }
        }
        return uricAcid;
    }

    public void addIngAction(View view, AutoCompleteTextView autoCompleteTextView, EditText weightText, TextView textView, List<String> ing, List<Integer> weight ){
//        checking if data exists in a lookup table, without it no calculation can be done
        if (purinLookUpTable.get(String.valueOf(autoCompleteTextView.getText())) != null) {
//            adding data to the list
            ing.add(String.valueOf(autoCompleteTextView.getText()));
//            validating on a xml level
            int weightTemp = 100;
//            still validating to be sure, if string is not integer add default value of a 100
            try {
                weight.add(Integer.parseInt(String.valueOf(weightText.getText())));
            } catch (NumberFormatException e) {
                weight.add(weightTemp);
            }
//            calculating uric acid
            int sum = calculateUricAcid(ing, weight);
//            showing calculations
            textView.setText(String.valueOf(sum));
//            clearing texts on a screen (comfort of using the app increased)
            autoCompleteTextView.setText("");
            weightText.setText("");
//            setting position to be the last element (the last added element will be a default do delete)
            position = ing.size() - 1;
        }
    }

    public void removeIngButtonAction(View view, ArrayAdapter<String> adapter, ListView ingListView, TextView textView, List<String> ing, List<Integer> weight) {
//        checking if item on a list was selected
        if (position != AdapterView.INVALID_POSITION) {
//            removing items from a lists
            adapter.remove(ing.get(position));
            weight.remove(position);
//            recalculating uric acid
            int sum = calculateUricAcid(ing, weight);
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
