package org.example;



import org.example.exception.UnknownShortLinkException;
import org.example.jdbc.JdbcUtils;
import org.example.service.MainService;
import org.example.service.MainServiceImpl;

import java.sql.*;
import java.util.Scanner;

import static org.example.service.MainServiceImpl.shortLinksDomain;

public class Main {
    public static Scanner sc = new Scanner(System.in);
    public static MainService service = new MainServiceImpl();
    public static void main(String[] args) {
        try {
            boolean connected = JdbcUtils.createConnection();
            if (!connected) {
                System.out.println("Error with connection to database");
                return;
            }
            runCli();
        } finally {
            JdbcUtils.closeConnection();
        }
    }

    private static void runCli() {
        while (true) {
            printMenu();

            String chosenService = sc.nextLine();

            if (chosenService.equals("1")) {
                System.out.println("Введите длинную ссылку");
                String longLink = null;
                while (longLink == null) {
                    longLink = readAndValidateLongLink();
                }
                String shortLink = service.getShortLink(longLink);
                System.out.println("Короткая ссылка: " + shortLink);

            } else if (chosenService.equals("2")) {
                System.out.println("Введите короткую ссылку:");
                String shortLink = null;
                while (shortLink == null) {
                    shortLink = readAndValidateShortLink();
                }
                try {
                    String longLink = service.getLongLink(shortLink);
                    System.out.println("Длинная ссылка: " + longLink);
                } catch(UnknownShortLinkException ex) {
                    System.out.println("Данной короткой ссылке не соответствует никакая длинная");
                }
            } else if (chosenService.equals("3")) {
                return;
            } else {
                System.out.println("Выберите валидный вариант");
            }
        }
    }

    private static String readAndValidateLongLink() {
        String link = sc.nextLine();

        if (link == null || link.isBlank()) {
            System.out.println("Некорректная ссылка, введите ещё раз");
            return null;
        }

        return link;
    }

    private static String readAndValidateShortLink() {
        String link = sc.nextLine();
        int len = shortLinksDomain.length();

        if (link == null || link.length() < len || !shortLinksDomain.equals(link.substring(0, len))) {
            System.out.println("Короткая ссылка должна начинаться с \"" + shortLinksDomain + "\". Введите ссылку ещё раз");
            return null;
        }

        return link;
    }


    private static void printMenu() {
        System.out.println("""
                Выберите действие:
                1. Укоротить ссылку
                2. Восстановить длинную ссылку по короткой
                3. Выйти
                """);
    }


}