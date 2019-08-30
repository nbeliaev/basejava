package ru.javawebinar.basejava.model;

public enum  ContactType {
    MOBILE_PHONE("Phone"),
    SKYPE("Skype"),
    EMAIL("E-mail"),
    LINKEDIN("LinkedIn"),
    GITHUB("GitHub"),
    STACKOVERFLOW("Stackoverflow"),
    WEBPAGE("Webpage");

    private String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
