package ru.javawebinar.basejava.storage.Serialization;

import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.util.XmlParser;

import java.io.*;

public class XmlSerialization implements SerializationStrategy {
    private XmlParser xmlParser;

    public XmlSerialization() {
        xmlParser = new XmlParser(
                Resume.class, SimpleSection.class,
                ListSection.class, Link.class,
                Organization.class, OrganizationSection.class, Organization.Position.class);
    }

    @Override
    public void doWrite(OutputStream outputStream, Resume resume) throws IOException {
        try (Writer writer = new OutputStreamWriter(outputStream)) {
            xmlParser.marshall(resume, writer);
        }
    }

    @Override
    public Resume doRead(InputStream inputStream) throws IOException {
        try (Reader reader = new InputStreamReader(inputStream)) {
            return xmlParser.unmarshall(reader);
        }

    }
}
