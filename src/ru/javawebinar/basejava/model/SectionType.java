package ru.javawebinar.basejava.model;

public enum  SectionType {

    PERSONAL("Personal qualities"),
    OBJECTIVE("Position"),
    ACHIEVEMENT("Achievements"),
    QUALIFICATIONS("Qualification"),
    EXPERIENCE("Experience"),
    EDUCATION("Education");

    private String title;

    SectionType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
