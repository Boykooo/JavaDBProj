package Screens.EmployeeScreen;


import Screens.AbstractController;
import Screens.MainScreen.MainController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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

    //Добавление
    @FXML
    private TextField nameBox;
    @FXML
    private TextField phoneBox;
    @FXML
    private Button addButton;

    //Удаление
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

    //Редактирование
    @FXML
    private Button updateButton;
    @FXML
    private TextField box_updateName;
    @FXML
    private TextField box_updatePhone;
    @FXML
    private Label label_ID;

    //Поиск
    @FXML
    private TextField box_searchName;
    @FXML
    private Button searchButton;
    @FXML
    private TextField box_searchPhone;

    @FXML
    private void click_AddButton() {
        if (checkData()) {
            closeWindow(addButton);
            parent.addCortege(nameBox.getText(), phoneBox.getText(), "employee");
        } else {
            showAlert("Поля не должны быть пусты");
        }
    }

    @FXML
    private void click_DeleteButton() {
        if (checkDeleteData()) {
            closeWindow(deleteButton);
            parent.deleteCortege(delete_IDBox.getText(), idChoiceBox.getValue(), delete_NameBox.getText(), delete_PhoneNumber.getText(), "employee");
        } else {
            showAlert("Должен быть введен хотя бы 1 параметр");
        }
    }

    @FXML
    private void click_UpdateButton() {
        if (checkUpdateData()) {
            parent.updateCortege(box_updateName.getText(), box_updatePhone.getText(), label_ID.getText(), "employee");
            closeWindow(updateButton);
        } else {
            showAlert("Некорректный ввод");
        }
    }

    @FXML
    private void click_SearchButton() {
        if (checkSearchData()) {
            parent.searchData(
                    box_searchName.getText(),
                    box_searchPhone.getText(),
                    "Employee"
            );
            closeWindow(searchButton);
        }
        else {
            showAlert("Хотя бы одно поле должно быть заполнено");
        }
    }

    @Override
    public void setTextField(Object... objects) {
        box_updateName.setText(objects[0].toString());
        box_updatePhone.setText(objects[1].toString());
        label_ID.setText(objects[2].toString());
    }

    private boolean checkData() {
        return !nameBox.getText().isEmpty() && !phoneBox.getText().isEmpty();
    }

    private boolean checkDeleteData() {
        return !delete_IDBox.getText().isEmpty() || !delete_PhoneNumber.getText().isEmpty() || !delete_NameBox.getText().isEmpty();
    }

    private boolean checkUpdateData() {
        return !box_updatePhone.getText().isEmpty();
    }

    private boolean checkSearchData() {
        return !box_searchName.getText().isEmpty()
                || !box_searchPhone.getText().isEmpty();
    }


}




























