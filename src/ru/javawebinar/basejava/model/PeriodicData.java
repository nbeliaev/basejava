package ru.javawebinar.basejava.model;

import java.time.LocalDate;
import java.util.Objects;

class PeriodicData {

    private Link link;
    private LocalDate beginDate;
    private LocalDate endDate;
    private String description;

    PeriodicData(Link link, LocalDate beginDate, LocalDate endDate, String description) {
        Objects.requireNonNull(link, "name is required");
        Objects.requireNonNull(beginDate, "beginDate is required");
        Objects.requireNonNull(endDate, "endDate is required");
        this.link = link;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.description = description;
    }

    PeriodicData(Link link, LocalDate beginDate, String description) {
        this.link = link;
        this.beginDate = beginDate;
        this.description = description;
    }

    PeriodicData(String name, String url, LocalDate beginDate, LocalDate endDate, String description) {
        this(new Link(name, url), beginDate, endDate, description);
    }

    PeriodicData(String name, LocalDate beginDate, LocalDate endDate, String description) {
        this(new Link(name), beginDate, endDate, description);
    }

    PeriodicData(String name, String url, LocalDate beginDate, String description) {
        this(new Link(name, url), beginDate, description);
    }

    PeriodicData(String name, LocalDate beginDate, String description) {
        this(new Link(name), beginDate, description);
    }

    @Override
    public String toString() {
        return "PeriodicData{" +
                "link=" + link +
                ", beginDate=" + beginDate +
                ", endDate=" + endDate +
                ", description='" + description + '\'' +
                '}';
    }
}
