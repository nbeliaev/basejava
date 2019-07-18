package ru.javawebinar.basejava.storage.Serialization;

import ru.javawebinar.basejava.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
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

    private <T> void writeWithException(T item, ActionOnWrite<T> action) throws IOException {
        action.writeItem(item);
    }

    private void writeLine(DataOutputStream out, String str) throws IOException {
        out.writeUTF(str == null ? "" : str);
    }

    private void writeContacts(DataOutputStream out, Resume resume) throws IOException {
        final Map<ContactType, String> contacts = resume.getContacts();
        out.writeByte(contacts.size());
        for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
            writeWithException(entry, (action -> {
                writeLine(out, entry.getKey().name());
                writeLine(out, entry.getValue());
            }));
        }
    }

    private void writeSections(DataOutputStream out, Resume resume) throws IOException {
        final Map<SectionType, Section> sections = resume.getSections();
        out.writeByte(sections.size());
        for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
            SectionType sectionType = entry.getKey();
            writeLine(out, sectionType.name());
            switch (sectionType) {
                case PERSONAL:
                case OBJECTIVE:
                    writeWithException(entry, (action -> writeLine(out, ((SimpleSection) entry.getValue()).getContent())));
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    final List<String> simpleSections = ((ListSection) entry.getValue()).getContent();
                    out.writeByte(simpleSections.size());
                    for (String item : simpleSections) {
                        writeWithException(item, (action -> writeLine(out, item)));
                    }
                    break;
                case EXPERIENCE:
                case EDUCATION:
                    final List<Organization> organizationSections = ((OrganizationSection) entry.getValue()).getContent();
                    out.writeByte(organizationSections.size());
                    for (Organization item : organizationSections) {
                        writeWithException(item, (action -> {
                            Link link = item.getLink();
                            writeLine(out, link.getName());
                            writeLine(out, link.getUrl());
                        }));

                        out.writeByte(item.getPositions().size());
                        final List<Organization.Position> positions = item.getPositions();
                        for (Organization.Position position : positions) {
                            writeWithException(position, (action -> {
                                writeLine(out, position.getBeginDate().toString());
                                writeLine(out, position.getEndDate().toString());
                                writeLine(out, position.getTitle());
                                writeLine(out, position.getDescription());
                            }));
                        }
                    }
                    break;
            }
        }
    }

    @FunctionalInterface
    private interface ActionOnRead<T> {
        T readItem() throws IOException;
    }

    private <T> T readWithException(ActionOnRead<T> action) throws IOException {
        return action.readItem();
    }

    private String readLine(DataInputStream input) throws IOException {
        final String s = input.readUTF();
        return s.isEmpty() ? null : s;
    }

    private void readContacts(DataInputStream input, Resume resume) throws IOException {
        byte size;
        size = readWithException(input::readByte);
        for (byte i = 0; i < size; i++) {
            resume.addContact(
                    readWithException(() -> ContactType.valueOf(readLine(input))),
                    readWithException(() -> readLine(input)));
        }
    }

    private void readSections(DataInputStream input, Resume resume) throws IOException {
        byte size = readWithException(input::readByte);
        for (byte i = 0; i < size; i++) {
            SectionType sectionType = readWithException(() -> SectionType.valueOf(readLine(input)));
            switch (sectionType) {
                case PERSONAL:
                case OBJECTIVE:
                    resume.addSection(sectionType, new SimpleSection(readWithException(() -> readLine(input))));
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    final byte listSectionSize = readWithException(input::readByte);
                    List<String> content = new ArrayList<>();
                    for (int j = 0; j < listSectionSize; j++) {
                        content.add(readWithException(() -> readLine(input)));
                    }
                    resume.addSection(sectionType, new ListSection(content));
                    break;
                case EXPERIENCE:
                case EDUCATION:
                    List<Organization> organizations = new ArrayList<>();
                    final byte organizationSectionSize = readWithException(input::readByte);
                    for (int j = 0; j < organizationSectionSize; j++) {
                        final String name = readWithException(() -> readLine(input));
                        final String url = readWithException(() -> readLine(input));
                        final byte positionSize = readWithException(input::readByte);
                        List<Organization.Position> positions = new ArrayList<>();
                        for (int k = 0; k < positionSize; k++) {
                            positions.add(
                                    new Organization.Position(
                                            LocalDate.parse(readWithException(input::readUTF)),
                                            LocalDate.parse(readWithException(input::readUTF)),
                                            readWithException(() -> readLine(input)),
                                            readWithException(() -> readLine(input))));
                        }
                        Organization organization = new Organization(name, url, positions);
                        organizations.add(organization);
                    }
                    resume.addSection(sectionType, new OrganizationSection(organizations));
                    break;
            }
        }
    }
}
