package com.kamjer.purinescalculator.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;

import com.kamjer.purinescalculator.R;
import com.kamjer.purinescalculator.model.Ingredient;

import java.util.HashMap;

public class SettingsActivity extends Activity {

    @Override
    protected void onStart() {
        super.onStart();
        setContentView(R.layout.purines_look_up_table_list);

//        ListView purinesLookUpTableListView = findViewById(R.id.listViewAllIng);
//        String[] autocompleteData = new String[MainApp.purinesLookUpTable.size()];
//        MainApp.purinesLookUpTable.keySet().toArray(autocompleteData);
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.ing_list_view, R.id.textViewItem, autocompleteData);
//        purinesLookUpTableListView.setAdapter(arrayAdapter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
