package code;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.shape.Rectangle;

public class GamePlaySceneController extends GameInstructionsScreenController {

    @FXML private TextArea textarea_username;

    @FXML private TextArea textarea_score;

    @FXML private TextArea textarea_lives;

    @FXML private Rectangle rectangle_gameZone;

    @FXML private Rectangle rectangle_en1;

    @FXML private Rectangle rectangle_en2;

    @FXML private Rectangle rectangle_en3;

    @FXML private Rectangle rectangle_protagonist;

    private int enemyDirection = 0; //0 right | 1 left

    //TODO: Add fixed positions in data structure?
    //TODO: Change enemy movement
    //TODO: Add Protagonist bounds

    @FXML
    void recDetect() {
        System.out.println("T DETECT: 0"); //Drag Detection
    }

    @FXML
    void recMouseEnter() {
        System.out.println("ENTER!"); //Enter Screen Detection
    }

    @FXML
    void recMouseExit() {
        System.out.println("EXIT!"); //Exit Screen Detection
    }

    @FXML
    void recMouseMove() {
        //System.out.println("MOVE!"); //Mouse Move Detection

        if (rectangle_en1.getLayoutY() >= 529) { //589
            System.out.println("Enemy Reached you; RIP");
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
    }

    @FXML
    void movePlayerPressed() {
        rectangle_protagonist.setLayoutX(rectangle_protagonist.getLayoutX() + 10);
    }

    @FXML
    void movePlayerReleased() {
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