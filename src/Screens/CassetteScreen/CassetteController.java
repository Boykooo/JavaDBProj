package Screens.CassetteScreen;

import Screens.AbstractController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.text.ParseException;
import java.util.ResourceBundle;


public class CassetteController extends AbstractController implements Initializable {



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (box_Exist != null)
            box_Exist.setSelected(true);

        if (box_deleteIDChoice != null) {
            box_deleteIDChoice.getItems().addAll("=", "<", ">");
            box_deleteIDChoice.setValue("=");
        }
        if (box_deleteYearChoice != null){
            box_deleteYearChoice.getItems().addAll("=", "<", ">");
            box_deleteYearChoice.setValue("=");
        }
        if (box_deleteExistChoice != null){
            box_deleteExistChoice.getItems().addAll("true", "false", "null");
            box_deleteExistChoice.setValue("null");
        }
    }

    @FXML
    private CheckBox box_Exist;
    @FXML
    private TextField box_Price;
    @FXML
    private TextField box_Director;
    @FXML
    private TextField box_Genre;
    @FXML
    private TextField box_Name;
    @FXML
    private TextField box_Year;
    @FXML
    public Button addButton;

    @FXML
    private ChoiceBox box_deleteExistChoice;
    @FXML
    private ChoiceBox box_deleteIDChoice;
    @FXML
    private Button deleteButton;
    @FXML
    private TextField box_deleteID;
    @FXML
    private TextField box_deleteGenre;
    @FXML
    private TextField box_deleteName;
    @FXML
    private TextField box_deleteDirector;
    @FXML
    private TextField box_deletePrice;
    @FXML
    private TextField box_deleteYear;
    @FXML
    private ChoiceBox box_deleteYearChoice;

    public void click_AddButton() {
        if (checkAddData()){
            parent.addCortege(
                    box_Genre.getText(),
                    box_Name.getText(),
                    box_Director.getText(),
                    box_Price.getText(),
                    box_Exist.isSelected(),
                    box_Year.getText(),
                    "cassette"
            );

            closeWindow(addButton);
        }
        else
        {
            showAlert("Некорректный ввод");
        }
    }
    public void click_DeleteButton() {
        if (checkDeleteData())
        {
            parent.deleteCortege(
                    box_deleteID.getText(),
                    box_deleteIDChoice.getValue(),
                    box_deleteGenre.getText(),
                    box_deleteName.getText(),
                    box_deleteDirector.getText(),
                    box_deletePrice.getText(),
                    box_deleteYear.getText(),
                    box_deleteYearChoice.getValue(),
                    getExistChoiceString(),
                    "cassette"
            );
            closeWindow(deleteButton);
        }
        else
        {
            showAlert("Некорректный ввод");
        }
    }
    private boolean checkAddData(){
        return  !box_Name.getText().isEmpty()
                && (!box_Price.getText().isEmpty() && checkIntegerSyntax(box_Price.getText()))
                && ((!box_Year.getText().isEmpty() && checkIntegerSyntax(box_Year.getText())) || box_Year.getText().isEmpty())
                ;
    }
    private boolean checkDeleteData(){

        return (!box_deleteID.getText().isEmpty() && checkIntegerSyntax(box_deleteID.getText()))
                || !box_deleteGenre.getText().isEmpty()
                || !box_deleteName.getText().isEmpty()
                || !box_deleteDirector.getText().isEmpty()
                || (!box_deletePrice.getText().isEmpty() && checkIntegerSyntax(box_deletePrice.getText()))
                || (!box_deleteYear.getText().isEmpty() && checkIntegerSyntax(box_deleteYear.getText()))
                || box_deleteExistChoice.getValue() != "null";
    }

    //Вспомогательные ф-ии
    private boolean checkIntegerSyntax(String text){
        try {
            Integer.parseInt(text);
            return true;
        }
        catch (Exception e)
        {
            return false;
        }

    }
    private String getExistChoiceString(){
        return box_deleteExistChoice.getValue() == "null" ? "" : (String) box_deleteExistChoice.getValue();
    }

}






































