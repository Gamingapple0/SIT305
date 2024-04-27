package com.example.chatbot;

public class Chat {
    String value ;
    boolean isIcon;

    public void setValue(String value) {
        this.value = value;
    }

    public void setIcon(boolean icon) {
        isIcon = icon;
    }

    public Chat(String value, boolean isIcon) {
        this.value = value;
        this.isIcon = isIcon;
    }

    public String getValue() {
        return value;
    }

    public boolean isIcon() {
        return isIcon;
    }
}
