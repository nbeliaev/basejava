package ru.javawebinar.basejava.util;

import ru.javawebinar.basejava.model.*;

import java.util.List;
import java.util.Map;

public class HtmlUtil {
    public static String contactPreview(Map.Entry<ContactType, String> contactEntry) {
        final String contact = contactEntry.getValue();
        if (contact == null || contact.isEmpty()) {
            return "";
        }
        switch (contactEntry.getKey()) {
            case SKYPE:
                return "<a href='skype:" + contact + "'>" + contact + "</a>";
            case EMAIL:
                return "<a href='mailto:" + contact + "'>" + contact + "</a>";
            case MOBILE_PHONE:
            case LINKEDIN:
            case GITHUB:
            case STACKOVERFLOW:
            case WEBPAGE:
                return "<a href='" + contact + "'>" + contact + "</a>";
            default:
                throw new IllegalArgumentException("Unknown contact type");
        }
    }

    public static String sectionPreview(Map.Entry<SectionType, Section> sectionEntry) {
        final Section section = sectionEntry.getValue();
        if (section == null) {
            return "";
        }
        switch (sectionEntry.getKey()) {
            case PERSONAL:
            case OBJECTIVE:
                return ((SimpleSection) section).getContent();
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                final List<String> content = ((ListSection) section).getContent();
                return String.join("<br>", content);
            default:
                throw new IllegalArgumentException("Unknown section type");
        }
    }
}
