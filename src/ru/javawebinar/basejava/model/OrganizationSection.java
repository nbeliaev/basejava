package ru.javawebinar.basejava.model;

import java.util.List;

public class OrganizationSection extends AbstractSection<List<Organization>> {
    private static final long serialVersionUID = 1L;

    public OrganizationSection(List<Organization> content) {
        super(content);
    }

    @Override
    public String toString() {
        return super.getContent().toString();
    }
}
