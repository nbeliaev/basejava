package ru.javawebinar.basejava.storage.Serialization;

import ru.javawebinar.basejava.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class DataStreamSerialization implements SerializationStrategy {

    @Override
    public void doWrite(OutputStream outputStream, Resume resume) throws IOException {
        try (DataOutputStream out = new DataOutputStream(outputStream)) {
            out.writeUTF(resume.getUuid());
            out.writeUTF(resume.getFullName());
            writeContacts(out, resume);
            writeSections(out, resume);
        }
    }

    @Override
    public Resume doRead(InputStream inputStream) throws IOException {
        try (final DataInputStream input = new DataInputStream(inputStream)) {
            Resume resume = new Resume(readLine(input), readLine(input));
            readContacts(input, resume);
            readSections(input, resume);
            return resume;
        }
    }

    @FunctionalInterface
    private interface ActionOnWrite<T> {
        void writeItem(T item) throws IOException;
    }

    private <T> void writeCollection(Collection<T> collection, DataOutputStream out, ActionOnWrite<T> action) throws IOException {
        out.writeByte(collection.size());
        for (T item : collection) {
            action.writeItem(item);
        }
    }

    private void writeLine(DataOutputStream out, String str) throws IOException {
        out.writeUTF(str == null ? "" : str);
    }

    private void writeContacts(DataOutputStream out, Resume resume) throws IOException {
        final Map<ContactType, String> contacts = resume.getContacts();
        writeCollection(contacts.entrySet(), out, (item -> {
            writeLine(out, item.getKey().name());
            writeLine(out, item.getValue());
        }));
    }

    private void writeSections(DataOutputStream out, Resume resume) throws IOException {
        final Map<SectionType, Section> sections = resume.getSections();
        writeCollection(sections.entrySet(), out, (section -> {
            SectionType sectionType = section.getKey();
            writeLine(out, sectionType.name());
            switch (sectionType) {
                case PERSONAL:
                case OBJECTIVE:
                    writeLine(out, ((SimpleSection) section.getValue()).getContent());
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    final List<String> simpleSections = ((ListSection) section.getValue()).getContent();
                    writeCollection(simpleSections, out, (item -> writeLine(out, item)));
                    break;
                case EXPERIENCE:
                case EDUCATION:
                    final List<Organization> organizationSections = ((OrganizationSection) section.getValue()).getContent();
                    writeCollection(organizationSections, out, (organization -> {
                        Link link = organization.getLink();
                        writeLine(out, link.getName());
                        writeLine(out, link.getUrl());
                        final List<Organization.Position> positions = organization.getPositions();
                        writeCollection(positions, out, (position -> {
                            writeLine(out, position.getBeginDate().toString());
                            writeLine(out, position.getEndDate().toString());
                            writeLine(out, position.getTitle());
                            writeLine(out, position.getDescription());
                        }));
                    }));
                    break;
            }
        }));
    }

    @FunctionalInterface
    private interface ActionOnRead {
        void readItem() throws IOException;
    }

    private void readCollection(DataInputStream input, ActionOnRead action) throws IOException {
        byte size = input.readByte();
        for (int i = 0; i < size; i++) {
            action.readItem();
        }
    }

    private String readLine(DataInputStream input) throws IOException {
        final String s = input.readUTF();
        return s.isEmpty() ? null : s;
    }

    private void readContacts(DataInputStream input, Resume resume) throws IOException {
        readCollection(input, () -> resume.addContact(ContactType.valueOf(readLine(input)), readLine(input)));
    }

    private void readSections(DataInputStream input, Resume resume) throws IOException {
        readCollection(input, () -> {
            SectionType sectionType = SectionType.valueOf(readLine(input));
            switch (sectionType) {
                case PERSONAL:
                case OBJECTIVE:
                    resume.addSection(sectionType, new SimpleSection(readLine(input)));
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    List<String> content = new ArrayList<>();
                    readCollection(input, () -> content.add(readLine(input)));
                    resume.addSection(sectionType, new ListSection(content));
                    break;
                case EXPERIENCE:
                case EDUCATION:
                    List<Organization> organizations = new ArrayList<>();
                    readCollection(input, () -> {
                        final String name = readLine(input);
                        final String url = readLine(input);
                        List<Organization.Position> positions = new ArrayList<>();
                        readCollection(input, () -> positions.add(
                                new Organization.Position(
                                        LocalDate.parse(input.readUTF()),
                                        LocalDate.parse(input.readUTF()),
                                        readLine(input),
                                        readLine(input))));
                        Organization organization = new Organization(name, url, positions);
                        organizations.add(organization);
                    });
                    resume.addSection(sectionType, new OrganizationSection(organizations));
                    break;
            }
        });
    }
}
