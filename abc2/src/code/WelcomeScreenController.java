package code;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import code.Main;

import java.io.IOException;

public class WelcomeScreenController {

    private Main main; //private

    @FXML
    void button_OnCreateCharacter(ActionEvent event) throws IOException {
        System.out.println("[Transition] OnCreateCharacter Clicked ... Entering CreateUserScreen");
        main.showCreateUserScreenScene();
    }

}



