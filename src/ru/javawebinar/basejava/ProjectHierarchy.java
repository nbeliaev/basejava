package ru.javawebinar.basejava;

import java.io.File;

public class ProjectHierarchy {
    public static void main(String[] args) {
        final File parentDirectory = new File("./");
        final String separator = "";
        print(parentDirectory, separator);
    }

    private static void print(final File parentDirectory, final String separator) {
        if (parentDirectory.isDirectory()) {
            final File[] files = parentDirectory.listFiles();
            if (files != null) {
                for (File file : files) {
                    System.out.println(separator + file.getName());
                    if (file.isDirectory()) {
                        print(file, separator + "\t");
                    }
                }
            }
        }
    }

}
