/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;

import java.util.Arrays;
import java.util.HashMap;

public class BaseballElimination {
    private String[] teams;
    private int[] wins;
    private int[] losses;
    private int[] remainingGames;
    private int[][] g;
    private HashMap<String, Integer> teamIdPair;

    /** create a baseball division from given filename */
    public BaseballElimination(String filename) {
        In inputStream = new In(filename);
        int count = 0;
        int teamNum;
        if (inputStream.hasNextLine()) {
            teamNum = Integer.parseInt(inputStream.readLine());
            teams = new String[teamNum];
            wins = new int[teamNum];
            losses = new int[teamNum];
            remainingGames = new int[teamNum];
            g = new int[teamNum][teamNum];
        }
        else {
            throw new IllegalArgumentException();
        }

        while (inputStream.hasNextLine()) {
            String currLineString = inputStream.readLine();
            String[] fields = currLineString.split("\\s+");
            teams[count] = fields[0];
            teamIdPair.put(fields[0], count);
            wins[count] = Integer.parseInt(fields[1]);
            losses[count] = Integer.parseInt(fields[2]);
            remainingGames[count] = Integer.parseInt(fields[3]);
            for (int i = 0; i < teamNum; i += 1) {
                g[count][i] = Integer.parseInt(fields[4 + i]);
            }
            count += 1;
        }
    }

    /** number of teams */
    public int numberOfTeams() {
        return teams.length;
    }

    /** all teams */
    public Iterable<String> teams() {
        return Arrays.asList(teams);
    }

    /** number of wins for given team */
    public int wins(String team) {
        if (!teamIdPair.containsKey(team)) {
            throw new IllegalArgumentException("The team is not in the division!");
        }
        return wins[teamIdPair.get(team)];
    }

    /** number of losses for given team */
    public int losses(String team) {
        if (!teamIdPair.containsKey(team)) {
            throw new IllegalArgumentException("The team is not in the division!");
        }
        return losses[teamIdPair.get(team)];
    }

    /** number of remaining games for given team */
    public int remaining(String team) {
        if (!teamIdPair.containsKey(team)) {
            throw new IllegalArgumentException("The team is not in the division!");
        }
        return remainingGames[teamIdPair.get(team)];
    }

    /** number of remaining games between team1 and team2 */
    public int against(String team1, String team2) {
        if (!teamIdPair.containsKey(team1) || !teamIdPair.containsKey(team2)) {
            throw new IllegalArgumentException("The team is not in the division!");
        }
        return g[teamIdPair.get(team1)][teamIdPair.get(team2)];
    }

    public static void main(String[] args) {
        // BaseballElimination division = new BaseballElimination("teams4.txt");
        // for (String team : division.teams) {
        //     System.out.println(team);
        // }
        // for (int i = 0; i < division.teams.length; i += 1) {
        //     System.out.println(division.wins[i]);
        // }
        // for (int i = 0; i < division.teams.length; i += 1) {
        //     System.out.println(division.g[3][i]);
        // }

    }
}
