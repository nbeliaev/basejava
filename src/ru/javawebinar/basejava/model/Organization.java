package ru.javawebinar.basejava.model;

import ru.javawebinar.basejava.util.DateUtil;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Organization implements Serializable {

    private Link link;
    private List<Position> positions;

    public Organization(String name, String url, Position... positions) {
        this.link = new Link(name, url);
        this.positions = Arrays.asList(positions);
    }

    public Organization(String name, Position... positions) {
        this.link = new Link(name);
        this.positions = Arrays.asList(positions);
    }

    public List<Position> getPositions() {
        return positions;
    }

    public static class Position implements Serializable {

        private LocalDate beginDate;
        private LocalDate endDate;
        private String description;

        public Position(LocalDate beginDate, LocalDate endDate, String description) {
            Objects.requireNonNull(beginDate, "beginDate is required");
            Objects.requireNonNull(endDate, "endDate is required");
            this.beginDate = beginDate;
            this.endDate = endDate;
            this.description = description;
        }

        public Position(LocalDate beginDate, String description) {
            Objects.requireNonNull(beginDate, "beginDate is required");
            this.beginDate = beginDate;
            this.endDate = DateUtil.NOW;
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
