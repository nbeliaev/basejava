package ru.javawebinar.basejava.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class OrganizationSection extends AbstractSection<List<Organization>> {
    private static final long serialVersionUID = 1L;

    public OrganizationSection(List<Organization> content) {
        super(content);
    }

    public OrganizationSection() {
    }

    @Override
    public String toString() {
        return super.getContent().toString();
    }
}
