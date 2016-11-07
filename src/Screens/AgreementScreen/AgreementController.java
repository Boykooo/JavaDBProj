package Screens.AgreementScreen;

import DBController.DBController;
import javafx.scene.control.Alert;

/**
 * Created by Andrey on 06.11.2016.
 */
public class AgreementController {

    public void initialize() {
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
    }

    private Alert alert;


}
