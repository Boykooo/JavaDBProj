package Screens.MainScreen;

import DBController.BaseClasses.Agreement;
import DBController.BaseClasses.Cassette;
import DBController.BaseClasses.Employee;
import DBController.DBController;
import Interfaces.IController;
import Screens.EmployeeScreen.EmployeeController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class MainController implements IController{
    public void initialize()
    {
        choiceDBBOX.setOnAction(event -> showTable(choiceDBBOX.getValue()));

        db = new DBController(this);
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        showTable("Employee");
    }

    private DBController db;
    private Alert alert;
    private EmployeeController newEmployeeController;

    @FXML
    private ChoiceBox<String> choiceDBBOX;
    @FXML
    private TableView<Object> DBBox;

    @Override
    public void errorMessage(String msg) {
        alert.setContentText(msg);
        alert.showAndWait();
    }
    @FXML
    public void click_AddButton(){
        switch (choiceDBBOX.getSelectionModel().getSelectedItem()){
            case "Employee":
                showEmployeeScreen("NewEmployeeScreen.fxml");
                break;
            case "Agreement":
                showAgreementScreen();
                break;
            case "Cassette":
                showCassetteScreen();
                break;
            default:
                break;
        }
    }
    @FXML
    public void click_refreshButton(MouseEvent mouseEvent) {
        showTable(choiceDBBOX.getValue());
    }
    @FXML
    public void click_deleteButton(MouseEvent mouseEvent) {
        showEmployeeScreen("DeleteEmployeeScreen.fxml");
    }
    public void addNewCortege(Object... params){
        db.addNewCortege(params);
    }
    public void deleteCortege(Object... params){
        db.deleteCortege(params);
    }
    public void closeConnection(){
        db.closeConnection();
    }

    private void showEmployeeScreen(String nameFile){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../EmployeeScreen/"+nameFile));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

            newEmployeeController = loader.getController();
            newEmployeeController.setParent(this);


        }
        catch (IOException ex){
            errorMessage("Не удалось загрузить экран");
        }
    }
    private void showAgreementScreen() {
        try{

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../EmployeeScreen/NewEmployeeScreen.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

            newEmployeeController = loader.getController();
            newEmployeeController.setParent(this);


        }
        catch (IOException ex){
            errorMessage("Не удалось загрузить экран");
        }
    }
    private void showCassetteScreen() {

    }
    private void showTable(String dbName){
        ResultSet dbData = db.getDB(dbName.toLowerCase());

        if (dbData != null)
        {
            try {
                DBBox.getColumns().clear();

                // Создаем столбцы
                ResultSetMetaData metaData = dbData.getMetaData();
                for (int i = 1; i < metaData.getColumnCount() + 1; i++) {
                    String nameColumn = metaData.getColumnLabel(i);
                    TableColumn<Object, Object> temp = new TableColumn<>(nameColumn);
                    temp.setCellValueFactory(new PropertyValueFactory<Object, Object>(nameColumn));
                    DBBox.getColumns().add(temp);
                }

                // Запихиваем данные в таблицу
                switch (dbName)
                {
                    case "Employee":
                        DBBox.setItems(getItemsEmployee(dbData));
                        break;
                    case "Agreement":
                        DBBox.setItems(getItemsAgreement(dbData));
                        break;
                    case "Cassette":
                        DBBox.setItems(getItemsCassette(dbData));
                        break;
                    default:
                        break;
                }
            } catch (SQLException e) {
                errorMessage("Ошибка обработки ResultSet");
            }
        }

        //db.closeConnection();
    }
    private ObservableList getItemsEmployee(ResultSet setData){
        ObservableList<Employee> list = FXCollections.observableArrayList();
        try {
            while (setData.next()) {
                int id = setData.getInt("ID_Employee");
                String name = setData.getString("Name");
                String phone = setData.getString("Phone_Number");
                list.add(new Employee(id, name, phone));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    private ObservableList getItemsAgreement(ResultSet setData){
        ObservableList<Agreement> list = FXCollections.observableArrayList();
        try {
            while (setData.next()) {
                int id_agreement = setData.getInt("ID_Agreement");
                String order_Date = setData.getString("Order_Date");
                String last_Return_Date = setData.getString("Last_Return_Date");
                String name = setData.getString("Client_Name");
                String client_Phone_Number = setData.getString("Client_Phone_Number");
                int total_Price = setData.getInt("Total_Price");
                int id_Employee = setData.getInt("ID_Employee");

                list.add(new Agreement(id_agreement, order_Date, last_Return_Date, name, client_Phone_Number, total_Price, id_Employee ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    private ObservableList getItemsCassette(ResultSet setData){
        ObservableList<Cassette> list = FXCollections.observableArrayList();
        try {
            while (setData.next()) {
                int ID_Cassette = setData.getInt("ID_Cassette");
                String Genre = setData.getString("Genre");
                String Name = setData.getString("Name");
                String Producer = setData.getString("Producer");
                String Price = setData.getString("Price");
                boolean Exist = setData.getBoolean("Exist");
                list.add(new Cassette(ID_Cassette, Genre, Name, Producer, Price, Exist));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

}


























