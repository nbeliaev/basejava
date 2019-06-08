package ru.javawebinar.basejava.model;

import java.io.Serializable;
import java.util.Objects;

public abstract class AbstractSection<T> implements Serializable {

    private T content;

    AbstractSection(T content) {
        Objects.requireNonNull(content, "content is required");
        this.content = content;
    }

    T getContent() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractSection<?> section = (AbstractSection<?>) o;

        return content.equals(section.content);

    }

    @Override
    public int hashCode() {
        return content.hashCode();
    }
}
