package com.tech.challenge.tech_challenge.core.domain.services.generic;
import org.springframework.stereotype.Component;
import java.lang.reflect.Field;

@Component
public class Patcher<T>{
    public T execute(T existingObject, T incompleteObject) throws IllegalAccessException {

        //GET THE COMPILED VERSION OF THE CLASS
        Class<?> productClass= existingObject.getClass();
        Field[] productFields=productClass.getDeclaredFields();
        System.out.println(productFields.length);
        for(Field field : productFields){
            System.out.println(field.getName());
            //CANT ACCESS IF THE FIELD IS PRIVATE
            field.setAccessible(true);

            if(field.getName().equals("id")) continue;

            //CHECK IF THE VALUE OF THE FIELD IS NOT NULL, IF NOT UPDATE EXISTING INTERN
            Object value=field.get(incompleteObject);
            if(value!=null){
                field.set(existingObject,value);
            }
            //MAKE THE FIELD PRIVATE AGAIN
            field.setAccessible(false);
        }

        return existingObject;
    }

}