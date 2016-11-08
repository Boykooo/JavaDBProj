package DBController;

import Screens.AbstractController;
import com.mysql.fabric.jdbc.FabricMySQLDriver;
import java.sql.*;


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
    private void addNewAgreement(){

    }
    private void addNewCassette(){

    }

    private void deleteEmployee(String id, String sign, String name, String phone){
        try{
            String req = "delete from employee where (ID_Employee = ? or ? = '') and (Name = ? or ? = '') and (Phone_Number = ? or ? = '')";
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

    public ResultSet getDB(String nameDB){

        //attachDB();
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
                break;
            case "cassette":
                break;
        }
    }
    public void deleteCortege(Object... params){
        switch (params[params.length - 1].toString()){
            case "employee":
                deleteEmployee(params[0].toString(), params[1].toString(), params[2].toString(), params[3].toString());
                break;
            case "agreement":
                break;
            case "cassette":
                break;
            default:
                break;
        }
    }
}
