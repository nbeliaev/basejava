package ru.javawebinar.basejava.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Organization {

    private Link link;
    private List<Position> positions = new ArrayList<>();

    public Organization(String name, String url) {
        this.link = new Link(name, url);
    }

    public Organization(String name) {
        this.link = new Link(name);
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

        public LocalDate getBeginDate() {
            return beginDate;
        }

        public LocalDate getEndDate() {
            return endDate;
        }

        public String getDescription() {
            return description;
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
