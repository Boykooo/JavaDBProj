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

        if (box_searchPriceChoice != null)
            box_searchPriceChoice.getItems().addAll("=", "<", ">");
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

    //Поиск
    @FXML
    private DatePicker box_searchOrderDate;
    @FXML
    private DatePicker box_searchReturnDate;
    @FXML
    private TextField box_searchEmployeeID;
    @FXML
    private ChoiceBox box_searchPriceChoice;
    @FXML
    private TextField box_searchName;
    @FXML
    private TextField box_searchPhone;
    @FXML
    private TextField box_searchPrice;
    @FXML
    private Button searchButton;

    //Доход
    @FXML
    private Button button_incomeButton;
    @FXML
    private DatePicker box_incomeStart;
    @FXML
    private DatePicker box_incomeEnd;

    @FXML
    public Button specificButton;
    @FXML
    public TextField box_specificPhone;

    @FXML
    private void click_AddNewAgreement() {
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

    @FXML
    private void click_DeleteAgreement() {
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

    @FXML
    private void click_UpdateButton() {
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
            showAlert("Некорректный ввод");
        }
    }

    @FXML
    private void click_SpecificButton() {
        if (!box_specificPhone.getText().isEmpty()) {

            parent.showSpecificAgreement(box_specificPhone.getText());

            closeWindow(specificButton);

        } else {
            showAlert("Введите телефон");
        }
    }

    @FXML
    private void click_searchButton() {
        if (checkSearchData()) {
            String orderDate = (box_searchOrderDate.getValue() != null) ? box_searchOrderDate.getValue().toString() : "";
            String returnDate = (box_searchReturnDate.getValue() != null) ? box_searchReturnDate.getValue().toString() : "";
            parent.searchData(
                    box_searchName.getText(),
                    box_searchPhone.getText(),
                    box_searchPrice.getText(),
                    box_searchPriceChoice.getValue(),
                    box_searchEmployeeID.getText(),
                    orderDate,
                    returnDate,
                    "Agreement"
            );

            closeWindow(searchButton);
        } else {
            showAlert("Хотя бы одно поле должно быть заполнено");
        }
    }

    public void click_incomeButton() {
        if (checkIncomeData()) {
            parent.income(box_incomeStart.getValue().toString(), box_incomeEnd.getValue().toString());

            closeWindow(button_incomeButton);
        } else {
            showAlert("Все поля должны быть заполнены");
        }
    }

    @Override
    public void setTextField(Object... objects) {
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

    private boolean checkSearchData() {
        return !box_searchName.getText().isEmpty() ||
                !box_searchPhone.getText().isEmpty() ||
                !box_searchPrice.getText().isEmpty() ||
                !box_searchEmployeeID.getText().isEmpty() ||
                box_searchOrderDate.getValue() != null ||
                box_searchReturnDate.getValue() != null;
    }

    private boolean checkIncomeData() {
        return box_incomeEnd.getValue() != null && box_incomeStart != null;
    }


}

















