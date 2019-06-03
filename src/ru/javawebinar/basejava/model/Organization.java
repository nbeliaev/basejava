package ru.javawebinar.basejava.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Organization {

    private Link link;
    private List<Position> positions = new ArrayList<>();

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

    public Organization(String name, String url) {
        this(new Link(name, url));
    }

    private Organization(Link link, LocalDate beginDate, LocalDate endDate, String description) {
        Objects.requireNonNull(link, "name is required");
        Objects.requireNonNull(beginDate, "beginDate is required");
        Objects.requireNonNull(endDate, "endDate is required");
        this.link = link;
        addPosition(beginDate, endDate, description);
    }

    private Organization(Link link, LocalDate beginDate, String description) {
        Objects.requireNonNull(link, "name is required");
        Objects.requireNonNull(beginDate, "beginDate is required");
        this.link = link;
        addPosition(beginDate, description);
    }

    private Organization(Link link) {
        Objects.requireNonNull(link, "name is required");
        this.link = link;
    }

    public void addPosition(LocalDate beginDate, LocalDate endDate, String description) {
        positions.add(new Position(beginDate, endDate, description));
    }

    public void addPosition(LocalDate beginDate, String description) {
        positions.add(new Position(beginDate, description));
    }

    public List<Position> getPositions() {
        return positions;
    }

    private class Position {

        private LocalDate beginDate;
        private LocalDate endDate;
        private String description;

        Position(LocalDate beginDate, LocalDate endDate, String description) {
            Objects.requireNonNull(beginDate, "beginDate is required");
            Objects.requireNonNull(endDate, "endDate is required");
            this.beginDate = beginDate;
            this.endDate = endDate;
            this.description = description;
        }

        Position(LocalDate beginDate, String description) {
            Objects.requireNonNull(beginDate, "beginDate is required");
            this.beginDate = beginDate;
            this.description = description;
        }

        @Override
        public String toString() {
            return "Position{" +
                    "beginDate=" + beginDate +
                    ", endDate=" + endDate +
                    ", description='" + description + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Organization{" +
                "link=" + link +
                ", positions=" + positions +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (!link.equals(that.link)) return false;
        return positions.equals(that.positions);

    }

    @Override
    public int hashCode() {
        int result = link.hashCode();
        result = 31 * result + positions.hashCode();
        return result;
    }
}
