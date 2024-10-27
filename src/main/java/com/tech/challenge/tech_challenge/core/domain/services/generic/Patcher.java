package com.tech.challenge.tech_challenge.core.domain.services.generic;
import org.springframework.stereotype.Component;
import java.lang.reflect.Field;

@Component
public class Patcher<T>{
    public T execute(T existingObject, T incompleteObject) throws IllegalAccessException {

        Class<?> productClass = existingObject.getClass();
        Field[] productFields = productClass.getDeclaredFields();

        for(Field field : productFields){
            field.setAccessible(true);

            if(field.getName().equals("id")) continue;

            Object value = field.get(incompleteObject);

            if(value != null){
                field.set(existingObject,value);
            }

            field.setAccessible(false);
        }

        return existingObject;
    }

}