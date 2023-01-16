/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;

public class BaseballElimination {
    String[] teams;
    int[] wins;
    int[] losses;
    int[] remainingGames;
    int[][] g;

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
            wins[count] = Integer.parseInt(fields[1]);
            losses[count] = Integer.parseInt(fields[2]);
            remainingGames[count] = Integer.parseInt(fields[3]);
            for (int i = 0; i < teamNum; i += 1) {
                g[count][i] = Integer.parseInt(fields[4 + i]);
            }
            count += 1;
        }

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
