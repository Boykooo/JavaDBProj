package Screens;

import Interfaces.IMainController;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public abstract class AbstractController {
    public AbstractController() {
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
    }

    private Alert alert;
    protected IMainController parent;

    public void showAlert(String message){
        alert.setContentText(message);
        alert.show();
    }
    public void setParent(IMainController parent){
        this.parent = parent;
    }

    public void setLabel(Object... objects) { }
    protected void closeWindow(Button btn){
        Stage stage = (Stage) btn.getScene().getWindow();
        stage.close();
    }
}
