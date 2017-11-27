package com.open_source.worldwide.baking.recipe_details.models;

import java.util.List;

public class Recipe {

    private Integer id;
    private String name;
    private List<Ingredient> ingredients = null;
    private List<Step> steps = null;
    private Integer servings;
    private String image;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }


    public Integer getServings() {
        return servings;
    }

    public String getImage() {
        return image;
    }

}