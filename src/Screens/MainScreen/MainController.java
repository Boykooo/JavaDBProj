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
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ResourceBundle;

public class MainController extends AbstractController implements IMainController, Initializable {


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        choiceDBBOX.setOnAction(event -> showTable(choiceDBBOX.getValue()));
        db = new DBController(this);
        showTable("Employee");
    }

    private DBController db;
    private AbstractController children;

    @FXML
    private ChoiceBox<String> choiceDBBOX;
    @FXML
    private TableView<Object> dbBox;
    @FXML
    private Button showSpecificAgreement;


    @Override
    public void addCortege(Object... params) {
        db.addNewCortege(params);
    }

    @Override
    public void deleteCortege(Object... params) {
        db.deleteCortege(params);
    }

    @Override
    public void updateCortege(Object... params) {
        db.updateCortege(params);
    }

    @Override
    public void showSpecificAgreement(String phone) {
        ResultSet dbData = db.getSpecificAgreement(phone);

        showTable(dbData, "Cassette");
    }

    @Override
    public void closeConnection() {
        db.closeConnection();
    }

    @FXML
    private void click_refreshButton() {
        showTable(choiceDBBOX.getValue());
    }

    @FXML
    private void click_AddButton() {

        String fileName = choiceDBBOX.getSelectionModel().getSelectedItem();
        String path = MessageFormat.format("../{0}Screen/New{1}Screen.fxml", fileName, fileName);
        showScreen(path);
    }

    @FXML
    private void click_deleteButton() {
        String fileName = choiceDBBOX.getSelectionModel().getSelectedItem();
        String path = MessageFormat.format("../{0}Screen/Delete{1}Screen.fxml", fileName, fileName);
        showScreen(path);
    }

    @FXML
    private void click_UpdateButton() {

        try {

            Object[] objects = getBoxFieldData(choiceDBBOX.getSelectionModel().getSelectedItem());

            String fileName = choiceDBBOX.getSelectionModel().getSelectedItem();
            String path = MessageFormat.format("../{0}Screen/Update{1}Screen.fxml", fileName, fileName);
            showScreen(path);

            children.setTextField(objects);

        } catch (Exception e) {
            showAlert("Выберете строку, которую хотите изменить");
        }
    }

    @FXML
    private void click_ShowSpecificAgreement() {
        showScreen("../AgreementScreen/SpecificAgreementScreen.fxml");
    }

    private void showTable(String dbName) {
        ResultSet dbData = db.getDB(dbName.toLowerCase());

        showTable(dbData, dbName);
        //db.closeConnection();
    }
    private void showTable(ResultSet dbData, String dbName){
        if (dbData != null) {
            try {
                dbBox.getColumns().clear();

                // Создаем столбцы
                ResultSetMetaData metaData = dbData.getMetaData();
                for (int i = 1; i < metaData.getColumnCount() + 1; i++) {
                    String nameColumn = metaData.getColumnLabel(i);
                    TableColumn<Object, Object> temp = new TableColumn<>(nameColumn);
                    temp.setCellValueFactory(new PropertyValueFactory<Object, Object>(nameColumn));
                    dbBox.getColumns().add(temp);
                }

                // Запихиваем данные в таблицу
                switch (dbName) {
                    case "Employee":
                        dbBox.setItems(getItemsEmployee(dbData));
                        break;
                    case "Agreement":
                        dbBox.setItems(getItemsAgreement(dbData));
                        break;
                    case "Cassette":
                        dbBox.setItems(getItemsCassette(dbData));
                        break;
                    default:
                        break;
                }
            } catch (SQLException e) {
                showAlert("Ошибка обработки ResultSet");
            }
        }
    }

    private ObservableList getItemsEmployee(ResultSet setData) {
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

    private ObservableList getItemsAgreement(ResultSet setData) {
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

                list.add(new Agreement(id_agreement, order_Date, last_Return_Date, name, client_Phone_Number, total_Price, id_Employee));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private ObservableList getItemsCassette(ResultSet setData) {
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

    private Object[] getBoxFieldData(String tableName) {
        Object[] label = null;

        switch (tableName) {
            case "Employee":
                Employee employee = (Employee) dbBox.getSelectionModel().getSelectedItem();
                label = new Object[]{employee.getName(), employee.getPhone_Number(), employee.getID_Employee()};
                return label;
            case "Agreement":
                Agreement agreement = (Agreement) dbBox.getSelectionModel().getSelectedItem();
                label = new Object[]{
                        agreement.getID_Agreement(),
                        agreement.getClient_Name(),
                        agreement.getClient_Phone_Number(),
                        agreement.getTotal_Price(),
                        agreement.getOrder_Date(),
                        agreement.getLast_Return_Date(),
                        agreement.getID_Employee()
                };
                return label;
            case "Cassette":
                Cassette cassette = (Cassette) dbBox.getSelectionModel().getSelectedItem();
                label = new Object[]{
                        cassette.getGenre(),
                        cassette.getName(),
                        cassette.getDirector(),
                        cassette.getPrice(),
                        cassette.getExist(),
                        cassette.getYear(),
                        cassette.getID_Cassette()
                };
                return label;
            default:
                return label;
        }
    }

    private void showScreen(String path) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

            children = loader.getController();
            children.setParent(this);

        } catch (Exception ex) {
            showAlert("Не удалось загрузить экран");
        }
    }


}


























