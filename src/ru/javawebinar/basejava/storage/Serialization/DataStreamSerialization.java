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

            out.writeByte(resume.getContacts().size());
            final Map<ContactType, String> contacts = resume.getContacts();
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                writeWithException(entry, (action -> {
                    writeLine(out, entry.getKey().name());
                    writeLine(out, entry.getValue());
                }));
            }

            out.writeByte(resume.getSections().size());
            final Map<SectionType, Section> sections = resume.getSections();
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
    }

    @Override
    public Resume doRead(InputStream inputStream) throws IOException {
        try (final DataInputStream input = new DataInputStream(inputStream)) {
            Resume resume = new Resume(readLine(input), readLine(input));

            byte size;
            size = input.readByte();
            for (byte i = 0; i < size; i++) {
                resume.addContact(ContactType.valueOf(readLine(input)), readLine(input));
            }

            size = input.readByte();
            for (byte i = 0; i < size; i++) {
                SectionType sectionType = SectionType.valueOf(readLine(input));
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        resume.addSection(sectionType, new SimpleSection(readLine(input)));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        final byte listSectionSize = input.readByte();
                        List<String> content = new ArrayList<>();
                        for (int j = 0; j < listSectionSize; j++) {
                            content.add(readLine(input));
                        }
                        resume.addSection(sectionType, new ListSection(content));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        List<Organization> organizations = new ArrayList<>();
                        final byte organizationSectionSize = input.readByte();
                        for (int j = 0; j < organizationSectionSize; j++) {
                            final String name = readLine(input);
                            final String url = readLine(input);
                            final byte positionSize = input.readByte();
                            List<Organization.Position> positions = new ArrayList<>();
                            for (int k = 0; k < positionSize; k++) {
                                positions.add(
                                        new Organization.Position(
                                                LocalDate.parse(input.readUTF()),
                                                LocalDate.parse(input.readUTF()),
                                                readLine(input),
                                                readLine(input)));
                            }
                            Organization organization = new Organization(name, url, positions);
                            organizations.add(organization);
                        }
                        resume.addSection(sectionType, new OrganizationSection(organizations));
                        break;
                }
            }
            return resume;
        }
    }

    private void writeLine(DataOutputStream out, String str) throws IOException {
        out.writeUTF(str == null ? "" : str);
    }

    private String readLine(DataInputStream input) throws IOException {
        final String s = input.readUTF();
        return s.isEmpty() ? null : s;
    }

    private <T> void writeWithException(T item, Action action) throws IOException {
        action.writeItem(item);
    }

    @FunctionalInterface
    private interface Action<T> {
        void writeItem(T item) throws IOException;
    }
}
