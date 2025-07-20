package org.restassured.test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.*;

public class JsonKeyValueExtractor {

    public static void main(String[] args) {
        String json = """
                [
                  {
                    "k":[
                      1,
                      3,
                      5
                    ],
                    "k1":{
                      "k10":4,
                      "k11":[
                        4,
                        7,
                        9
                      ],
                      "k12":{
                        "k120":{
                          "k121":"v121"
                        }
                      },
                      "k14":6
                    }
                  },
                  {
                    "k22":{
                      "k221":"v122"
                    }
                  }
                ]
            """;

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(json); // converted to Json node object
            System.out.println(rootNode);

            Set<String> keys = new LinkedHashSet<>();
            List<Object> values = new ArrayList<>();

            extractKeysAndValues(rootNode, keys, values);

            // Print in the required format
            System.out.println("keys = " + keys);
            System.out.println("values = " + values);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void extractKeysAndValues(JsonNode node, Set<String> keys, List<Object> values) {
        if (node.isObject()) {
            // Handle JSON object
            Iterator<Map.Entry<String, JsonNode>> fields = node.fields(); // created the MAP entry of the filed

            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> entry = fields.next();
                System.out.println(" Entry is "+entry);
                String key = entry.getKey();
                System.out.println("Key is "+key);
                JsonNode value = entry.getValue();
                System.out.println("Value is "+value);

                keys.add(key);

                if (value.isValueNode()) { // check if this is value node
                    // Leaf node - add value
                    System.out.println(value + " is a Value node ");
                    values.add(getValueFromNode(value));
                } else if (value.isArray()) {  // if this is Array
                    // Array - add the entire array as one value
                    System.out.println(value + " is a Array ");
                    values.add(convertArrayToList(value));
                    // Recurse into array elements if they are objects
                    for (JsonNode arrayElement : value) {
                        if (arrayElement.isObject()) {  // if this is object so there are only 3 possiblities value node, Array node or object node =>  key:[], => key: {}
                            System.out.println(arrayElement + " is the object ");
                            extractKeysAndValues(arrayElement, keys, values);
                        }else
                        {
                            System.out.println(arrayElement + " is not an object");
                        }
                    }
                } else {
                    // Nested object - recurse
                    System.out.println(value + "Need to be recurrsively test");
                    extractKeysAndValues(value, keys, values);
                }
            }
        } else if (node.isArray()) {
            System.out.println("It's an array");
            // Handle JSON array at root level
            for (JsonNode arrayElement : node) {
                System.out.println(" The nod is "+ arrayElement);
                if (arrayElement.isObject()) {
                    extractKeysAndValues(arrayElement, keys, values);
                }
            }
        }
    }

    private static List<Object> convertArrayToList(JsonNode arrayNode) {
        List<Object> list = new ArrayList<>();
        for (JsonNode element : arrayNode) {
            list.add(getValueFromNode(element));
        }
        return list;
    }

    private static Object getValueFromNode(JsonNode node) {
        if (node.isTextual()) return node.asText(); // check if text and get text
        if (node.isInt()) return node.asInt(); // check for int
        if (node.isLong()) return node.asLong(); // long
        if (node.isDouble()) return node.asDouble(); // Double
        if (node.isBoolean()) return node.asBoolean(); // Boolean
        if (node.isNull()) return null; // if null
        return node.asText(); // fallback  // else text
    }
}