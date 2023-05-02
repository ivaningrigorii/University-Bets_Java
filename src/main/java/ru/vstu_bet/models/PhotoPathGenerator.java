package ru.vstu_bet.models;

public class PhotoPathGenerator {
    private static String mainPath = "D:\\Пользователи\\Документы\\" +
            "EDUCATION\\VSTU\\5 семестр\\EE\\course work program" +
            "\\vstu_bet\\src_photo";

    public static String getPath() {
        return mainPath;
    }
}
