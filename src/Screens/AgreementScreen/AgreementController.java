package Screens.AgreementScreen;

import Screens.AbstractController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;


public class AgreementController extends AbstractController implements Initializable {


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (deleteChoiceTotalPrice != null)
            deleteChoiceTotalPrice.getItems().addAll("=", "<", ">");
    }

    //Добавление
    @FXML
    private TextField box_employeeID;
    @FXML
    private Button button_AddNew;
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

    //Удаление
    @FXML
    private ChoiceBox deleteChoiceTotalPrice;
    @FXML
    public TextField box_deleteID;
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

    //Редактирование
    @FXML
    private Button updateButton;
    @FXML
    private DatePicker box_updateOrderDate;
    @FXML
    private DatePicker box_updateLastReturnDate;
    @FXML
    private TextField box_updateEmployeeID;
    @FXML
    private TextField box_updateTotalPrice;
    @FXML
    private TextField box_updateClientPhone;
    @FXML
    private TextField box_updateClientName;
    @FXML
    private Label label_ID;


    public void click_AddNewAgreement() {
        if (checkInputAddData()) {
            parent.addCortege(box_clientName.getText(),
                    box_clientPhone.getText(),
                    box_totalPrice.getText(),
                    box_filmsID.getText(),
                    box_orderDate.getValue(),
                    box_returnDate.getValue(),
                    box_employeeID.getText(),
                    "agreement");

            closeWindow(button_AddNew);
        } else {
            showAlert("Некорректный ввод");
        }
    }

    public void click_DeleteAgreement() {
        if (checkInputDeleteData()) {
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
        } else {
            showAlert("Некорректный ввод");
        }
    }

    public void click_UpdateButton() {
        if (checkUpdateData()) {

            parent.updateCortege(
                    box_updateClientName.getText(),
                    box_updateClientPhone.getText(),
                    box_updateTotalPrice.getText(),
                    box_updateOrderDate.getValue(),
                    box_updateEmployeeID.getText(),
                    box_updateLastReturnDate.getValue(),
                    label_ID.getText(),
                    "agreement"
                    );

            closeWindow(updateButton);
        } else {

        }
    }

    @Override
    public void setLabel(Object... objects) {
        label_ID.setText(objects[0].toString());
        box_updateClientName.setText(objects[1].toString());
        box_updateClientPhone.setText(objects[2].toString());
        box_updateTotalPrice.setText(objects[3].toString());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(objects[4].toString(), formatter);
        box_updateOrderDate.setValue(localDate);

        localDate = LocalDate.parse(objects[5].toString(), formatter);
        box_updateLastReturnDate.setValue(localDate);

        box_updateEmployeeID.setText(objects[6].toString());
    }

    private boolean checkInputAddData() {
        return !box_clientName.getText().isEmpty()
                && !box_clientPhone.getText().isEmpty()
                && checkIntegerSyntax(box_totalPrice.getText())
                && checkFilmlsIDSyntax()
                && checkIntegerSyntax(box_employeeID.getText())
                && box_orderDate.getValue() != null
                && box_returnDate.getValue() != null;
    }

    private boolean checkInputDeleteData() {
        return !box_deleteClientName.getText().isEmpty()
                || !box_deleteClientPhone.getText().isEmpty()
                || !box_deleteTotalPrice.getText().isEmpty()
                || box_deleteOrderDate.getValue() != null
                || box_deleteReturnDate.getValue() != null
                || (!box_deleteEmployeeID.getText().isEmpty() && checkIntegerSyntax(box_deleteEmployeeID.getText()))
                || (!box_deleteID.getText().isEmpty() && checkIntegerSyntax(box_deleteID.getText()))
                ;
    }

    private boolean checkFilmlsIDSyntax() {
        String[] temp = box_filmsID.getText().split(",");

        for (String filmID : temp) {
            if (!checkIntegerSyntax(filmID))
                return false;
        }
        return true;
    }

    private boolean checkIntegerSyntax(String str) {
        try {
            if (str == "") return false;

            Integer.parseInt(str);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private boolean checkUpdateData() {
        return !box_updateClientPhone.getText().isEmpty()
                && !box_updateClientName.getText().isEmpty()
                && !box_updateTotalPrice.getText().isEmpty()
                && box_updateOrderDate.getValue() != null
                && box_updateLastReturnDate.getValue() != null
                && !box_updateEmployeeID.getText().isEmpty();
    }


}
