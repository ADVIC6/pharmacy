package com.study.pharmacy;

import java.sql.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
 * SQLiteHandler - сущность для взаимодействия остальных классов с базой данных.
 */
public class SQLiteHandler {

    public class Message {
        int id;
        String account;
        String text;
        String recipient;
        String date;
        String time;

        public Message(int id, String account, String text, String recipient, String date, String time) {
            this.id = id;
            this.account = account;
            this.text = text;
            this.recipient = recipient;
            this.date = date;
            this.time = time;
        }
    }

    public class Account
    {
        private String login;
        private String password;
        private String role;

        public Account(String login, String password, String role) {
            this.login = login;
            this.password = password;
            this.role = role;
        }

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }


    }


    private static final String CON_ADD = "jdbc:sqlite:db/pharmacy.db";

    // Для SQLiteHandler применяется паттерн Singleton
    private static SQLiteHandler instance = null;

    public static synchronized SQLiteHandler getInstance() throws SQLException {
        if (instance == null)
            instance = new SQLiteHandler();
        return instance;
    }

    // Объект, в котором будет храниться соединение с БД
    private Connection connection;

    private SQLiteHandler() throws SQLException {
        this.connection = DriverManager.getConnection(CON_ADD);
    }

    private void closeConnect() throws SQLException {
        this.connection.close();
    }

    public boolean registeringMessage(String login, String text, String recipientID) throws SQLException {
        /*
        * registeringMessage - регистрирует сообщение в базе данных.
        * параметры:    account - логин отправителя
        *               text - текст сообщения
        *               recipientID - адрес отправителя
        * */
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO message(`login`, `text`, `recipient`, `date`, `time`) VALUES(?, ?, ?, ?, ?)")) {
            statement.setObject(1, login);
            statement.setObject(2, text);
            statement.setObject(3, recipientID);
            statement.setObject(4, LocalDate.now());
            statement.setObject(5, LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));
            statement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Account> getAllAccounts() throws SQLException {

        // Statement используется для того, чтобы выполнить sql-запрос
        try (Statement statement = this.connection.createStatement()) {
            // В данный список будем загружать наши продукты, полученные из БД
            List<Account> products = new ArrayList<>();
            // В resultSet будет храниться результат нашего запроса,
            // который выполняется командой statement.executeQuery()
            ResultSet resultSet = statement.executeQuery("SELECT * FROM account");
            // Проходимся по нашему resultSet и заносим данные в products
            while (resultSet.next()) {
                products.add(new Account(
                        resultSet.getString("login"),
                        resultSet.getString("password"),
                        resultSet.getString("role")));
            }
            // Возвращаем наш список
            return products;

        } catch (SQLException e) {
            e.printStackTrace();
            // Если произошла ошибка - возвращаем пустую коллекцию
            return Collections.emptyList();
        }
    }

    public Account getAccount(String login) throws SQLException {
        String query = "SELECT * FROM account WHERE login = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setObject(1, login);

            ResultSet resultSet = statement.executeQuery();

            return new Account(
                    resultSet.getString("login"),
                    resultSet.getString("password"),
                    resultSet.getString("role"));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void addAccount(String login, String password, String role) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO account(`login`, `password`, role) VALUES(?, ?, ?,)")) {
            statement.setObject(1, login);
            statement.setObject(2, password);
            statement.setObject(3, role);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAccount(String login) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM Accounts WHERE login = ?")) {
            statement.setObject(1, login);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addEmployee(String FIO, String dateOfBirth, String passport, String telephone, String address,
                            String employmentDate, String jobTitle, String account) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO Employee(FIO, `dateofbrith`, `passport`, telephone, address, employmentDate, jobTitle, account) VALUES(?, ?, ?, ?, ?, ?, ?, ?)")) {
            statement.setObject(1, FIO);
            statement.setObject(2, dateOfBirth);
            statement.setObject(3, passport);
            statement.setObject(4, telephone);
            statement.setObject(5, address);
            statement.setObject(6, employmentDate);
            statement.setObject(7, jobTitle);
            statement.setObject(8, account);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
