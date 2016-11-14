package Screens.CassetteScreen;

import Screens.AbstractController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
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
        if (box_deleteYearChoice != null) {
            box_deleteYearChoice.getItems().addAll("=", "<", ">");
            box_deleteYearChoice.setValue("=");
        }
        if (box_deleteExistChoice != null) {
            box_deleteExistChoice.getItems().addAll("true", "false", "null");
            box_deleteExistChoice.setValue("null");
        }

        if (choicebox_updateExist != null) {
            choicebox_updateExist.getItems().addAll("true", "false");
        }
    }

    //Добавление
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

    //Удаление
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


    //Редактирование
    @FXML
    private ChoiceBox choicebox_updateExist;
    @FXML
    private TextField box_updateYear;
    @FXML
    private TextField box_updatePrice;
    @FXML
    private TextField box_updateDirector;
    @FXML
    private TextField box_updateName;
    @FXML
    private TextField box_updateGenre;
    @FXML
    private Button updateButton;
    @FXML
    private Label label_ID;


    public void click_AddButton() {
        if (checkAddData()) {
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
        } else {
            showAlert("Некорректный ввод");
        }
    }

    public void click_DeleteButton() {
        if (checkDeleteData()) {
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
        } else {
            showAlert("Некорректный ввод");
        }
    }

    public void click_updateButton() {
        if (checkUpdateData()) {

            parent.updateCortege(
                    box_updateGenre.getText(),
                    box_updateName.getText(),
                    box_updateDirector.getText(),
                    box_updatePrice.getText(),
                    choicebox_updateExist.getValue(),
                    box_updateYear.getText(),
                    label_ID.getText(),
                    "cassette"
            );

            closeWindow(updateButton);
        } else {
            showAlert("Некорректный ввод");
        }

    }

    @Override
    public void setTextField(Object... obj) {
        box_updateGenre.setText(obj[0].toString());
        box_updateName.setText(obj[1].toString());
        box_updateDirector.setText(obj[2].toString());
        box_updatePrice.setText(obj[3].toString());
        choicebox_updateExist.setValue(obj[4].toString());
        box_updateYear.setText(obj[5].toString());
        label_ID.setText(obj[6].toString());
    }

    //Проверки на правильность ввода
    private boolean checkAddData() {
        return !box_Name.getText().isEmpty()
                && (!box_Price.getText().isEmpty() && checkIntegerSyntax(box_Price.getText()))
                && ((!box_Year.getText().isEmpty() && checkIntegerSyntax(box_Year.getText())) || box_Year.getText().isEmpty())
                ;
    }

    private boolean checkDeleteData() {

        return (!box_deleteID.getText().isEmpty() && checkIntegerSyntax(box_deleteID.getText()))
                || !box_deleteGenre.getText().isEmpty()
                || !box_deleteName.getText().isEmpty()
                || !box_deleteDirector.getText().isEmpty()
                || (!box_deletePrice.getText().isEmpty() && checkIntegerSyntax(box_deletePrice.getText()))
                || (!box_deleteYear.getText().isEmpty() && checkIntegerSyntax(box_deleteYear.getText()))
                || box_deleteExistChoice.getValue() != "null";
    }

    private boolean checkUpdateData() {

        return !box_updateName.getText().isEmpty()
                && !box_updatePrice.getText().isEmpty();
    }

    //Вспомогательные ф-ии
    private boolean checkIntegerSyntax(String text) {
        try {
            Integer.parseInt(text);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    private String getExistChoiceString() {
        return box_deleteExistChoice.getValue() == "null" ? "" : (String) box_deleteExistChoice.getValue();
    }


}






































