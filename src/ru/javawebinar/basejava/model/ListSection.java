package ru.javawebinar.basejava.model;

import java.util.Arrays;
import java.util.List;

public class ListSection extends AbstractSection<List<String>> {
    private static final long serialVersionUID = 1L;

    public ListSection(String... content) {
        super(Arrays.asList(content));
    }
}
