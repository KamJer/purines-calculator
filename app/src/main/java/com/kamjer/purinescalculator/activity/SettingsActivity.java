package com.kamjer.purinescalculator.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kamjer.purinescalculator.R;
import com.kamjer.purinescalculator.adapters.allingredientsrecyclierviewadapter.AllIngredientsListAdapter;
import com.kamjer.purinescalculator.adapters.ingredientsrecyclierviewadapter.IngredientsListAdapter;
import com.kamjer.purinescalculator.datarepository.DataRepository;
import com.kamjer.purinescalculator.model.Ingredient;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SettingsActivity extends Activity {

    @Override
    protected void onStart() {
        super.onStart();
        setContentView(R.layout.purines_look_up_table_list);

        try {
            DataRepository dataRepository = new DataRepository(this);

            RecyclerView recyclerView = findViewById(R.id.allIngredientRecyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            AllIngredientsListAdapter ingredientsListAdapter = new AllIngredientsListAdapter(this, dataRepository.getPurinesLookUpTable());
            recyclerView.setAdapter(ingredientsListAdapter);

        } catch (XmlPullParserException | IOException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
