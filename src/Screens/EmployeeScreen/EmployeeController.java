package Screens.EmployeeScreen;


import Screens.AbstractController;
import Screens.MainScreen.MainController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class EmployeeController extends AbstractController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (idChoiceBox != null)
            idChoiceBox.getItems().addAll("=", "<", ">");
    }

    @FXML
    private TextField nameBox;
    @FXML
    private TextField phoneBox;
    @FXML
    private Button addButton;
    @FXML
    private Button deleteButton;
    @FXML
    private TextField delete_IDBox;
    @FXML
    private TextField delete_NameBox;
    @FXML
    private TextField delete_PhoneNumber;
    @FXML
    private ChoiceBox idChoiceBox;

    public void click_AddButton(){
        if (checkData()){
            closeWindow(addButton);
            parent.addCortege(nameBox.getText(), phoneBox.getText(), "employee");
        }
        else
        {
            showAlert("Поля не должны быть пусты");
        }
    }
    public void click_DeleteButton(MouseEvent mouseEvent) {
        if (checkDeleteData()){
            closeWindow(deleteButton);
            parent.deleteCortege(delete_IDBox.getText(), idChoiceBox.getValue(), delete_NameBox.getText(), delete_PhoneNumber.getText(), "employee");
        }
        else
        {
            showAlert("Должен быть введен хотя бы 1 параметр");
        }
    }

    private boolean checkData(){
        return !nameBox.getText().isEmpty() && !phoneBox.getText().isEmpty();
    }
    private boolean checkDeleteData(){
        return !delete_IDBox.getText().isEmpty() || !delete_PhoneNumber.getText().isEmpty() || !delete_NameBox.getText().isEmpty();
    }



}
