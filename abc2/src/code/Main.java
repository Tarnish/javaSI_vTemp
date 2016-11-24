package code;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import java.util.*;

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
        BorderPane mainWindow = loader.load();
        currentLayout.setCenter(mainWindow);
    }

    public static void showCreateUserScreenScene() throws IOException { //public static void -> switch scene
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

    static int fireID = -1;
    static boolean canShoot = true;
    private static int enemyDirection = 0; //0 right | 1 left
    public static double[] counter = new double [50]; //bullet movement increment

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
        final Image bullet = new Image("images\\ammo_pistol.png");
        final Image img_Enemy = new Image("images\\teacher_cyan_1.png");
        EventHandler<KeyEvent> keyPressedEventHandler;

        //TOTAL TEST//
        ArrayList<ImageView> ivTest= new ArrayList<ImageView>();
        ArrayList<ImageView> ivE= new ArrayList<ImageView>();

        for (int i = 0; i < 50; i++) { // Z:50
            ImageView imageView = new ImageView();
            imageView.setImage(bullet);
            imageView.setRotate(45);
            imageView.setFitWidth(50);
            //imageView.setFitHeight(50);
            ivTest.add(imageView);
        }

        for (int i = 0; i < 33; i++) {
            ImageView imageViewEnemy = new ImageView();
            imageViewEnemy.setImage(img_Enemy);
            imageViewEnemy.setFitWidth(50);
            //imageViewEnemy.setFitHeight(50);
            ivE.add(imageViewEnemy);

            if (i < 11) {                                          //Start X = 130 | Y = 130
                ivE.get(i).setLayoutX(130 + (i * 50) + (i * 10)); //ivE.get(i).setLayoutX(130 + (i * 50));
                ivE.get(i).setLayoutY(130);
                mainWindow.getChildren().add(ivE.get(i));
            }
            else if (i > 10 && i < 22) {
                ivE.get(i).setLayoutX(130 + ((i - 11) * 50)  + ((i - 11) * 10)); //ivE.get(i).setLayoutX(130 + ((i - 11) * 50));
                ivE.get(i).setLayoutY(230);
                mainWindow.getChildren().add(ivE.get(i));
            }
            else if (i >= 22 && i < 33) {
                ivE.get(i).setLayoutX(130 + ((i - 22) * 50)  + ((i - 22) * 10)); //ivE.get(i).setLayoutX(130 + ((i - 22) * 50));
                ivE.get(i).setLayoutY(330);
                mainWindow.getChildren().add(ivE.get(i));
            }
        } //Create Perm Timer till GV

        EnemyMovement(mainWindow, ivE, controller);



        controller.setGlobalCounter(counter);
        //mainWindow.getChildren().addAll(ivTest);          //---> Add all
        //mainWindow.getChildren().addAll(ivTest);          //mainWindow.getChildren().addAll(ivTest.get(10)); ---> Add single 10 index
        //mainWindow.getChildren().removeAll(ivTest);       //---> Remove all nodes of this

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
                        if (keyCode.name().equals("UP") && canShoot) {
                            System.out.println("PRESSED UP");

                            canShoot = false;
                            ReloadTimer();

                            fireID += 1;

                            ivTest.get(fireID).setLayoutX(controller.rectangle_protagonist.getLayoutX()); //3
                            ivTest.get(fireID).setLayoutY(controller.rectangle_protagonist.getLayoutY() - 80); //3

                            ivTest.get(fireID).setVisible(true); //3

                            BulletIndexRemoverTimer(mainWindow, fireID, ivTest); // fireID = 3 when testing

                            mainWindow.getChildren().add(ivTest.get(fireID));
                            controller.movePlayerUp(ivTest, fireID, ivE, counter);
                            //TOTAL TEST END
                        }
                    }
                }
            }
        };

        scene.addEventFilter(KeyEvent.KEY_PRESSED, keyPressedEventHandler);
        scene.addEventFilter(KeyEvent.KEY_RELEASED, keyReleasedEventHandler);
    }

    private static void EnemyMovement(BorderPane mainWindow, ArrayList<ImageView> ivE, GamePlaySceneController controller) {
        Timer enemyTimer = new Timer();
        enemyTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                for (int index = 0; index < 33; index++) {
                    if (ivE.get(index).getLayoutY() >= 469 && ivE.get(index).isVisible()) { //589
                        System.out.println("Enemy Reached you; RIP");
                        controller.clientData.lives -= 1;
                        controller.textarea_lives.setText(Integer.toString(controller.clientData.lives));
                        return;
                    }
                }

                if (enemyDirection == 0) {
                    for (int index = 0; index < 33; index++) {
                        if (controller.rectangle_gameZone.getWidth() <= ivE.get(index).getLayoutX() && ivE.get(index).isVisible()) {
                            System.out.println("TOO FAR [RIGHT] ... STOPPING & ... LOWERING TO NEXT ROW");
                            enemyDirection = 1;
                            for (int moveAllDownIndex = 0; moveAllDownIndex < 33; moveAllDownIndex++) {
                                ivE.get(moveAllDownIndex).setLayoutY(ivE.get(moveAllDownIndex).getLayoutY() + 10);
                            }
                            return;
                        }
                    }
                    //System.out.println("RUN_1");
                    for (int index = 0; index < 33; index++) {
                        if (ivE.get(index).isVisible()) {
                            ivE.get(index).setLayoutX(ivE.get(index).getLayoutX() + 10);
                        }
                    }
                }
                else if (enemyDirection == 1) {
                    for (int index = 0; index < 33; index++) {
                        if (ivE.get(index).getLayoutX() <= 10 && ivE.get(index).isVisible()) {
                            System.out.println("TOO FAR [LEFT] ... STOPPING & ... LOWERING TO NEXT ROW");
                            enemyDirection = 0;
                            for (int moveAllDownIndex = 0; moveAllDownIndex < 33; moveAllDownIndex++) {
                                ivE.get(moveAllDownIndex).setLayoutY(ivE.get(moveAllDownIndex).getLayoutY() + 10);
                            }
                            return;
                        }
                    }
                    //System.out.println("RUN_2");
                    for (int index = 0; index < 33; index++) {
                        if (ivE.get(index).isVisible()) {
                            ivE.get(index).setLayoutX(ivE.get(index).getLayoutX() - 10);
                        }
                    }
                }
            }
        }, 0, 500); //500 (Slow) | 200 (Fast)
    }

    private static void BulletIndexRemoverTimer(BorderPane mainWindow, int index, ArrayList<ImageView> ivTest) {
        Timeline RemoveLabel_Timer = new Timeline( new KeyFrame(
                Duration.seconds(2), //2 seconds -> 5s
                ae -> BulletIndexRemover(mainWindow, index, ivTest))); //index = 3 for linear testing
        RemoveLabel_Timer.play();
    }

    private static Object BulletIndexRemover(BorderPane mainWindow, int index, ArrayList<ImageView> ivTest) {
        System.out.println("Removed Bullet Index # " + index);
        ivTest.get(index).setVisible(false);
        counter[index] = 0;
        //mainWindow.getChildren().removeAll(ivTest.get(index));
        mainWindow.getChildren().remove(ivTest.get(index));

        if (fireID >= 49) {
            fireID = -1;
        }
        return null;
    }

    private static void ReloadTimer() {
        Timeline xReload = new Timeline( new KeyFrame(
                Duration.millis(900),
                ae -> xReloadComplete()));
        xReload.play();
    }

    private static void xReloadComplete() {
        System.out.println("RELOAD COMPLETE");
        canShoot = true;
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
