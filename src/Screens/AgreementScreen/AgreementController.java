package Screens.AgreementScreen;

import DBController.DBController;
import Screens.AbstractController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * Created by Andrey on 06.11.2016.
 */
public class AgreementController extends AbstractController{

    @FXML
    private TextField box_clientName;
    @FXML
    private TextField box_clientPhone;
    @FXML
    private TextField box_totalPrice;
    @FXML
    private TextField box_filmsID;
    @FXML
    private TextField box_orderDate;
    @FXML
    private TextField box_returnDate;

    public void click_AddNewAgreement(MouseEvent mouseEvent) {
        showAlert("Test");
    }
}
