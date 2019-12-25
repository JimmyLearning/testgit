package me.jiu4.file;

import java.io.File;

public class FileManagement {

    public static void main(String[] args) {
//        System.err.println(new File("C:\\Software\\a.txt").getAbsoluteFile());
//        System.err.println(new File("C:\\Software\\a.txt").getAbsolutePath());
//        System.err.println(new File("C:\\Software\\a.txt").getName());
//        System.err.println(new File("C:\\Software\\a.txt").getParent());
//        System.err.println(new File("C:\\Software\\a.txt").getParentFile());
//        System.err.println(new File("C:\\Software\\a.txt").getPath());
        FileCopy.copy(new File("C:\\Software\\Fliqlo"));
    }

}
