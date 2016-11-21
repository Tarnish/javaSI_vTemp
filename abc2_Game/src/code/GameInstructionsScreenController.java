package code;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

public class GameInstructionsScreenController extends WeaponSelectScreenController {

    //TODO: Scene change with [ENTER] instead of button to increase Instructions Image?

    private Main main;

    @FXML private Label label_username;

    @FXML
    void button_OnGotoPlay() throws IOException {

        clientData.username = label_username.getText();
        main.showGamePlayScreenScene(clientData);
    }

    void setclientData(userData clientData) {
        this.clientData = clientData;
    }
}