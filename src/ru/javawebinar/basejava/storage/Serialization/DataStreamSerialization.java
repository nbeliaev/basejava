package ru.javawebinar.basejava.storage.Serialization;

import ru.javawebinar.basejava.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DataStreamSerialization implements SerializationStrategy {

    @Override
    public void doWrite(OutputStream outputStream, Resume resume) throws IOException {
        try (DataOutputStream out = new DataOutputStream(outputStream)) {
            out.writeUTF(resume.getUuid());
            out.writeUTF(resume.getFullName());

            out.writeByte(resume.getContacts().size());
            resume.getContacts().forEach((contactType, contact) -> {
                try {
                    out.writeUTF(contactType.name());
                    out.writeUTF(contact);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            out.writeByte(resume.getSections().size());
            resume.getSections().forEach((sectionType, section) -> {
                try {
                    out.writeUTF(sectionType.name());
                    switch (sectionType) {
                        case PERSONAL:
                        case OBJECTIVE:
                            writeLine(out, (String) section.getContent());
                            break;
                        case ACHIEVEMENT:
                        case QUALIFICATIONS:
                            final List<String> simpleSection = (List<String>) section.getContent();
                            out.writeByte(simpleSection.size());
                            simpleSection.forEach(item -> writeLine(out, item));
                            break;
                        case EXPERIENCE:
                        case EDUCATION:
                            final List<Organization> organizationsSection = (List<Organization>) section.getContent();
                            out.writeByte(organizationsSection.size());
                            organizationsSection.forEach(item -> {
                                Link link = item.getLink();
                                writeLine(out, link.getName());
                                writeLine(out, link.getUrl());
                                try {
                                    out.writeByte(item.getPositions().size());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                item.getPositions().forEach(position -> {
                                    writeLine(out, position.getBeginDate().toString());
                                    writeLine(out, position.getEndDate().toString());
                                    writeLine(out, position.getDescription());
                                });
                            });
                            break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
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

    private void writeLine(DataOutputStream out, String item) {
        try {
            out.writeUTF(item == null ? "" : item);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
