package Screens.AgreementScreen;

import Screens.AbstractController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;


public class AgreementController extends AbstractController{

    @FXML
    public TextField box_employeeID;
    @FXML
    public Button button_AddNew;
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
            showAlert("Все поля должны быть заполнены");
        }
    }

    private boolean checkInputAddData(){
        return !box_clientName.getText().isEmpty()
                && !box_clientPhone.getText().isEmpty()
                && !box_totalPrice.getText().isEmpty()
                && checkFilmlsIDSyntax()
                && checkIDEmployeeSyntax()
                && box_orderDate.getValue() != null
                && box_returnDate.getValue()!= null;
    }
    private boolean checkIDEmployeeSyntax(){
        try
        {
            int k = Integer.parseInt(box_employeeID.getText());
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
    private boolean checkFilmlsIDSyntax(){
        String[] temp = box_filmsID.getText().split(",");

        for (String filmID : temp){
            try
            {
                if (filmID == "") return false;

                Integer.parseInt(filmID);
            }
            catch (Exception e){
                return false;
            }
        }
        return true;
    }
}
