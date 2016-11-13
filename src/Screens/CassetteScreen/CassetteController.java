package Screens.CassetteScreen;

import Screens.AbstractController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
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

//        if (idChoiceBox != null)
//            idChoiceBox.getItems().addAll("=", "<", ">");
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
    private boolean checkAddData(){
        return  !box_Name.getText().isEmpty()
                && (!box_Price.getText().isEmpty() && checkIntegerSyntax(box_Price.getText()))
                && ((!box_Year.getText().isEmpty() && checkIntegerSyntax(box_Year.getText())) || box_Year.getText().isEmpty())
                ;
    }
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
}






































