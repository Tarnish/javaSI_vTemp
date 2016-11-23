package code;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class GamePlaySceneController extends GameInstructionsScreenController {

    @FXML private TextArea textarea_username;

    @FXML private TextArea textarea_score;

    @FXML public TextArea textarea_lives;

    @FXML public Rectangle rectangle_gameZone;

    @FXML private Rectangle rectangle_en1; //testing block

    @FXML public Rectangle rectangle_protagonist; //test public

    @FXML private ImageView imgv_1;

    private int enemyDirection = 0; //0 right | 1 left
    final Image bullet = new Image("images\\ammo_pistol.png");


    //TODO: Add fixed positions in data structure?
    //TODO: Change enemy movement
    //TODO: Add Protagonist bounds      --> Adjust Protagonist Bounds
    //TODO: Fix Counter & fireID
    //TODO: Fix ShootingMovementTimer
    //TODO: Destroy Timers/Threads
    //TODO: Add GameEnd Event

    /*@FXML
    void recMouseMove() {
        //System.out.println("MOVE!"); //Mouse Move Detection


        if (rectangle_en1.getLayoutY() >= 529) { //589
            System.out.println("Enemy Reached you; RIP");
            clientData.lives -= 1;
            textarea_lives.setText(Integer.toString(clientData.lives));
            return;
        }

        if (rectangle_gameZone.getWidth() <= rectangle_en1.getLayoutX() && enemyDirection == 0) { // H = 589 | W = 770 | GS
            System.out.println("TOO FAR [RIGHT] ... STOPPING & ... LOWERING TO NEXT ROW");
            enemyDirection = 1;
            rectangle_en1.setLayoutY(rectangle_en1.getLayoutY() + 10);
            return;
        }
        if (rectangle_en1.getLayoutX() <= 10 && enemyDirection == 1) { // H = 589 | W = 770 | GS
            System.out.println("TOO FAR [LEFT] ... STOPPING & ... LOWERING TO NEXT ROW");
            enemyDirection = 0;
            rectangle_en1.setLayoutY(rectangle_en1.getLayoutY() + 10);
            return;
        }

        if (enemyDirection == 0) {
            //System.out.println("<X> X = " + rectangle_gameZone.getWidth());
            //System.out.println("<PRE> X = " + rectangle_en1.getLayoutX());
            rectangle_en1.setLayoutX(rectangle_en1.getLayoutX() + 10);
            //System.out.println("<POST> X = " + rectangle_en1.getLayoutX());
        }
        else if (enemyDirection == 1) {
            //System.out.println("<X> X = " + rectangle_gameZone.getWidth());
            //System.out.println("<PRE> X = " + rectangle_en1.getLayoutX());
            rectangle_en1.setLayoutX(rectangle_en1.getLayoutX() - 10);
            //System.out.println("<POST> X = " + rectangle_en1.getLayoutX());
        }

    }*/

    @FXML
    void movePlayerPressed() {
        if (rectangle_protagonist.getLayoutX() >= (rectangle_gameZone.getWidth() - 80))  { // 80 = Width of Protagonist
            System.out.println("[RIGHT] LIMIT REACHED");
            return;
        }
        rectangle_protagonist.setLayoutX(rectangle_protagonist.getLayoutX() + 10);
    }

    @FXML
    void movePlayerReleased() {

        if (rectangle_protagonist.getLayoutX() <= 90) { // 10 + 80
            System.out.println("[LEFT] LIMIT REACHED");
            return;
        }
        rectangle_protagonist.setLayoutX(rectangle_protagonist.getLayoutX() - 10);
    }

    public void setTextToTextArea_Username(String userName) {
        textarea_username.setText(userName);
    }

    public void setTextToTextArea_Lives(int lives) {
        clientData.lives = lives;
        textarea_lives.setText(Integer.toString(clientData.lives));
    }


    public void setWeaponID(int num) {
        clientData.weapon_selected = num;
    }

    public void movePlayerUp(ArrayList<ImageView> index, int id, ArrayList<ImageView> ivE) {
        ShootingMovementTimer(index, id, ivE);
    }

    //double counter = 1; //temp test global
    double counter[] = new double [50];

    public void setGlobalCounter() {
        for (int i = 0; i < 50; i++) {
            counter[i] = 0;
        }
    }

    private void ShootingMovementTimer(ArrayList<ImageView> index, int id, ArrayList<ImageView> ivE) {

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                BoundChecker(index, id, ivE, timer);

                if (index.get(id).getLayoutY() < 50 || !index.get(id).isVisible()) {
                    index.get(id).setVisible(false);
                    ////counter[id] = 0;
                    timer.cancel();
                }
                index.get(id).setLayoutY(522 - counter[id]); // index.get(id).setLayoutY(rectangle_protagonist.getLayoutY() - counter[id]);
                counter[id] += 10; //1
            }
        }, 0, 50); // < 160 or not smooth 16 (if set lower timer (e.g. 15) checking will go too fast and lead to potential errors)
    }

    private void BoundChecker(ArrayList<ImageView> index, int id, ArrayList<ImageView> ivE, Timer timer) { //id = bullet id
        for (int i = 0; i < 33; i++) {
            if (index.get(id).getBoundsInParent().intersects(ivE.get(i).getBoundsInParent()) && ivE.get(i).isVisible()) {
                index.get(id).setVisible(false);
                ivE.get(i).setVisible(false);
                timer.cancel();
            }
        }
    }

    public enum ScoreOperation {
        ADD,
        SUBTRACT,
        SET
    }

    public void changeScore(int Score, ScoreOperation ScoreTYPE) {
        switch (ScoreTYPE) {
            case ADD:       clientData.currentScore += Score;
                            textarea_score.setText(Integer.toString(Score));
                            break;

            case SUBTRACT:  clientData.currentScore -= Score;
                            textarea_score.setText(Integer.toString(Score));
                            break;

            case SET:       clientData.currentScore =  Score;
                            textarea_score.setText(Integer.toString(Score));
                            break;
        }
    }

}