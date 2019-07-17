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
            writeWithException(
                    resume.getContacts(),
                    out,
                    (collection, stream) -> {
                        for (Map.Entry<ContactType, String> entry : ((Map<ContactType, String>) collection).entrySet()) {
                            writeLine(stream, entry.getKey().name());
                            writeLine(stream, entry.getValue());
                        }
                    });

            out.writeByte(resume.getSections().size());
            writeWithException(
                    resume.getSections(),
                    out,
                    ((collection, stream) -> {
                        for (Map.Entry<SectionType, Section> entry : ((Map<SectionType, Section>) collection).entrySet()) {
                            SectionType sectionType = entry.getKey();
                            writeLine(stream, sectionType.name());
                            switch (sectionType) {
                                case PERSONAL:
                                case OBJECTIVE:
                                    writeLine(stream, (((SimpleSection) entry.getValue())).getContent());
                                    break;
                                case ACHIEVEMENT:
                                case QUALIFICATIONS:
                                    final List<String> simpleSections = ((ListSection) entry.getValue()).getContent();
                                    stream.writeByte(simpleSections.size());
                                    for (String item : simpleSections) {
                                        writeLine(stream, item);
                                    }
                                    break;
                                case EXPERIENCE:
                                case EDUCATION:
                                    final List<Organization> organizationSections = ((OrganizationSection) entry.getValue()).getContent();
                                    stream.writeByte(organizationSections.size());
                                    for (Organization item : organizationSections) {
                                        Link link = item.getLink();
                                        writeLine(stream, link.getName());
                                        writeLine(stream, link.getUrl());

                                        stream.writeByte(item.getPositions().size());
                                        final List<Organization.Position> positions = item.getPositions();
                                        for (Organization.Position position : positions) {
                                            writeLine(stream, position.getBeginDate().toString());
                                            writeLine(stream, position.getEndDate().toString());
                                            writeLine(stream, position.getTitle());
                                            writeLine(stream, position.getDescription());
                                        }
                                    }
                                    break;
                            }
                        }
                    }));
        }
    }

    @Override
    public Resume doRead(InputStream inputStream) throws IOException {
        try (DataInputStream input = new DataInputStream(inputStream)) {
            Resume resume = new Resume(input.readUTF(), input.readUTF());

            byte size;
            size = input.readByte();
            for (byte i = 0; i < size; i++) {
                resume.addContact(ContactType.valueOf(input.readUTF()), input.readUTF());
            }

            size = input.readByte();
            for (byte i = 0; i < size; i++) {
                SectionType sectionType = SectionType.valueOf(input.readUTF());
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        resume.addSection(sectionType, new SimpleSection(input.readUTF()));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        final byte listSectionSize = input.readByte();
                        List<String> content = new ArrayList<>();
                        for (int j = 0; j < listSectionSize; j++) {
                            content.add(input.readUTF());
                        }
                        resume.addSection(sectionType, new ListSection(content));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        List<Organization> organizations = new ArrayList<>();
                        final byte organizationSectionSize = input.readByte();
                        for (int j = 0; j < organizationSectionSize; j++) {
                            final String name = input.readUTF();
                            final String url = input.readUTF();
                            final byte positionSize = input.readByte();
                            List<Organization.Position> positions = new ArrayList<>();
                            for (int k = 0; k < positionSize; k++) {
                                positions.add(
                                        new Organization.Position(
                                                LocalDate.parse(input.readUTF()),
                                                LocalDate.parse(input.readUTF()),
                                                input.readUTF(),
                                                input.readUTF()));
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

    private <T> void writeWithException(T collection, DataOutputStream out, Action action) throws IOException {
        action.writeCollection(collection, out);
    }

    @FunctionalInterface
    private interface Action<T> {
        void writeCollection(T collection, DataOutputStream stream) throws IOException;
    }
}
