package Screens;

import Interfaces.IMainController;
import javafx.scene.control.Alert;

public abstract class AbstractController {
    public AbstractController() {
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
    }

    private Alert alert;
    private IMainController parent;

    public void showAlert(String message){
        alert.setContentText(message);
        alert.show();
    }
    public void setParent(IMainController parent){
        this.parent = parent;
    }
}
