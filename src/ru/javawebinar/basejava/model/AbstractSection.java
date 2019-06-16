package ru.javawebinar.basejava.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public abstract class AbstractSection<T> implements Serializable {

    private T content;

    AbstractSection(T content) {
        Objects.requireNonNull(content, "content is required");
        this.content = content;
    }

    protected AbstractSection() {
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
