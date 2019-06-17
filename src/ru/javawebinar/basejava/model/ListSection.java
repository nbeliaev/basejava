package ru.javawebinar.basejava.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class ListSection extends AbstractSection<List<String>> {
    private static final long serialVersionUID = 1L;

    public ListSection(String... content) {
        super(new ArrayList<>(Arrays.asList(content)));
    }

    public ListSection() {
    }
}
