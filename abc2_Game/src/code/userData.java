package code;

/**
 * Created by Ryan on 11/18/2016.
 */
public class userData {
    public String username;
    int currentScore;
    int lives;
    int weapon_selected;


    public enum WeaponType {
        PISTOL,
        BOLTACTION,
        AK47
    }

    public enum ScoreOperation {
        ADD,
        SUBTRACT,
        SET
    }

    public userData() {}

    public userData(String Username, int CurrentScore, int Lives) {
        username = Username;
        currentScore = CurrentScore;
        lives = Lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public String getWeaponTypeName(int WeaponID) {
        switch (weapon_selected) {
            case 0:     return "PISTOL";
            case 1:     return "BOLTACTION";
            case 2:     return "AK47";
            default:    return "[ERROR] INCORRECT WEAPON ID... ";
        }
    }

    public void changeScore(int Score, ScoreOperation ScoreTYPE) {
        switch (ScoreTYPE) {
            case ADD:       currentScore += Score; break;
            case SUBTRACT:  currentScore -= Score; break;
            case SET:       currentScore =  Score; break;
        }
    }

    public void setUsername(String Username) {
        this.username = Username;
    }

    public String getUsername() {
        return this.username;
    }
}
