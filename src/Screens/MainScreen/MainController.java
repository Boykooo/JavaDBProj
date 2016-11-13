package Screens.MainScreen;

import DBController.BaseClasses.Agreement;
import DBController.BaseClasses.Cassette;
import DBController.BaseClasses.Employee;
import DBController.DBController;
import Interfaces.IMainController;
import Screens.AbstractController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.MessageFormat;

public class MainController extends AbstractController implements IMainController{

    @FXML
    public void initialize() {
        choiceDBBOX.setOnAction(event -> showTable(choiceDBBOX.getValue()));
        db = new DBController(this);
        showTable("Employee");
    }

    private DBController db;

    @FXML
    private ChoiceBox<String> choiceDBBOX;
    @FXML
    private TableView<Object> DBBox;

    @FXML
    public void click_refreshButton() {
        showTable(choiceDBBOX.getValue());
    }
    @FXML
    public void click_AddButton(){

        String fileName = choiceDBBOX.getSelectionModel().getSelectedItem();
        String path = MessageFormat.format("../{0}Screen/New{1}Screen.fxml", fileName, fileName);
        showScreen(path);
    }
    @FXML
    public void click_deleteButton() {
        String fileName = choiceDBBOX.getSelectionModel().getSelectedItem();
        String path = MessageFormat.format("../{0}Screen/Delete{1}Screen.fxml", fileName, fileName);
        showScreen(path);
    }

    @Override
    public void addCortege(Object... params) {
        db.addNewCortege(params);
    }
    @Override
    public void deleteCortege(Object... params){
        db.deleteCortege(params);
    }
    @Override
    public void updateCortege(Object... params) {

    }
    @Override
    public void closeConnection(){
        db.closeConnection();
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
                showAlert("Ошибка обработки ResultSet");
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
                String genre = setData.getString("Genre");
                String name = setData.getString("Name");
                String producer = setData.getString("Director");
                String price = setData.getString("Price");
                boolean exist = setData.getBoolean("Exist");
                int year = setData.getInt("Year");
                list.add(new Cassette(ID_Cassette, genre, name, producer, price, exist, year));
            }
        } catch (SQLException e) {
            showAlert("Ошибка вывода таблицы");
        }
        return list;
    }

    private void showScreen(String path) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

            AbstractController children = loader.getController();
            children.setParent(this);

        } catch (Exception ex) {
            showAlert("Не удалось загрузить экран");
        }
    }
}


























