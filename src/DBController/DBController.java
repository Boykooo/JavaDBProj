package DBController;

import Screens.AbstractController;
import com.mysql.fabric.jdbc.FabricMySQLDriver;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class DBController {
    public DBController(AbstractController view){
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

    public ResultSet getDB(String nameDB){
        ResultSet resultSet = null;
        try
        {
            Statement st = conn.createStatement();
            resultSet = st.executeQuery("Select * from " + nameDB);

        }
        catch (SQLException e){
            view.showAlert("Ошибка при получении таблицы");
        }

        return resultSet;
    }
    public void closeConnection(){
        detachDB();
    }
    public void addNewCortege(Object... params){
        switch (params[params.length - 1].toString()){
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
    public void deleteCortege(Object... params){
        switch (params[params.length - 1].toString()){
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
                break;
            default:
                break;
        }
    }



    //Добавление в базу
    private void addNewEmployee(String name, String phone){
        try{
            PreparedStatement request = conn.prepareStatement("INSERT INTO employee (Name, Phone_Number) VALUES (?, ?)");
            request.setString(1, name);
            request.setString(2, phone);

            request.executeUpdate();
        }
        catch (SQLException ex){
            view.showAlert("Ошибка при добавлении нового сотрудника");
        }
    }
    private void addNewAgreement(String clientName, String clientPhone, String totalPrice, String filmsID, String orderDate, String returnDate, String employeeID){
        try{
            String req = "INSERT INTO agreement (Client_Name, Client_Phone_Number, Total_Price, Order_Date, ID_Employee, Last_Return_Date) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement request = conn.prepareStatement(req);
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
            String idAgreement =  resultSet.getString(1);

            addAgrAndCassette(idAgreement, filmsID);
        }
        catch (Exception  ex){
            view.showAlert("Ошибка при добавлении нового договора");
        }
    }
    private void addAgrAndCassette(String idAgreement, String filmsID){
        String[] cassettes = filmsID.split(",");
        String req = "INSERT INTO cassette_rentals (ID_Agreement, ID_Cassette) VALUES (?, ?)";

        try
        {
            PreparedStatement request = conn.prepareStatement(req);

            int idAgr = Integer.parseInt(idAgreement);

            //Добавляем все кассеты в договоре
            for (String cassette : cassettes) {

                request.setInt(1, idAgr);
                request.setInt(2, Integer.parseInt(cassette));
                request.executeUpdate();
            }
        }
        catch (Exception e){
            view.showAlert("Ошибка при добавлении в базу cassette_rentals");
        }
    }
    private void addNewCassette(String genre, String name, String director, String price, boolean exist, String year){

        if (!checkFreeFilmName(name))
        {
            view.showAlert(name + " уже находится в базе");
            return;
        }

        try{

            String req = "INSERT INTO cassette (Genre, Name, Director, Price, Exist, Year) values(?,?,?,?,?,?)";
            PreparedStatement request = conn.prepareStatement(req);
            request.setString(1, genre);
            request.setString(2, name);
            request.setString(3, director);
            request.setInt(4, getInteger(price));
            request.setBoolean(5, exist);
            request.setInt(6, getInteger(year));

            request.executeUpdate();

        }
        catch (Exception  ex){
            view.showAlert("Ошибка при добавлении новой кассеты");
        }
    }

    //Удаление из базы
    private void deleteEmployee(String id, String sign, String name, String phone){

        String req = "delete from employee where (ID_Employee = ? or ? = '') and (Name = ? or ? = '') and (Phone_Number = ? or ? = '')";
        try{
            PreparedStatement request = conn.prepareStatement(req);

            request.setString(1, id);
            request.setString(2, id);
            request.setString(3, name);
            request.setString(4, name);
            request.setString(5, phone);
            request.setString(6, phone);

            request.executeUpdate();
        }
        catch (SQLException e){
            view.showAlert("Ошибка при удалении кортежа");
        }
    }
    private void deleteAgreement(String id, String clientName, String clientPhone, String totalPrice, String sign, String employeeID , String orderDate, String returnDate){
        if (!checkSign(sign))
        {
            view.showAlert("Попытка sql-инъекции!");
            return;
        }


        String req = "delete from agreement where " +
                "(ID_Agreement = ? or ? = '') and " +
                "(Client_Name = ? or ? = '') and " +
                "(Client_Phone_Number = ? or ? = '') and " +
                "(Total_price " + sign + " ? or ? = '') and " +
                "(Order_Date = ? or isnull(?)) " +
                "and (ID_Employee = ? or ? = '') and " +
                "(Last_Return_Date = ? or isnull(?))";
        try
        {
            PreparedStatement request = conn.prepareStatement(req);
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
        }
        catch (Exception e){
            view.showAlert("Ошибка при удалении договора из базы");
        }
    }

    //Вспомогательные функции
    private Date parseDate(String date) throws ParseException {
        if (date.isEmpty())
        {
            return null;
        }
        else
        {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            java.sql.Date sqlDate = new  java.sql.Date(formatter.parse(date).getTime());

            return sqlDate;
        }
    }
    private boolean checkSign(String sign){
        return sign.equals("=") || sign.equals("<") || sign.equals(">");
    }
    private int getInteger(String str){
        return str.isEmpty() ? -1 : Integer.parseInt(str);
    }
    private boolean checkFreeFilmName(String name){

        int count = 0;
        try
        {
            String req = "select * from cassette where Name = ?";
            PreparedStatement request = conn.prepareStatement(req);
            request.setString(1, name);

            ResultSet res = request.executeQuery();
            res.last();
            count = res.getRow();
            System.out.printf(String.valueOf(count));
        }
        catch (Exception e)
        {
            view.showAlert("Ошибка в проверке свободного имени фильма");
        }
        return count == 0;
    }

    private void attachDB(){
        try{
            String PASSWORD = "root";
            String USERNAME = "root";
            String URL = "jdbc:mysql://91.202.20.14:3306/videorental?autoReconnect=true&useSSL=false";

            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            if (!conn.isClosed()){
                System.out.printf("Соединение с базой установлено \n");
            }
        }
        catch (SQLException e){
            view.showAlert("Конект не удался");
        }
    }
    private void detachDB(){
        try {
            if (!conn.isClosed()){
                conn.close();
                System.out.printf("База отключена \n");
            }
        }
        catch (SQLException e){
            view.showAlert("Отключить базу не удалось");
        }
    }
}
