package org.example.complexpojo;

public class Info {
    private String name;
    private String description;
    private String schema;

    public Info(String name, String description, String schema) {
        this.name = name;
        this.description = description;
        this.schema = schema;
    }

    public Info() {
        // Default constructor
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }
}
