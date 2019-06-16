package ru.javawebinar.basejava.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class SimpleSection extends AbstractSection<String> {
    private static final long serialVersionUID = 1L;

    public SimpleSection(String content) {
        super(content);
    }

    public SimpleSection() {
    }
}
