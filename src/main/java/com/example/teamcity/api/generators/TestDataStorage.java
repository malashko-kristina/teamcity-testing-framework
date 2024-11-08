package com.example.teamcity.api.generators;

import com.example.teamcity.api.enums.Endpoint;
import com.example.teamcity.api.models.BaseModel;
import com.example.teamcity.api.requests.unchecked.UncheckedBase;
import com.example.teamcity.api.spec.Specifications;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class TestDataStorage { // Decorator
    private static TestDataStorage testDataStorage;
    private final EnumMap<Endpoint, Set<String>> createdEntitiesMap;

    private TestDataStorage () {
        createdEntitiesMap = new EnumMap<>(Endpoint.class);
    };

    public static TestDataStorage getStorage() {
        if (testDataStorage == null) {
            testDataStorage = new TestDataStorage();
        }
        return testDataStorage;
    }

    private void addCreatedEntity(Endpoint endpoint, String id) {
        if (id != null) {
            createdEntitiesMap.computeIfAbsent(endpoint, key -> new HashSet<>()).add(id);

        }
    }

    private String getEntityIdOrLocator(BaseModel model) {
        try {
            var idField = model.getClass().getDeclaredField("id");
            idField.setAccessible(true);
            var idFieldValue = Objects.toString(idField.get(model), null);
            idField.setAccessible(false);
            return idFieldValue;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            try {
                var locatorField = model.getClass().getDeclaredField("locator");
                locatorField.setAccessible(true);
                var locatorFieldValue = Objects.toString(locatorField.get(model), null);
                locatorField.setAccessible(false);
                return locatorFieldValue;
            } catch (NoSuchFieldException | IllegalAccessException ex) {
                throw new IllegalStateException("Cannot get id or locator of entity", e);
            }
        }
    }

    public void addCreatedEntity(Endpoint endpoint, BaseModel model) {
        addCreatedEntity(endpoint, getEntityIdOrLocator(model));

    }

    public void deleteCreatedEntities() {
        createdEntitiesMap.forEach((((endpoint, ids) ->
                ids.forEach(id ->
                        new UncheckedBase(Specifications.superUserSpec(), endpoint).delete(id)
                )
                )
                )
        );
        createdEntitiesMap.clear(); // так как запускаем этот метод после каждого теста
    }
}
