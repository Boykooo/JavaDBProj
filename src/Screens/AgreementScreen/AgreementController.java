package Screens.AgreementScreen;

import Screens.AbstractController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;


public class AgreementController extends AbstractController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (deleteChoiceTotalPrice != null)
        deleteChoiceTotalPrice.getItems().addAll("=", "<", ">");
    }

    @FXML
    private ChoiceBox deleteChoiceTotalPrice;
    @FXML
    public TextField box_deleteID;
    @FXML
    private TextField box_employeeID;
    @FXML
    private Button button_AddNew;
    @FXML
    private DatePicker box_deleteOrderDate;
    @FXML
    private DatePicker box_deleteReturnDate;
    @FXML
    private TextField box_deleteEmployeeID;
    @FXML
    private TextField box_deleteClientPhone;
    @FXML
    private TextField box_deleteClientName;
    @FXML
    private Button button_Delete;
    @FXML
    private TextField box_deleteTotalPrice;
    @FXML
    private TextField box_clientName;
    @FXML
    private TextField box_clientPhone;
    @FXML
    private TextField box_totalPrice;
    @FXML
    private TextField box_filmsID;
    @FXML
    private DatePicker box_orderDate;
    @FXML
    private DatePicker box_returnDate;

    public void click_AddNewAgreement() {
        if (checkInputAddData()){
            parent.addCortege(box_clientName.getText(),
                    box_clientPhone.getText(),
                    box_totalPrice.getText(),
                    box_filmsID.getText(),
                    box_orderDate.getValue(),
                    box_returnDate.getValue(),
                    box_employeeID.getText(),
                    "agreement");

            closeWindow(button_AddNew);
        }
        else {
            showAlert("Некорректный ввод");
        }
    }
    public void click_DeleteAgreement() {
        if (checkInputDeleteData()){
            String orderDate = (box_deleteOrderDate.getValue() != null) ? box_deleteOrderDate.getValue().toString() : "";
            String returnDate = (box_deleteReturnDate.getValue() != null) ? box_deleteReturnDate.getValue().toString() : "";
            parent.deleteCortege(
                    box_deleteID.getText(),
                    box_deleteClientName.getText(),
                    box_deleteClientPhone.getText(),
                    box_deleteTotalPrice.getText(),
                    deleteChoiceTotalPrice.getValue().toString(),
                    box_deleteEmployeeID.getText(),
                    orderDate,
                    returnDate,
                    "agreement"
            );

            closeWindow(button_Delete);
        }
        else {
            showAlert("Некорректный ввод");
        }
    }

    private boolean checkInputAddData(){
        return !box_clientName.getText().isEmpty()
                && !box_clientPhone.getText().isEmpty()
                && checkIntegerSyntax(box_totalPrice.getText())
                && checkFilmlsIDSyntax()
                && checkIntegerSyntax(box_employeeID.getText())
                && box_orderDate.getValue() != null
                && box_returnDate.getValue()!= null;
    }
    private boolean checkInputDeleteData(){
        return !box_deleteClientName.getText().isEmpty()
                || !box_deleteClientPhone.getText().isEmpty()
                || !box_deleteTotalPrice.getText().isEmpty()
                || box_deleteOrderDate.getValue() != null
                || box_deleteReturnDate.getValue() != null
                || (!box_deleteEmployeeID.getText().isEmpty() && checkIntegerSyntax(box_deleteEmployeeID.getText()))
                || (!box_deleteID.getText().isEmpty() && checkIntegerSyntax(box_deleteID.getText()))
                ;
    }
    private boolean checkFilmlsIDSyntax(){
        String[] temp = box_filmsID.getText().split(",");

        for (String filmID : temp){
            if (!checkIntegerSyntax(filmID))
                return false;
        }
        return true;
    }
    private boolean checkIntegerSyntax(String str){
        try
        {
            if (str == "") return false;

            Integer.parseInt(str);
        }
        catch (Exception e){
            return false;
        }
        return true;
    }
}
