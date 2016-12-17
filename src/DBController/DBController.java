package DBController;

import Screens.AbstractController;
import com.mysql.fabric.jdbc.FabricMySQLDriver;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class DBController {
    public DBController(AbstractController view) {
        this.view = view;
        try {
            DriverManager.registerDriver(new FabricMySQLDriver());
        } catch (SQLException e) {
            view.showAlert("Ошибка при подключении драйвера субд");
        }
        attachDB();
    }

    private Connection conn;
    private AbstractController view;

    public ResultSet getDB(String nameDB) {
        ResultSet resultSet = null;
        try {
            Statement st = conn.createStatement();
            resultSet = st.executeQuery("Select * from " + nameDB);

        } catch (SQLException e) {
            view.showAlert("Ошибка при получении таблицы");
        }

        return resultSet;
    }

    public void closeConnection() {
        detachDB();
    }

    public void addNewCortege(Object... params) {
        switch (params[params.length - 1].toString()) {
            case "employee":
                addNewEmployee(params[0].toString(), params[1].toString());
                break;
            case "agreement":
                addNewAgreement(params[0].toString(),
                        params[1].toString(),
                        params[2].toString(),
                        params[3].toString(),
                        params[4].toString(),
                        params[5].toString(),
                        params[6].toString());
                break;
            case "cassette":
                addNewCassette(
                        params[0].toString(),
                        params[1].toString(),
                        params[2].toString(),
                        params[3].toString(),
                        (boolean) params[4],
                        params[5].toString()
                );
                break;
        }
    }

    public void deleteCortege(Object... params) {
        switch (params[params.length - 1].toString()) {
            case "employee":
                deleteEmployee(params[0].toString(),
                        params[1].toString(),
                        params[2].toString(),
                        params[3].toString());
                break;
            case "agreement":
                deleteAgreement(params[0].toString(),
                        params[1].toString(),
                        params[2].toString(),
                        params[3].toString(),
                        params[4].toString(),
                        params[5].toString(),
                        params[6].toString(),
                        params[7].toString()
                );
                break;
            case "cassette":
                deleteCassette(
                        params[0].toString(),
                        params[1].toString(),
                        params[2].toString(),
                        params[3].toString(),
                        params[4].toString(),
                        params[5].toString(),
                        params[6].toString(),
                        params[7].toString(),
                        params[8].toString()
                );
                break;
            default:
                break;
        }
    }

    public void updateCortege(Object... params) {
        switch (params[params.length - 1].toString()) {
            case "employee":
                updateEmployee(params[0].toString(), params[1].toString(), params[2].toString());
                break;
            case "agreement":
                updateAgreement(
                        params[0].toString(),
                        params[1].toString(),
                        params[2].toString(),
                        params[3].toString(),
                        params[4].toString(),
                        params[5].toString(),
                        params[6].toString()
                );
                break;
            case "cassette":
                updateCassette(
                        params[0].toString(),
                        params[1].toString(),
                        params[2].toString(),
                        params[3].toString(),
                        params[4].toString(),
                        params[5].toString(),
                        params[6].toString()
                );

                break;
            default:
                break;
        }
    }

    public ResultSet getSpecificAgreement(String phone) {
        return getAgreementCassettes(phone);
    }

    //Добавление в базу
    private void addNewEmployee(String name, String phone) {
        PreparedStatement request = null;
        try {
            request = conn.prepareStatement("INSERT INTO employee (Name, Phone_Number) VALUES (?, ?)");
            request.setString(1, name);
            request.setString(2, phone);

            request.executeUpdate();
        } catch (SQLException ex) {
            view.showAlert("Ошибка при добавлении нового сотрудника");
        } finally {
            try {
                request.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void addNewAgreement(String clientName, String clientPhone, String totalPrice,
                                 String filmsID, String orderDate, String returnDate, String employeeID) {

        if (!checkFilmsID(filmsID)) {
            view.showAlert("Некорректные id фильмов");
            return;
        }

        PreparedStatement request = null;
        try {
            String req = "INSERT INTO agreement (Client_Name, Client_Phone_Number, Total_Price, Order_Date, ID_Employee, Last_Return_Date) VALUES (?, ?, ?, ?, ?, ?)";
            request = conn.prepareStatement(req);
            request.setString(1, clientName);
            request.setString(2, clientPhone);
            request.setString(3, totalPrice);
            request.setString(4, orderDate);
            request.setInt(5, Integer.parseInt(employeeID));
            request.setString(6, returnDate);

            request.executeUpdate();

            req = "SELECT ID_Agreement FROM Agreement WHERE Client_Phone_Number = ?";
            request = conn.prepareStatement(req);
            request.setString(1, clientPhone);

            //Получаем id договора
            ResultSet resultSet = request.executeQuery();
            resultSet.next();
            String idAgreement = resultSet.getString(1);

            addAgrAndCassette(idAgreement, filmsID);

            changeExistCassette(filmsID.split(","), false);

        } catch (Exception ex) {
            view.showAlert("Ошибка при добавлении нового договора");
        } finally {
            try {
                request.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void addAgrAndCassette(String idAgreement, String filmsID) {
        String[] cassettes = filmsID.split(",");
        String req = "INSERT INTO cassette_rentals (ID_Agreement, ID_Cassette) VALUES (?, ?)";

        PreparedStatement request = null;
        try {
            request = conn.prepareStatement(req);

            int idAgr = Integer.parseInt(idAgreement);

            //Добавляем все кассеты в договоре
            for (String cassette : cassettes) {

                request.setInt(1, idAgr);
                request.setInt(2, Integer.parseInt(cassette));
                request.executeUpdate();

            }

        } catch (Exception e) {
            view.showAlert("Ошибка при добавлении в базу cassette_rentals");
        } finally {
            try {
                request.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void addNewCassette(String genre, String name, String director, String price, boolean exist, String year) {


        if (!checkFreeValue("select * from cassette where Name = ?", name)) {
            view.showAlert(name + " уже находится в базе");
            return;
        }

        PreparedStatement request = null;
        try {

            String req = "INSERT INTO cassette (Genre, Name, Director, Price, Exist, Year) values(?,?,?,?,?,?)";
            request = conn.prepareStatement(req);
            request.setString(1, genre);
            request.setString(2, name);
            request.setString(3, director);
            request.setInt(4, getInteger(price));
            request.setBoolean(5, exist);
            request.setInt(6, getInteger(year));

            request.executeUpdate();

            request.close();

        } catch (Exception ex) {
            view.showAlert("Ошибка при добавлении новой кассеты");
        } finally {
            try {
                request.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //Удаление из базы
    private void deleteEmployee(String id, String sign, String name, String phone) {

        if (!checkSign(sign)) {
            view.showAlert("Попытка sql-инъекции!");
            return;
        }

        PreparedStatement request = null;
        String req = "delete from employee where (ID_Employee " + sign + " ? or ? = '') and (Name = ? or ? = '') and (Phone_Number = ? or ? = '')";
        try {
            request = conn.prepareStatement(req);

            request.setString(1, id);
            request.setString(2, id);
            request.setString(3, name);
            request.setString(4, name);
            request.setString(5, phone);
            request.setString(6, phone);

            request.executeUpdate();

        } catch (SQLException e) {
            view.showAlert("Ошибка при удалении кортежа");
        } finally {
            try {
                request.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void deleteAgreement(String id, String clientName, String clientPhone,
                                 String totalPrice, String sign, String employeeID, String orderDate, String returnDate) {
        if (!checkSign(sign)) {
            view.showAlert("Попытка sql-инъекции!");
            return;
        }


        String req;
        PreparedStatement request = null;


        try {
            //освобождение кассет

            req = "select * from agreement where " +
                    "(ID_Agreement = ? or ? = '') and " +
                    "(Client_Name = ? or ? = '') and " +
                    "(Total_Price =  ? or ? = '') and " +
                    "(Order_Date = ? or isnull(?)) and " +
                    "(ID_Employee = ? or ? = '') and " +
                    "(Last_Return_Date = ? or isnull(?))";
            request = conn.prepareStatement(req);
            request.setString(1, id);
            request.setString(2, id);
            request.setString(3, clientName);
            request.setString(4, clientName);
            request.setString(5, totalPrice);
            request.setString(6, totalPrice);
            request.setDate(7, parseDate(orderDate));
            request.setDate(8, parseDate(orderDate));
            request.setString(9, employeeID);
            request.setString(10, employeeID);
            request.setDate(11, parseDate(returnDate));
            request.setDate(12, parseDate(returnDate));

            ResultSet resultSet = request.executeQuery();

            resultSet.next();
            int tempID = resultSet.getInt("ID_Agreement");
            freeCassettes(tempID);


            req = "delete from agreement where " +
                    "(ID_Agreement = ? or ? = '') and " +
                    "(Client_Name = ? or ? = '') and " +
                    "(Client_Phone_Number = ? or ? = '') and " +
                    "(Total_Price " + sign + " ? or ? = '') and " +
                    "(Order_Date = ? or isnull(?)) and " +
                    "(ID_Employee = ? or ? = '') and " +
                    "(Last_Return_Date = ? or isnull(?))";

            request = conn.prepareStatement(req);
            request.setString(1, id);
            request.setString(2, id);
            request.setString(3, clientName);
            request.setString(4, clientName);
            request.setString(5, clientPhone);
            request.setString(6, clientPhone);
            request.setString(7, totalPrice);
            request.setString(8, totalPrice);
            request.setDate(9, parseDate(orderDate));
            request.setDate(10, parseDate(orderDate));
            request.setString(11, employeeID);
            request.setString(12, employeeID);
            request.setDate(13, parseDate(returnDate));
            request.setDate(14, parseDate(returnDate));


            request.executeUpdate();


        } catch (Exception e) {
            view.showAlert("Ошибка при удалении договора из базы");
        } finally {
            try {
                request.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void freeCassettes(int idAgreement) {

        PreparedStatement request = null;
        try {
            String req = "select ID_Cassette from cassette_rentals where ID_Agreement = ?";
            request = conn.prepareStatement(req);
            request.setString(1, String.valueOf(idAgreement));

            ResultSet result = request.executeQuery();

            ArrayList<String> temp = new ArrayList<>();

            while (result.next()) {
                temp.add(String.valueOf(result.getInt("ID_Cassette")));
            }

            changeExistCassette(temp.toArray(), true);
        } catch (Exception c) {
            view.showAlert("Ошибка при удалении договора: освобождение кассет");
        } finally {
            try {
                request.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    private void deleteCassette(String id, String idSign, String genre, String name,
                                String director, String price, String year, String yearSign, String exist) {
        if (!checkSign(idSign) || !checkSign(yearSign)) {
            view.showAlert("Попытка sql-инъекции!");
            return;
        }

        String req = "delete from cassette where " +
                "(ID_Cassette " + idSign + " ? or ? = '') and " +
                "(Genre = ? or ? = '') and " +
                "(Name = ? or ? = '') and " +
                "(Director = ? or ? = '') and " +
                "(Price = ? or ? = '') and " +
                "(Exist = ? or ? = '') and " +
                "(Year " + yearSign + " ? or ? = '')";
        PreparedStatement request = null;
        try {
            request = conn.prepareStatement(req);
            request.setString(1, id);
            request.setString(2, id);
            request.setString(3, genre);
            request.setString(4, genre);
            request.setString(5, name);
            request.setString(6, name);
            request.setString(7, director);
            request.setString(8, director);
            request.setString(9, price);
            request.setString(10, price);
            request.setString(11, getBoolean(exist));
            request.setString(12, getBoolean(exist));
            request.setString(13, year);
            request.setString(14, year);

            request.executeUpdate();
        } catch (Exception e) {
            view.showAlert("Ошибка при удалении кассеты из базы");
        } finally {
            try {
                request.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //обновление базы
    private void updateEmployee(String newName, String newPhone, String id) {

        if (!checkFreeUpdateValue("select * from employee where Phone_Number = ? and ID_Employee != ?", newPhone, id)) {
            view.showAlert("Данный телефон занят");
            return;
        }

        PreparedStatement request = null;
        try {
            String req = "update employee set Phone_Number = ?, Name = ? where ID_Employee = ?";
            request = conn.prepareStatement(req);
            request.setString(1, newPhone);
            request.setString(2, newName);
            request.setString(3, id);

            request.executeUpdate();
        } catch (Exception ex) {
            view.showAlert("Ошибка при обновлении значений сотрудника");
        } finally {
            try {
                request.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateAgreement(String newName, String newPhone, String newTotalPrice,
                                 String newOrderDate, String newIdEmployee, String newLastReturnDay, String id) {

        if (!checkFreeUpdateValue("select * from agreement where Client_Phone_Number = ? and ID_Agreement != ?", newPhone, id)) {
            view.showAlert("Данный телефон занят");
            return;
        }

        String req = "update agreement set  " +
                "Client_Name = ?, " +
                "Client_Phone_Number = ?, " +
                "Total_Price = ?, " +
                "Order_Date = ?, " +
                "ID_Employee = ?, " +
                "Last_Return_Date = ? " +
                "where ID_Agreement = ?";
        PreparedStatement request = null;
        try {

            request = conn.prepareStatement(req);
            request.setString(1, newName);
            request.setString(2, newPhone);
            request.setString(3, newTotalPrice);
            request.setDate(4, parseDate(newOrderDate));
            request.setString(5, newIdEmployee);
            request.setDate(6, parseDate(newLastReturnDay));
            request.setString(7, id);

            request.executeUpdate();

            request.close();
        } catch (Exception ex) {
            view.showAlert("Ошибка при обновлении значений договора");
        } finally {
            try {
                request.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateCassette(String newGenre, String newName, String newDirector,
                                String newPrice, String newExist, String newYear, String id) {

        if (!checkFreeUpdateValue("select * from cassette where Name = ? and ID_Cassette != ?", newName, id)) {
            view.showAlert("Данное имя уже в базе");
            return;
        }

        String req = "update cassette set  " +
                "Genre = ?, " +
                "Name = ?, " +
                "Director = ?, " +
                "Price = ?, " +
                "Exist = ?, " +
                "Year = ? " +
                "where ID_Cassette = ?";
        PreparedStatement request = null;
        try {


            request = conn.prepareStatement(req);
            request.setString(1, newGenre);
            request.setString(2, newName);
            request.setString(3, newDirector);
            request.setString(4, newPrice);
            request.setString(5, getBoolean(newExist));
            request.setString(6, newYear);
            request.setString(7, id);

            request.executeUpdate();

            request.close();
        } catch (Exception ex) {
            view.showAlert("Ошибка при обновлении значений кассеты");
        } finally {
            try {
                request.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //Информация из базы
    private ResultSet getAgreementCassettes(String phone) {

        ResultSet resultSet = null;
        PreparedStatement request = null;

        if (!checkExistValue("select * from agreement where Client_Phone_Number = ?", phone)) {
            view.showAlert("Такой номер не существует");
            return resultSet;
        }

        String req = "select * from cassette" +
                " where ID_Cassette in" +
                "(select ID_Cassette from cassette_rentals where ID_Agreement = " +
                "(select ID_Agreement from agreement where Client_Phone_Number = ?) )";
        try {
            request = conn.prepareStatement(req);
            request.setString(1, phone);

            return request.executeQuery();
        } catch (Exception e) {
            view.showAlert("Ошибка в получении данных конкретного договора");
        }

        return resultSet;
    }

    //Вспомогательные функции
    private Date parseDate(String date) throws ParseException {
        if (date.isEmpty()) {
            return null;
        } else {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            java.sql.Date sqlDate = new java.sql.Date(formatter.parse(date).getTime());

            return sqlDate;
        }
    }

    private boolean checkSign(String sign) {
        return sign.equals("=") || sign.equals("<") || sign.equals(">");
    }

    private int getInteger(String str) {
        return str.isEmpty() ? -1 : Integer.parseInt(str);
    }

    private String getBoolean(String bool) {
        switch (bool) {
            case "true":
                return "1";
            case "false":
                return "0";
            default:
                return "";
        }
    }

    private boolean checkFreeValue(String req, String name) {
        return checkValue(req, name) == 0;
    }

    private boolean checkExistValue(String req, String name) {
        return checkValue(req, name) != 0;
    }

    private int checkValue(String req, String name) {
        int count = 0;
        try {
            PreparedStatement request = conn.prepareStatement(req);
            request.setString(1, name);

            ResultSet res = request.executeQuery();
            res.last();
            count = res.getRow();

            request.close();
        } catch (Exception e) {
            view.showAlert("Ошибка в проверке значения");
        }

        return count;
    }


    private boolean checkFreeUpdateValue(String req, String value, String id) {
        int count = 0;
        try {
            PreparedStatement request = conn.prepareStatement(req);
            request.setString(1, value);
            request.setString(2, id);

            ResultSet res = request.executeQuery();
            res.last();
            count = res.getRow();

            request.close();
        } catch (Exception e) {
            view.showAlert("Ошибка в проверке свободного значения - " + value);
        }
        return count == 0;
    }

    private boolean checkFilmsID(String filmsID) {
        try {
            String[] films = filmsID.split(",");
            String req = "select * from cassette where ID_Cassette = ?";
            PreparedStatement request = conn.prepareStatement(req);

            int count;
            for (String film : films) {
                request.setString(1, film);
                ResultSet temp = request.executeQuery();
                temp.last();
                count = temp.getRow();
                if (count == 0)
                    return false;
            }

            request.close();
        } catch (Exception e) {
            view.showAlert("Ошибка в проверке корректности id фильмов");
        }
        return true;
    }

    private void changeExistCassette(Object[] films, boolean exist) {
        try {
            String req = "update cassette set Exist = ? where ID_Cassette = ?";
            PreparedStatement request = conn.prepareStatement(req);

            for (Object film : films) {
                request.setBoolean(1, exist);
                request.setString(2, film.toString());
                request.executeUpdate();
            }

            request.close();
        } catch (Exception e) {
            view.showAlert("Ошибка при обновлении поля Exist в таблице Cassette");
        }
    }

    private void attachDB() {
        try {

            String USERNAME = "admin";
            String PASSWORD = "adminadmin007";
            String URL = "jdbc:mysql://91.202.20.14:3306/videorental?autoReconnect=true&useSSL=false";

            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            if (!conn.isClosed()) {
                System.out.printf("Соединение с базой установлено \n");
            }
        } catch (SQLException e) {
            view.showAlert("Конект не удался");
        }
    }

    private void detachDB() {
        try {
            if (!conn.isClosed()) {
                conn.close();
                System.out.printf("База отключена \n");
            }
        } catch (SQLException e) {
            view.showAlert("Отключить базу не удалось");
        }
    }
}























