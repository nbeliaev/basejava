package ru.javawebinar.basejava.model;

import java.util.List;

public class OrganizationSection extends AbstractSection<List<Organization>> {

    public OrganizationSection(List<Organization> content) {
        super(content);
    }

    @Override
    public String toString() {
        return super.getContent().toString();
    }
}
