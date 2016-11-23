package code;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import code.CreateUserScreenController;
import javafx.scene.control.Label;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import code.CreateUserScreenController;
import code.userData;

public class WeaponSelectScreenController extends CreateUserScreenController {

    private Main main;

    @FXML public Label label_username;

    @FXML private CheckBox checkbox_pistol;

    @FXML private CheckBox checkbox_boltaction;

    @FXML private CheckBox checkbox_ak47;

    @FXML
    void button_OnGotoInstructions() throws IOException {
        if (!checkbox_pistol.isSelected() && !checkbox_boltaction.isSelected() && !checkbox_ak47.isSelected())
            return;

        clientData.username = label_username.getText();
        System.out.println("[Update] Weapon " + clientData.getWeaponTypeName(clientData.weapon_selected) + " was selected");
        System.out.println("[Transition] Show Instructions Clicked ... Entering Instructions Screen Scene");
        main.showInstructionScreenScene(clientData);
    }

    @FXML
    void handle_checkbox_pistol() {
        if (checkbox_pistol.isSelected()) {
            clientData.weapon_selected = 0;
            checkbox_boltaction.setSelected(false);
            checkbox_ak47.setSelected(false);
        }
    }

    @FXML
    void handle_checkbox_boltaction() {
        if (checkbox_boltaction.isSelected()) {
            clientData.weapon_selected = 1;
            checkbox_pistol.setSelected(false);
            checkbox_ak47.setSelected(false);
        }
    }

    @FXML
    void handle_checkbox_ak47() {
        if (checkbox_ak47.isSelected()) {
            clientData.weapon_selected = 2;
            checkbox_pistol.setSelected(false);
            checkbox_boltaction.setSelected(false);
        }
    }

    public void setTextToLabel(String userName) {
        label_username.setText(userName);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {}

}

