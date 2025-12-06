import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class D23 {
    public static class Solver {
        HashSet<String> computers;
        HashMap<String, HashSet<String>> adjList;

        public HashSet<HashSet<String>> getTrianglesDFS(
            String src, String cur, HashSet<String> vis
        ) {
            if (vis.size() == 3) {
                if (src.equals(cur)) {
                    HashSet<HashSet<String>> temp = new HashSet<>();
                    temp.add(vis);
                    return temp;
                } else {
                    return new HashSet<>();
                }
            }

            HashSet<HashSet<String>> triangles = new HashSet<>();

            for (String neighbour : adjList.get(cur)) {
                if (!vis.contains(neighbour)) {
                    HashSet<String> tempVis = new HashSet<>();
                    tempVis.addAll(vis);
                    tempVis.add(neighbour);

                    HashSet<HashSet<String>> subset =
                        getTrianglesDFS(src, neighbour, tempVis);
                    triangles.addAll(subset);
                }
            }

            return triangles;
        }

        public HashSet<HashSet<String>> getTrianglesFrom(String src) {
            return getTrianglesDFS(src, src, new HashSet<>());
        }

        public int solveP1() {
            HashSet<HashSet<String>> triangles = new HashSet<>();
            for (String chiefCom : computers) {
                if (chiefCom.charAt(0) != 't') continue;
                HashSet<HashSet<String>> subset = getTrianglesFrom(chiefCom);
                triangles.addAll(subset);
            }
            return triangles.size();
        }

        public boolean isMutualNeighbour(HashSet<String> vs, String test) {
            HashSet<String> adjListTest = adjList.get(test);
            for (String v : vs) {
                if (!adjListTest.contains(v)) {
                    return false;
                }
            }
            return true;
        }

        // only visit lexicographically larger neighbours
        public HashSet<HashSet<String>> getCliquesDFS(
            String cur, HashSet<String> vis
        ) {
            HashSet<HashSet<String>> cliques = new HashSet<>();

            for (String neighbour : adjList.get(cur)) {
                if (
                    cur.compareTo(neighbour) < 0 &&
                    !vis.contains(neighbour) &&
                    isMutualNeighbour(vis, neighbour)
                ) {
                    HashSet<String> tempVis = new HashSet<>();
                    tempVis.addAll(vis);
                    tempVis.add(neighbour);

                    HashSet<HashSet<String>> subset =
                        getCliquesDFS(neighbour, tempVis);
                    cliques.addAll(subset);
                }
            }

            cliques.add(vis);
            return cliques;
        }

        public HashSet<HashSet<String>> getCliquesFrom(String src) {
            HashSet<String> vis = new HashSet<>();
            vis.add(src);
            return getCliquesDFS(src, vis);
        }

        public String solveP2() {
            int maxCliqueSize = 0;
            String maxCliqueStr = "";

            for (String com : computers) {
                HashSet<HashSet<String>> subset = getCliquesFrom(com);
                for (HashSet<String> cliqueSet : subset) {
                    if (cliqueSet.size() > maxCliqueSize) {
                        maxCliqueSize = cliqueSet.size();
                        ArrayList<String> cliqueList =
                            new ArrayList<>(cliqueSet);
                        cliqueList.sort(null);
                        maxCliqueStr = String.join(",", cliqueList);
                    }
                }
            }

            return maxCliqueStr;
        }

        public Solver(HashMap<String, HashSet<String>> adjList) {
            this.adjList = adjList;
            computers = new HashSet<>(adjList.keySet());
        }
    }

    public static HashMap<String, HashSet<String>> input(Scanner scanner) {
        HashMap<String, HashSet<String>> adjList = new HashMap<>();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] computers = line.split("-");

            for (int i = 0; i <= 1; i++) {
                if (!adjList.keySet().contains(computers[i])) {
                    adjList.put(computers[i], new HashSet<>());
                }
                adjList.get(computers[i]).add(computers[1 - i]);
            }
        }

        return adjList;
    }

    public static HashMap<String, HashSet<String>> readFile(String filePath) {
        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);
            HashMap<String, HashSet<String>> adjList = input(scanner);
            scanner.close();
            return adjList;
        } catch (FileNotFoundException e) {
            System.out.println("Could not open file");
            e.printStackTrace();
            return new HashMap<>();
        }
    }

    public static void main(String[] args) {
        HashMap<String, HashSet<String>> adjList =
            readFile("Day 23/input.txt");
        Solver solver = new Solver(adjList);
        System.out.println(solver.solveP1());
        System.out.println(solver.solveP2());
    }
}