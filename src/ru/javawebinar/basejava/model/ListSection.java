package ru.javawebinar.basejava.model;

import java.util.Arrays;
import java.util.List;

public class ListSection extends AbstractSection<List<String>> {

    public ListSection(String... content) {
        super(Arrays.asList(content));
    }
}
