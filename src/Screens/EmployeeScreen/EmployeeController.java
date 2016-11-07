package Screens.EmployeeScreen;


import Screens.MainScreen.MainController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class EmployeeController {




    public void initialize()
    {
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
    }

    @FXML
    public TextField nameBox;
    @FXML
    public TextField phoneBox;
    @FXML
    public Button addButton;
    @FXML
    public Button deleteButton;
    @FXML
    public TextField delete_IDBox;
    @FXML
    public TextField delete_NameBox;
    @FXML
    public TextField delete_PhoneNumber;
    @FXML
    public ChoiceBox idChoiceBox;

    private MainController parent;
    private Alert alert;

    public void setParent(MainController parent) {
        this.parent = parent;
    }

    public void click_AddButton(){
        if (checkData()){
            closeWindow(addButton);
            parent.addNewCortege(nameBox.getText(), phoneBox.getText(), "employee");
        }
        else
        {
            error("Поля не должны быть пусты");
        }
    }
    public void click_DeleteButton(MouseEvent mouseEvent) {
        if (true){
            closeWindow(deleteButton);
            parent.deleteCortege(delete_IDBox.getText(), idChoiceBox.getValue(), delete_NameBox.getText(), delete_PhoneNumber.getText(), "employee");
        }
        else
        {
            error("Одно из следующих полей не должно быть пустым: ID, Phone number");
        }
    }

    private boolean checkData(){
        return !nameBox.getText().isEmpty() && !phoneBox.getText().isEmpty();
    }
    private boolean checkDeleteData(){
        return !delete_IDBox.getText().isEmpty() || !delete_PhoneNumber.getText().isEmpty();
    }
    private void error(String msg){
        alert.setContentText(msg);
        alert.show();
    }
    private void closeWindow(Button btn){
        Stage stage = (Stage) btn.getScene().getWindow();
        stage.close();
    }

}
