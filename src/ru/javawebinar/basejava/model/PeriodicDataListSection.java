package ru.javawebinar.basejava.model;

import java.util.List;

class PeriodicDataListSection extends AbstractSection<List<PeriodicData>> {

    PeriodicDataListSection(List<PeriodicData> content) {
        super(content);
    }

    @Override
    public String toString() {
        return super.getContent().toString();
    }
}
