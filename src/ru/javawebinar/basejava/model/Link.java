package ru.javawebinar.basejava.model;

import java.util.Objects;

public class Link {

    private String name;
    private String url;

    public Link(String name) {
        Objects.requireNonNull(name, "name is required");
        this.name = name;
    }

    public Link(String name, String url) {
        this(name);
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Link link = (Link) o;

        if (!name.equals(link.name)) return false;
        return Objects.equals(url, link.url);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (url != null ? url.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Link{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
