/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class BaseballElimination {
    private String[] teams;
    private int[] wins;
    private int[] losses;
    private int[] remainingGames;
    private int[][] g; // num of remaining games between two teams i and j
    private ArrayList<Integer>[] opponents;
    private HashMap<String, Integer> teamIdPair;
    private ArrayList<String>[] coe;

    /** create a baseball division from given filename */
    public BaseballElimination(String filename) {
        In inputStream = new In(filename);
        int count = 0;
        int teamNum;
        teamIdPair = new HashMap<>();
        if (inputStream.hasNextLine()) {
            teamNum = Integer.parseInt(inputStream.readLine());
            teams = new String[teamNum];
            wins = new int[teamNum];
            losses = new int[teamNum];
            remainingGames = new int[teamNum];
            g = new int[teamNum][teamNum];
            coe = new ArrayList[teamNum];
            for (int i = 0; i < teamNum; i += 1) {
                coe[i] = new ArrayList<>();
            }
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
            opponents = new ArrayList[teamNum];
            for (int i = 0; i < teamNum; i += 1) {
                g[count][i] = Integer.parseInt(fields[4 + i]);
                opponents[i] = new ArrayList<Integer>();
                for (int j = i + 1; j < teamNum; j += 1) {
                    opponents[i].add(j);
                }
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

    /** Helper function to get coe */
    private void getCoe(String team) {
        int n = teams.length;
        int currTeamNum = teamIdPair.get(team);
        if (coe[currTeamNum].size() != 0) {
            return;
        }
        for (int i = 0; i < n; i += 1) {
            if (i == currTeamNum) {
                continue;
            }
            if (wins[i] > (wins[currTeamNum] + remainingGames[currTeamNum])) {
                coe[currTeamNum].add(teams[i]);
                return;
            }
        }
        int verticeNum = (n - 1) * (n - 2) / 2 + n + 1;
        int source = (n - 1) * (n - 2) / 2 + n - 1;
        int target = (n - 1) * (n - 2) / 2 + n;
        FlowNetwork f = new FlowNetwork(verticeNum);
        int matchCount = 0;
        int fullflow = 0;
        for (int i = 0; i < currTeamNum; i += 1) {
            f.addEdge(new FlowEdge(i, target,
                                   wins[currTeamNum] + remainingGames[currTeamNum] - wins[i]));
            for (int j : opponents[i]) {
                if (j == currTeamNum) {
                    continue;
                }
                f.addEdge(new FlowEdge(source, n - 1 + matchCount, g[i][j]));
                fullflow += g[i][j];
                matchCount += 1;
            }
        }
        for (int i = currTeamNum; i < n - 1; i += 1) {
            f.addEdge(new FlowEdge(i, target,
                                   wins[currTeamNum] + remainingGames[currTeamNum] - wins[i + 1]));
            for (int j : opponents[i + 1]) {
                if (j == currTeamNum) {
                    continue;
                }
                f.addEdge(new FlowEdge(source, n - 1 + matchCount, g[i + 1][j]));
                fullflow += g[i + 1][j];
                matchCount += 1;
            }
        }

        int oldVerNum = 0;
        int newVerNum = 0;
        for (int i = 0; i < n - 1; i += 1) {
            newVerNum = oldVerNum;
            for (int j = i + 1; j < n - 1; j += 1) {
                f.addEdge(new FlowEdge(n - 2 + newVerNum + j - i, i, Double.POSITIVE_INFINITY));
                f.addEdge(new FlowEdge(n - 2 + newVerNum + j - i, j, Double.POSITIVE_INFINITY));
                oldVerNum += 1;
            }

        }
        FordFulkerson ff = new FordFulkerson(f, source, target);
        if (ff.value() == fullflow) {
            return;
        }
        else {
            for (int i = 0; i < currTeamNum; i += 1) {
                if (ff.inCut(i)) {
                    coe[currTeamNum].add(teams[i]);
                }
            }
            for (int i = currTeamNum; i < n - 1; i += 1) {
                if (ff.inCut(i)) {
                    coe[currTeamNum].add(teams[i + 1]);
                }
            }
        }
    }

    /** is given team eliminated? */
    public boolean isEliminated(String team) {
        if (!teamIdPair.containsKey(team)) {
            throw new IllegalArgumentException("The team is not in the division!");
        }
        getCoe(team);
        int currTeamNum = teamIdPair.get(team);
        return (coe[currTeamNum].size() != 0);
    }

    /** subset R of teams that eliminates given team; null if not eliminated */
    public Iterable<String> certificateOfElimination(String team) {
        if (!teamIdPair.containsKey(team)) {
            throw new IllegalArgumentException("The team is not in the division!");
        }
        getCoe(team);
        if (coe[teamIdPair.get(team)].size() == 0) {
            return null;
        }
        else {
            return (coe[teamIdPair.get(team)]);
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

        // BaseballElimination division = new BaseballElimination("teams5.txt");
        // for (String team : division.teams()) {
        //     if (division.isEliminated(team)) {
        //         StdOut.print(team + " is eliminated by the subset R = { ");
        //         for (String t : division.certificateOfElimination(team)) {
        //             StdOut.print(t + " ");
        //         }
        //         StdOut.println("}");
        //     }
        //     else {
        //         StdOut.println(team + " is not eliminated");
        //     }
        // }

    }
}
