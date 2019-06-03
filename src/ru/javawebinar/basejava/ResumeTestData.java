package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ResumeTestData {

    public static void main(String[] args) {
        System.out.println(ResumeTestData.getResume("dummy", "dummy"));
    }

    public static Resume getResume(String uuid, String name) {

        Resume resume = new Resume(uuid, name);
        resume.setFullName("Григорий Кислин");
        resume.addContact(ContactType.MOBILE_PHONE, "+7(921) 855-0482");
        resume.addContact(ContactType.SKYPE, "grigory.kislin");
        resume.addContact(ContactType.EMAIL, "gkislin@yandex.ru");
        resume.addContact(ContactType.LINKEDIN, "https://www.linkedin.com/in/gkislin");
        resume.addContact(ContactType.GITHUB, "https://github.com/gkislin");
        resume.addContact(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/548473");
        resume.addContact(ContactType.WEBPAGE, "http://gkislin.ru/");

        AbstractSection section;
        section = new SimpleSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.");
        resume.addSection(SectionType.PERSONAL, section);
        section = new SimpleSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");
        resume.addSection(SectionType.OBJECTIVE, section);

        List<String> achievement = new ArrayList<>();
        achievement.add("С 2013 года: разработка проектов \"Разработка Web приложения\"");
        achievement.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike.");
        achievement.add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM.");
        section = new ListSection(achievement);
        resume.addSection(SectionType.ACHIEVEMENT, section);

        List<String> qualifications = new ArrayList<>();
        qualifications.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        qualifications.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        qualifications.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle");
        section = new ListSection(qualifications);
        resume.addSection(SectionType.QUALIFICATIONS, section);

        List<Organization> experience = new ArrayList<>();
        experience.add(new Organization(
                "Java Online Projects",
                "http://javaops.ru/",
                LocalDate.of(2013, 10, 1),
                "Автор проекта.\n" +
                        "Создание, организация и проведение Java онлайн проектов и стажировок"));
        experience.add(new Organization(
                "Wrike",
                LocalDate.of(2014, 10, 1),
                LocalDate.of(2016, 1, 1),
                "Старший разработчик (backend)\n" +
                        "Проектирование и разработка онлайн платформы управления проектами Wrike"));
        section = new OrganizationSection(experience);
        resume.addSection(SectionType.EXPERIENCE, section);

        List<Organization> education = new ArrayList<>();
        education.add(new Organization(
                "Coursera",
                "https://www.coursera.org/course/progfun",
                LocalDate.of(2013, 3, 1),
                LocalDate.of(2013, 5, 1),
                "\"Functional Programming Principles in Scala\" by Martin Odersky"));
        education.add(new Organization(
                "Luxoft",
                "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366",
                LocalDate.of(2011, 3, 1),
                LocalDate.of(2011, 4, 1),
                "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\""));
        section = new OrganizationSection(education);
        resume.addSection(SectionType.EDUCATION, section);

        return resume;
    }

}
