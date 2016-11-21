package code;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.BitSet;

public class Main extends Application {

    private static Stage currentStage;
    private static BorderPane currentLayout;
    private static BitSet keyboardBitSet = new BitSet();

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage currentStage) throws Exception {

        this.currentStage = currentStage;
        this.currentStage.setTitle("Group 1 Java Game");

        //TEMP IMG
        final Image earth = new Image("images\\Earth.png");
        this.currentStage.getIcons().add(earth);
        //

        showInitialScreen();
        showWelcomeScene();
    }

    public void showInitialScreen() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("../fxml_css/InitialScreen.fxml"));
        currentLayout = loader.load();
        Scene scene = new Scene(currentLayout);
        currentStage.setScene(scene);
        currentStage.show();
    }

    public static void showWelcomeScene() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("../fxml_css/WelcomeScreen.fxml"));
        BorderPane electricalDep = loader.load();
        currentLayout.setCenter(electricalDep);
    }

    public static void showCreateUserScreenScene() throws IOException { //make sure its -> public static void -> to switch scene correctly
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("../fxml_css/CreateUserScreen.fxml"));
        BorderPane mainWindow = loader.load();

        Scene scene = new Scene(mainWindow);
        currentStage.setScene(scene);
        currentStage.show();
    }

    public static void showWeaponSelectScreenScene(userData clientData) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("../fxml_css/WeaponSelectScreen.fxml"));
        BorderPane mainWindow = loader.load();
        WeaponSelectScreenController controller = loader.getController();
        controller.setTextToLabel(clientData.username);

        Scene scene = new Scene(mainWindow);
        currentStage.setScene(scene);
        currentStage.show();
    }

    public static void showInstructionScreenScene(userData clientData) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("../fxml_css/GameInstructionsScreen.fxml"));
        BorderPane mainWindow = loader.load();
        GameInstructionsScreenController controller = loader.getController();
        controller.setTextToLabel(clientData.username);
        controller.setclientData(clientData);

        Scene scene = new Scene(mainWindow);
        currentStage.setScene(scene);
        currentStage.show();
    }

    public static void showGamePlayScreenScene(userData clientData) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("../fxml_css/GamePlayScreen.fxml"));
        BorderPane mainWindow = loader.load();
        GamePlaySceneController controller = loader.getController();

        //Test Assigning
        controller.setWeaponID(clientData.weapon_selected);
        controller.setTextToTextArea_Username(clientData.username);
        controller.changeScore(0, GamePlaySceneController.ScoreOperation.SET);
        controller.setTextToTextArea_Lives(3);

        Scene scene = new Scene(mainWindow);
        currentStage.setScene(scene);
        currentStage.show();

        EventHandler<KeyEvent> keyPressedEventHandler;
        keyPressedEventHandler = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                // register key down
                keyboardBitSet.set(event.getCode().ordinal(), true);
                //updateKeyboardStatus();

                for(KeyCode keyCode: KeyCode.values()) {
                    if(keyboardBitSet.get(keyCode.ordinal())) {
                        if (keyCode.name().equals("RIGHT")) {
                            controller.movePlayerPressed(); //0
                        }
                        if (keyCode.name().equals("LEFT")) {
                            controller.movePlayerReleased(); //1
                        }
                    }
                }
            }
        };

        scene.addEventFilter(KeyEvent.KEY_PRESSED, keyPressedEventHandler);
        scene.addEventFilter(KeyEvent.KEY_RELEASED, keyReleasedEventHandler);
    }

    /*
    Taken From SO
    */
    public static EventHandler<KeyEvent> keyPressedEventHandler = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {

            // register key down
            keyboardBitSet.set(event.getCode().ordinal(), true);
            //updateKeyboardStatus();

            for(KeyCode keyCode: KeyCode.values()) {

                if(keyboardBitSet.get(keyCode.ordinal())) {
                    System.out.println(keyCode.name());
                }
            }
        }
    };

    /*
    Taken From SO
    */
    private static EventHandler<KeyEvent> keyReleasedEventHandler = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {

            // register key up
            keyboardBitSet.set(event.getCode().ordinal(), false);
            //updateKeyboardStatus();
        }
    };

    /*
    Taken From SO
    */
    private static void updateKeyboardStatus() {

        StringBuilder sb = new StringBuilder();
        sb.append("Current key combination: ");

        int count = 0;
        for(KeyCode keyCode: KeyCode.values()) {

            if(keyboardBitSet.get(keyCode.ordinal())) {

                if(count > 0) {
                    sb.append(" ");
                }
                sb.append(keyCode.toString());
                count++;
            }
        }
        //System.out.println(sb.toString());
    }

}
