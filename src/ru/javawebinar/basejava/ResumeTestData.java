package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.Resume;

public class ResumeTestData {

    public static void main(String[] args) {
        System.out.println(ResumeTestData.getResume("dummy", "dummy"));
    }

    public static Resume getResume(String uuid, String name) {

        Resume resume = new Resume(uuid, name);
        resume.setFullName("Григорий Кислин");
        /*resume.addContact(ContactType.MOBILE_PHONE, "+7(921) 855-0482");
        resume.addContact(ContactType.SKYPE, "grigory.kislin");
        resume.addContact(ContactType.EMAIL, "gkislin@yandex.ru");
        resume.addContact(ContactType.LINKEDIN, "https://www.linkedin.com/in/gkislin");
        resume.addContact(ContactType.GITHUB, "https://github.com/gkislin");
        resume.addContact(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/548473");
        resume.addContact(ContactType.WEBPAGE, "http://gkislin.ru/");

        Section section;
        section = new SimpleSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.");
        resume.addSection(SectionType.PERSONAL, section);
        section = new SimpleSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");
        resume.addSection(SectionType.OBJECTIVE, section);

        section = new ListSection(
                "С 2013 года: разработка проектов \"Разработка Web приложения\"",
                "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike.",
                "Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM.");
        resume.addSection(SectionType.ACHIEVEMENT, section);

        section = new ListSection(
                "JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2",
                "Version control: Subversion, Git, Mercury, ClearCase, Perforce",
                "DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle");
        resume.addSection(SectionType.QUALIFICATIONS, section);

        List<Organization> experience = new ArrayList<>();
        Organization organization;
        organization = new Organization(
                "Java Online Projects",
                "http://javaops.ru/",
                new Organization.Position(
                        DateUtil.of(2013, 10),
                        DateUtil.NOW,
                        "Автор проекта",
                        "Создание, организация и проведение Java онлайн проектов и стажировок"));
        experience.add(organization);

        organization = new Organization(
                "Wrike",
                new Organization.Position(
                        DateUtil.of(2014, 10),
                        DateUtil.of(2016, 1),
                        "Старший разработчик",
                        "Проектирование и разработка онлайн платформы управления проектами Wrike"));
        experience.add(organization);

        section = new OrganizationSection(experience);
        resume.addSection(SectionType.EXPERIENCE, section);

        List<Organization> education = new ArrayList<>();
        organization = new Organization(
                "Coursera",
                "https://www.coursera.org/course/progfun",
                new Organization.Position(
                        DateUtil.of(2013, 3),
                        DateUtil.of(2013, 5),
                        "Functional Programming Principles in Scala by Martin Odersky"));
        education.add(organization);

        organization = new Organization(
                "Luxoft",
                "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366",
                new Organization.Position(
                        DateUtil.of(2011, 3),
                        DateUtil.of(2011, 4),
                        "Курс Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML."));
        education.add(organization);

        organization = new Organization(
                "Санкт-Петербургский национальный исследовательский университет",
                "http://www.ifmo.ru/",
                new Organization.Position(
                        DateUtil.of(1993, 9),
                        DateUtil.of(1996, 7),
                        "Аспирантура (программист С, С++)"),
                new Organization.Position(
                        DateUtil.of(1987, 9),
                        DateUtil.of(1993, 7),
                        "Инженер (программист Fortran, C)"));
        education.add(organization);

        section = new OrganizationSection(education);
        resume.addSection(SectionType.EDUCATION, section);*/

        return resume;
    }

}
