import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class MSTApp extends JFrame {

    private ArrayList<Edge> edges = new ArrayList<>();
    private ArrayList<String> cities = new ArrayList<>();
    private Map<String, ArrayList<Edge>> mst = new HashMap<>();
    private JTextArea resultArea;
    private JTextField city1Field, city2Field, distanceField, startCityField, endCityField;

    public MSTApp() {
        setTitle("Minimum Distance Between Cities using MST");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("City 1:"), gbc);
        gbc.gridx = 1;
        city1Field = new JTextField();
        inputPanel.add(city1Field, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("City 2:"), gbc);
        gbc.gridx = 1;
        city2Field = new JTextField();
        inputPanel.add(city2Field, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(new JLabel("Distance:"), gbc);
        gbc.gridx = 1;
        distanceField = new JTextField();
        inputPanel.add(distanceField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        inputPanel.add(new JLabel("Start City:"), gbc);
        gbc.gridx = 1;
        startCityField = new JTextField();
        inputPanel.add(startCityField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        inputPanel.add(new JLabel("End City:"), gbc);
        gbc.gridx = 1;
        endCityField = new JTextField();
        inputPanel.add(endCityField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        JButton addButton = new JButton("Add Connection");
        inputPanel.add(addButton, gbc);

        gbc.gridy = 6;
        JButton mstButton = new JButton("Find MST and Shortest Path");
        inputPanel.add(mstButton, gbc);

        add(inputPanel, BorderLayout.NORTH);

        resultArea = new JTextArea();
        add(new JScrollPane(resultArea), BorderLayout.CENTER);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String city1 = city1Field.getText().trim();
                String city2 = city2Field.getText().trim();
                int distance = Integer.parseInt(distanceField.getText().trim());

                if (!cities.contains(city1)) {
                    cities.add(city1);
                }
                if (!cities.contains(city2)) {
                    cities.add(city2);
                }

                edges.add(new Edge(city1, city2, distance));
                resultArea.append("Added connection: " + city1 + " - " + city2 + " : " + distance + "\n");
            }
        });

        mstButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                findMST();
                String startCity = startCityField.getText().trim();
                String endCity = endCityField.getText().trim();
                if (!startCity.isEmpty() && !endCity.isEmpty()) {
                    findShortestPath(startCity, endCity);
                }
            }
        });
    }

    private void findMST() {
        resultArea.append("\nFinding MST...\n");

        Collections.sort(edges, new Comparator<Edge>() {
            public int compare(Edge e1, Edge e2) {
                return Integer.compare(e1.distance, e2.distance);
            }
        });

        UnionFind uf = new UnionFind(cities.size());
        int totalDistance = 0;

        mst.clear();
        for (String city : cities) {
            mst.put(city, new ArrayList<>());
        }

        for (Edge edge : edges) {
            int city1Index = cities.indexOf(edge.city1);
            int city2Index = cities.indexOf(edge.city2);

            if (uf.find(city1Index) != uf.find(city2Index)) {
                uf.union(city1Index, city2Index);
                mst.get(edge.city1).add(edge);
                mst.get(edge.city2).add(edge);
                resultArea.append("Adding edge: " + edge.city1 + " - " + edge.city2 + " : " + edge.distance + "\n");
                totalDistance += edge.distance;
            }
        }

        resultArea.append("Total distance of MST: " + totalDistance + "\n");
    }

    private void findShortestPath(String startCity, String endCity) {
        resultArea.append("\nFinding shortest path from " + startCity + " to " + endCity + "...\n");
        ArrayList<String> path = new ArrayList<>();
        boolean found = dfs(startCity, endCity, path, new ArrayList<String>());

        if (found) {
            int pathDistance = calculatePathDistance(path);
            resultArea.append("Path: " + String.join(" -> ", path) + "\n");
            resultArea.append("Total distance: " + pathDistance + "\n");
        } else {
            resultArea.append("No path found between " + startCity + " and " + endCity + "\n");
        }
    }

    private boolean dfs(String current, String end, ArrayList<String> path, ArrayList<String> visited) {
        visited.add(current);
        path.add(current);

        if (current.equals(end)) {
            return true;
        }

        for (Edge edge : mst.get(current)) {
            String nextCity = edge.city1.equals(current) ? edge.city2 : edge.city1;
            if (!visited.contains(nextCity)) {
                boolean found = dfs(nextCity, end, path, visited);
                if (found) {
                    return true;
                }
            }
        }

        path.remove(path.size() - 1);
        return false;
    }

    private int calculatePathDistance(ArrayList<String> path) {
        int totalDistance = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            String city1 = path.get(i);
            String city2 = path.get(i + 1);
            for (Edge edge : mst.get(city1)) {
                if ((edge.city1.equals(city1) && edge.city2.equals(city2)) || (edge.city1.equals(city2) && edge.city2.equals(city1))) {
                    totalDistance += edge.distance;
                    break;
                }
            }
        }
        return totalDistance;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MSTApp().setVisible(true);
            }
        });
    }
}

class Edge {
    String city1, city2;
    int distance;

    Edge(String city1, String city2, int distance) {
        this.city1 = city1;
        this.city2 = city2;
        this.distance = distance;
    }
}

class UnionFind {
    private int[] parent, rank;

    UnionFind(int size) {
        parent = new int[size];
        rank = new int[size];
        for (int i = 0; i < size; i++) {
            parent[i] = i;
        }
    }

    int find(int p) {
        if (p != parent[p]) {
            parent[p] = find(parent[p]);
        }
        return parent[p];
    }

    void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);

        if (rootP != rootQ) {
            if (rank[rootP] > rank[rootQ]) {
                parent[rootQ] = rootP;
            } else if (rank[rootP] < rank[rootQ]) {
                parent[rootP] = rootQ;
            } else {
                parent[rootQ] = rootP;
                rank[rootP]++;
            }
        }
    }
}
