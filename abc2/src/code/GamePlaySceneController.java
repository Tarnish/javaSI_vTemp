package code;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class GamePlaySceneController extends GameInstructionsScreenController {

    private Main main;

    @FXML private TextArea textarea_username;

    @FXML private TextArea textarea_score;

    @FXML public TextArea textarea_lives;

    @FXML public Rectangle rectangle_gameZone;

    @FXML public Rectangle rectangle_protagonist; //test public

    static int enemyRemaining = 33;

    //TODO: Add fixed positions in data structure?                  -->
    //TODO: Add enemy movement                                      --> Fixed? (Needs Exact Pos Adjusting/Testing)
    //TODO: Add Protagonist bounds                                  --> Fixed? (Needs Exact Pos Adjusting/Testing)
    //TODO: Fix Counter & fireID                                    --> Fixed?
    //TODO: Fix ShootingMovementTimer                               -->
    //TODO: Destroy Timers/Threads                                  -->
    //TODO: Add GameEnd Event                                       -->
    //TODO: Lower bullet index array? (since loop done)             -->
    //TODO: Add Adjustable Enemy Movement Speed (based on Y-axis)   -->
    //TODO: Allow enemies to shoot                                  -->

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

    public void movePlayerUp(ArrayList<ImageView> index, int id, ArrayList<ImageView> ivE, double[] counter) {
        ShootingMovementTimer(index, id, ivE, counter);
    }

    public void setGlobalCounter(double[] counter) {
        for (int i = 0; i < 50; i++) {
            counter[i] = 0;
        }
    }

    private void ShootingMovementTimer(ArrayList<ImageView> index, int id, ArrayList<ImageView> ivE, double[] counter) {

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    BoundChecker(index, id, ivE, timer);
                } catch (IOException e) {
                    e.printStackTrace();
                }

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

    private void BoundChecker(ArrayList<ImageView> index, int id, ArrayList<ImageView> ivE, Timer timer) throws IOException { //id = bullet id
        for (int i = 0; i < 33; i++) {
            if (index.get(id).getBoundsInParent().intersects(ivE.get(i).getBoundsInParent()) && ivE.get(i).isVisible()) {
                clientData.currentScore += 100;
                textarea_score.setText(Integer.toString(clientData.currentScore));
                enemyRemaining--;

                index.get(id).setVisible(false);
                ivE.get(i).setVisible(false);

                if (enemyRemaining == 0) {
                    System.out.println("YOU DESTROYED THEM ALL");
                }

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