package code;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.zip.DataFormatException;

public class CreateUserScreenController implements Initializable {

    private Main main;
    public userData clientData = new userData();

    @FXML private TextField textfield_usernameField;

    @FXML private Label label_invalidusername;

    @FXML
    void button_OnSubmitUserName() throws IOException {

        clientData.setUsername(textfield_usernameField.getText());

        try {
            if (textfield_usernameField.getText().length() > 16) //TODO: Find out what the max username length can be
                throw new IndexOutOfBoundsException();

            if (!textfield_usernameField.getText().matches("[A-Za-z0-9!\"#$%&'()*+,.\\/:;<=>?@[\\]^_`{|}~-]+$]+")) //TODO: Find out if more chars should be banned
                throw new DataFormatException();
            System.out.println("[Update] Username " + textfield_usernameField.getText() + " was selected");
            System.out.println("[Transition] Submit Username Clicked ... Entering Weapon Select Scene");
            main.showWeaponSelectScreenScene(clientData);

        } catch (IndexOutOfBoundsException ex) {
            System.out.println("[ERROR] ... Username too long!");
            label_invalidusername.setVisible(true);
            label_invalidusername.setText("[ERROR] ... Username too long!");
            RemoveExceptionLabel();
        } catch (DataFormatException ex) {
            System.out.println("[ERROR] ... Illegal Character Found!");
            label_invalidusername.setVisible(true);
            label_invalidusername.setText("[ERROR] ... Illegal Character Found!");
            RemoveExceptionLabel();
        }
    }

    //TODO: Fix Multiple-Error timer malfunction (or just remove completely)
    private void RemoveExceptionLabel() {
        Timeline RemoveLabel_Timer = new Timeline( new KeyFrame(
                Duration.millis(4000),
                ae -> RemoveLabel_Timer()));
        RemoveLabel_Timer.play();

        FadeTransition fade = new FadeTransition(Duration.seconds(4), label_invalidusername);
        fade.setFromValue(1.0);
        fade.setToValue(0.2);
        fade.setCycleCount(4);
        fade.play();
    }

    private Object RemoveLabel_Timer() {
        label_invalidusername.setVisible(false);
        return null;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {}
}