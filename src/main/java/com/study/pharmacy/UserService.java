package com.study.pharmacy;

import java.sql.SQLException;
import java.util.List;

/*
    UserService - класс, который обеспечивает функционал пользователей.
    Он отвечает за вход, регистрацию и отправку сообщения, а также назначение ролей юзерам в системе.
 */
public class UserService {
    private static String currentUser;
    private static final SQLiteHandler source;

    static {
        try {
            source = SQLiteHandler.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean findAccountName(String account_name, List<SQLiteHandler.Account> accounts) throws SQLException {
        System.out.println("-- findAccountName --");
        for (SQLiteHandler.Account account : accounts)
            if (account.getLogin().equals(account_name))
                return false;
        return true;
    }

    public static boolean login(String login, String password) throws SQLException {
        System.out.println("-- login --");
        for (SQLiteHandler.Account account : source.getAllAccounts()) {
            if (account.getLogin().equals(login) && account.getPassword().equals(password)) {
                currentUser = account.getLogin();
                System.out.println(currentUser);
                return true;
            }
        }
        return false;
    }

    public static boolean sendMessage(String account_name, String text, String recipient_name) throws SQLException {
        System.out.println("-- sendMessage --");

        List<SQLiteHandler.Account> accounts = source.getAllAccounts();

        if (findAccountName(account_name, accounts)) {
            System.out.println("Системная ошибка: аккаунта нет в базе данных!");
            return false;
        }

        if (text.isEmpty()) {
            System.out.println("Текст сообщения пустой!");
            return false;
        }

        if (findAccountName(recipient_name, accounts)) {
            System.out.println("Неверный адрес получателя");
            return false;
        }

        if (source.registeringMessage(account_name, text, recipient_name)) {
            System.out.println("Сообщение успешно отправлено!");
            return true;
        }

        return false;
    }



    public static boolean employeeRegist(String FIO, String date_of_birth, String passport, String telephone,
                                         String address, String employmentDate, String jobTitle,
                                         String login, String password, String role) throws SQLException {
        System.out.println("-- employeeRegist --");

        source.addAccount(login, password, role);
        source.addEmployee(FIO, date_of_birth, passport, telephone, address, employmentDate, jobTitle, login);

        return true;


    }
}
