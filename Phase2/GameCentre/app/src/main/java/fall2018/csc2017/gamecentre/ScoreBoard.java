package fall2018.csc2017.gamecentre;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Score board for games.
 */
class ScoreBoard implements Serializable {

    /**
     * Stores the scoreboard.
     */
    static final String SCOREBOARD = "score.ser";

    /**
     * Stores a HashMap of game (String): Score[];
     */
    private HashMap<String, ArrayList<Score>> byGame = new HashMap<>();

    /**
     * Stores a HashMap of user (String): Score[].
     */
    private HashMap<String, ArrayList<Score>> byUser = new HashMap<>();

    /**
     * Update hashmap byGame.
     *
     * @param score score that is added
     */
    void updateByGame(Score score) {
        // Assuming index 0 - 9 is lowest to highest score.
        if (!byGame.containsKey(score.getGame())) {
            byGame.put(score.getGame(), new ArrayList<Score>());
        }
        updateArray(score, byGame.get(score.getGame()));
    }

    /**
     * Update hashmap byUser.
     */
    void updateByUser(Score score) {
        if (!byUser.containsKey(score.getUser())) {
            byUser.put(score.getUser(), new ArrayList<Score>());
        }
        updateArray(score, byUser.get(score.getUser()));
    }

    /**
     * Update the given ArrayList.
     *
     * @param score score to add
     * @param toUpdate array to update
     */
    private void updateArray(Score score, ArrayList<Score> toUpdate) {
        int index = 0;
        for (Score x : toUpdate) {
            if (score.getScore() >= x.getScore())
                break;
            index++;
        }
        if (index >= toUpdate.size()) {
            toUpdate.add(score);
        } else {
            toUpdate.add(index, score);
        }
    }

    /**
     * Get the top ten score in categories depending on what select is.
     *
     * @param name the name of either the user or the game
     * @param select selects between either "user" or "game"
     * @return an array of the top scores
     */
    Score[] getTopTenSelect(String name, String select) {
        ArrayList<Score> scoresByUser = this.byUser.get(name);
        ArrayList<Score> scoresByGame = this.byGame.get(name);
        if (select.equals("user")) {
            return getTopTen(scoresByUser);
        }
        return getTopTen(scoresByGame);
    }

    /**
     * Return the top ten by user.
     *
     * @return the top ten by user.
     */
    private Score[] getTopTen(ArrayList<Score> scoreList) {
        Score[] scores = new Score[10];
        ArrayList<Score> newScoreList = checkIfNull(scoreList);

        for (int i = 0; i < scores.length; i++) {
            scores[i] = new Score();
        }
        for (int i = 0; i < 10; i++) {
            if (i < newScoreList.size()) {
                scores[i] = newScoreList.get(i);
            }
        }
        return scores;
    }

    /**
     * Return the top ten by game by user.
     *
     * @return the top ten by game by user.
     */
    Score[] getTopTenByGameByUser(String game, String name) {
        Score[] scores = new Score[10];
        ArrayList<Score> scoresAll = checkIfNull(this.byUser.get(name));
        for (int i = 0; i < scores.length; i++) {
            scores[i] = new Score();
        }
        checkUserAndGameName(game, scores, scoresAll);
        return scores;
    }

    /**
     * Check if item in scoresAll has gamename game, and add it to scores if it does.
     * @param game the game name
     * @param scores array of scores to be updated
     * @param scoresAll arraylist of all previous scores
     */
    private void checkUserAndGameName(String game, Score[] scores, ArrayList<Score> scoresAll) {
        int j = 0;
        for (int i = 0; i < scoresAll.size(); i++) {
            if (j == 10) {
                break;
            }
            if (scoresAll.get(i).getGame().equals(game)) {
                scores[j] = scoresAll.get(i);
                j++;
            }
        }
    }

    /**
     * Checks to see if the arraylist is null, if it is, return a new non null arraylist.
     *
     * @param score the input arraylist
     * @return score if score is not null, or a new empty arraylist if score is null
     */
    private ArrayList<Score> checkIfNull(ArrayList<Score> score) {
        if (score == null) {
            return new ArrayList<>();
        }
        return score;
    }

}
