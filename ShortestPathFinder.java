import java.util.*;

class ShortestPathFinder {
    static class Edge {
        String to;
        double weight;

        Edge(String to, double weight) {
            this.to = to;
            this.weight = weight;
        }
    }

    public static List<String> dijkstra(Map<String, List<Edge>> graph, String start, String end) {
        Map<String, Double> distances = new HashMap<>();
        Map<String, String> previous = new HashMap<>();
        PriorityQueue<String> queue = new PriorityQueue<>(Comparator.comparingDouble(distances::get));
        List<String> shortestPath = new ArrayList<>();

        for (String node : graph.keySet()) {
            distances.put(node, node.equals(start) ? 0.0 : Double.POSITIVE_INFINITY);
            queue.offer(node);
        }

        while (!queue.isEmpty()) {
            String current = queue.poll();
            if (current.equals(end)) {
                while (previous.containsKey(current)) {
                    shortestPath.add(current);
                    current = previous.get(current);
                }
                shortestPath.add(start);
                Collections.reverse(shortestPath);
                break;
            }

            if (distances.get(current) == Double.POSITIVE_INFINITY) {
                break;
            }

            for (Edge neighbor : graph.get(current)) {
                double alt = distances.get(current) + neighbor.weight;
                if (alt < distances.get(neighbor.to)) {
                    distances.put(neighbor.to, alt);
                    previous.put(neighbor.to, current);
                    queue.remove(neighbor.to); // Update the priority queue
                    queue.offer(neighbor.to);
                }
            }
        }
        return shortestPath;
    }

    public static void main(String[] args) {
        // Example JSON input
        String inputJson = "[{\"from\": \"A\", \"to\": \"B\", \"weight\": 1}, {\"from\": \"B\", \"to\": \"C\", \"weight\": 3}, {\"from\": \"B\", \"to\": \"E\", \"weight\": 3.5}, {\"from\": \"C\", \"to\": \"E\", \"weight\": 4}, {\"from\": \"C\", \"to\": \"D\", \"weight\": 2.5}, {\"from\": \"D\", \"to\": \"G\", \"weight\": 25}, {\"from\": \"G\", \"to\": \"F\", \"weight\": 3.5}, {\"from\": \"E\", \"to\": \"F\", \"weight\": 2}, {\"from\": \"F\", \"to\": \"H\", \"weight\": 2.5}, {\"from\": \"H\", \"to\": \"H\", \"weight\": 11}]";

        List<Map<String, Object>> edges = new ArrayList<>();
        edges = Arrays.asList(
                new HashMap<String, Object>() {{ put("from", "A"); put("to", "B"); put("weight", 1.0); }},
                new HashMap<String, Object>() {{ put("from", "B"); put("to", "C"); put("weight", 3.0); }},
                new HashMap<String, Object>() {{ put("from", "B"); put("to", "E"); put("weight", 3.5); }},
                new HashMap<String, Object>() {{ put("from", "C"); put("to", "E"); put("weight", 4.0); }},
                new HashMap<String, Object>() {{ put("from", "C"); put("to", "D"); put("weight", 2.5); }},
                new HashMap<String, Object>() {{ put("from", "D"); put("to", "G"); put("weight", 25.0); }},
                new HashMap<String, Object>() {{ put("from", "G"); put("to", "F"); put("weight", 3.5); }},
                new HashMap<String, Object>() {{ put("from", "E"); put("to", "F"); put("weight", 2.0); }},
                new HashMap<String, Object>() {{ put("from", "F"); put("to", "H"); put("weight", 2.5); }},
                new HashMap<String, Object>() {{ put("from", "H"); put("to", "H"); put("weight", 11.0); }}
        );

        Map<String, List<Edge>> graph = new HashMap<>();
        for (Map<String, Object> edge : edges) {
            String from = (String) edge.get("from");
            String to = (String) edge.get("to");
            double weight = (double) edge.get("weight");
            graph.putIfAbsent(from, new ArrayList<>());
            graph.get(from).add(new Edge(to, weight));
        }

        String startNode = "C";
        String endNode = "F";

        List<String> shortestPath = dijkstra(graph, startNode, endNode);
        System.out.println(shortestPath);
    }
}
