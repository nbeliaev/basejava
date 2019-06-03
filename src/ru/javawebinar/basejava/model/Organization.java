package ru.javawebinar.basejava.model;

import java.time.LocalDate;
import java.util.Objects;

public class Organization {

    private Link link;
    private LocalDate beginDate;
    private LocalDate endDate;
    private String description;

    public Organization(Link link, LocalDate beginDate, LocalDate endDate, String description) {
        Objects.requireNonNull(link, "name is required");
        Objects.requireNonNull(beginDate, "beginDate is required");
        Objects.requireNonNull(endDate, "endDate is required");
        this.link = link;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.description = description;
    }

    public Organization(Link link, LocalDate beginDate, String description) {
        Objects.requireNonNull(link, "name is required");
        Objects.requireNonNull(beginDate, "beginDate is required");
        this.link = link;
        this.beginDate = beginDate;
        this.description = description;
    }

    public Organization(String name, String url, LocalDate beginDate, LocalDate endDate, String description) {
        this(new Link(name, url), beginDate, endDate, description);
    }

    public Organization(String name, LocalDate beginDate, LocalDate endDate, String description) {
        this(new Link(name), beginDate, endDate, description);
    }

    public Organization(String name, String url, LocalDate beginDate, String description) {
        this(new Link(name, url), beginDate, description);
    }

    public Organization(String name, LocalDate beginDate, String description) {
        this(new Link(name), beginDate, description);
    }

    @Override
    public String toString() {
        return "Organization{" +
                "link=" + link +
                ", beginDate=" + beginDate +
                ", endDate=" + endDate +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (!link.equals(that.link)) return false;
        if (!beginDate.equals(that.beginDate)) return false;
        if (!Objects.equals(endDate, that.endDate)) return false;
        return Objects.equals(description, that.description);

    }

    @Override
    public int hashCode() {
        int result = link.hashCode();
        result = 31 * result + beginDate.hashCode();
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
