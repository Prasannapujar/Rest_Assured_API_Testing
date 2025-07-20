package org.restassured.test;

import  io.restassured.path.json.JsonPath;
import java.util.*;

public class RestAssuredJsonExtractor {

    public static void main(String[] args) {
        String json = """
            [
              {
                "k": [1, 3, 5],
                "k1": {
                  "k10": 4,
                  "k11": [4, 7, 9],
                  "k12": {
                    "k120": {
                      "k121": "v121"
                    }
                  },
                  "k14": 6
                }
              },
              {
                "k22": {
                  "k221": "v122"
                }
              }
            ]
            """;

        JsonPath jsonPath = new JsonPath(json);
        Set<String> keys = new LinkedHashSet<>();
        List<Object> values = new ArrayList<>();

        extractKeysAndValuesRecursive(jsonPath, keys, values);

        // Print in the required format
        System.out.println("keys = " + keys);
        System.out.println("values = " + values);
    }

    public static void extractKeysAndValuesRecursive(JsonPath jsonPath, Set<String> keys, List<Object> values) {
        // Extract from first object
        extractFromObject(jsonPath, "[0]", keys, values);
        // Extract from second object
        extractFromObject(jsonPath, "[1]", keys, values);
    }

    private static void extractFromObject(JsonPath jsonPath, String objectPath, Set<String> keys, List<Object> values) {
        try {
            Object object = jsonPath.get(objectPath);
            System.out.println(Object.class.getName() + " = " + object);
            if (object instanceof Map) {
                Map<String, Object> map = (Map<String, Object>) object;

                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();

                    keys.add(key);

                    if (value instanceof Map) {
                        // Nested object - recurse
                        extractFromNestedObject((Map<String, Object>) value, keys, values);
                    } else if (value instanceof List) {
                        // Array - add as one value
                        values.add(value);
                    } else {
                        // Primitive value
                        values.add(value);
                    }
                }
            }
        } catch (Exception e) {
            // Object doesn't exist
        }
    }

    private static void extractFromNestedObject(Map<String, Object> map, Set<String> keys, List<Object> values) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            keys.add(key);

            if (value instanceof Map) {
                // Further nested object
                extractFromNestedObject((Map<String, Object>) value, keys, values);
            } else if (value instanceof List) {
                // Array
                values.add(value);
            } else {
                // Primitive value
                values.add(value);
            }
        }
    }
}