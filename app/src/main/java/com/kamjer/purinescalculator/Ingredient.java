package com.kamjer.purinescalculator;

import androidx.annotation.NonNull;

public class Ingredient {
    private String name;
    private int weight;
    private int uricAcidContent;

    public Ingredient(String name, int weight) {
        this.name = name;
        this.weight = weight;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getUricAcidContent() {
        return uricAcidContent;
    }

    public void setUricAcidContent(int uricAcidContent) {
        this.uricAcidContent = uricAcidContent;
    }
}
