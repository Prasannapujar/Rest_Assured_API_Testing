package org.example.complexpojo;

public class Body {
    private String mode;
    private String raw;

    public Body() {
        // Default constructor
    }

    public Body(String mode, String raw) {
        this.mode = mode;
        this.raw = raw;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getRaw() {
        return raw;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }
}