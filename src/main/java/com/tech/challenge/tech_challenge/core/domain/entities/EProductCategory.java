package com.tech.challenge.tech_challenge.core.domain.entities;

import com.tech.challenge.tech_challenge.core.application.exceptions.UnableToParseProductCategoryException;
import lombok.Getter;

@Getter
public enum EProductCategory {
    SNACK("snack"),
    CUSTOM_SNACK("custom_snack"),
    SIDE_DISH("side_dish"),
    DRINK("drink"),
    DESSERT("dessert");

    private String value;

    EProductCategory(String value) {
        this.value = value;
    }

    public EProductCategory fromValue(String value){
        for(EProductCategory category : EProductCategory.values()){
            if(category.value.equals(value)) return category;
        }

        throw new UnableToParseProductCategoryException(value);
    }
}
