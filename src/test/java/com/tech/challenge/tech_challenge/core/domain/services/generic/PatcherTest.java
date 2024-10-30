package com.tech.challenge.tech_challenge.core.domain.services.generic;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PatcherTest {

    @Autowired
    private Patcher<PatchedObject> patcher;

    @Test
    public void executeTest() throws IllegalAccessException {
        PatchedObject patchedObjectExistent = new PatchedObject(10, "Dez", 11.0);
        PatchedObject patchedObjectIncomplete = new PatchedObject(null, null, 10.0);

        PatchedObject result = patcher.execute(patchedObjectExistent, patchedObjectIncomplete);

        assertEquals(result.doubleField, 10.0);
        assertEquals(result.intField, 10);
        assertEquals(result.stringField, "Dez");

        patchedObjectIncomplete = new PatchedObject(11, null, 11.0);
        result = patcher.execute(result, patchedObjectIncomplete);

        assertEquals(result.doubleField, 11.0);
        assertEquals(result.intField, 11);
        assertEquals(result.stringField, "Dez");

        patchedObjectIncomplete = new PatchedObject(null, "Onze", null);
        result = patcher.execute(result, patchedObjectIncomplete);

        assertEquals(result.doubleField, 11.0);
        assertEquals(result.intField, 11);
        assertEquals(result.stringField, "Onze");
    }

    private static class PatchedObject{
        protected Integer intField;
        protected String stringField;
        protected Double doubleField;

        protected PatchedObject(Integer intField, String stringField, Double doubleField){
            this.intField = intField;
            this.stringField = stringField;
            this.doubleField = doubleField;
        }
    }
}
