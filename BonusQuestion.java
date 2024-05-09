import java.util.*;

class Edge {
    String to;
    double weight;

    Edge(String to, double weight) {
        this.to = to;
        this.weight = weight;
    }
}

public class ShortestPathFinder {
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
        // JSON input string provided by NodeEdgeGenerator output
        String inputJson = "{\"Nodes\": [\"IF\", \"SD\", \"YF\", \"TJ\", \"LU\", \"BH\", \"HM\"], \"Edges\": [{\"from\": \"IF\", \"to\": \"SD\", \"cost\": 2.5}, {\"from\": \"IF\", \"to\": \"YF\", \"cost\": 1.4}, {\"from\": \"IF\", \"to\": \"TJ\", \"cost\": 2.7}, {\"from\": \"IF\", \"to\": \"LU\", \"cost\": 1.6}, {\"from\": \"IF\", \"to\": \"HM\", \"cost\": 2.9}, {\"from\": \"SD\", \"to\": \"BH\", \"cost\": 2.2}, {\"from\": \"YF\", \"to\": \"BH\", \"cost\": 4.1}, {\"from\": \"YF\", \"to\": \"HM\", \"cost\": 2.3}, {\"from\": \"TJ\", \"to\": \"HM\", \"cost\": 1.8}]}";

        // Parsing JSON input and finding the shortest path
        Map<String, List<Edge>> graph = parseJson(inputJson);
        String startNode = "IF";
        String endNode = "BH";
        List<String> shortestPath = dijkstra(graph, startNode, endNode);
        System.out.println("Shortest path from " + startNode + " to " + endNode + ": " + shortestPath);
    }

    private static Map<String, List<Edge>> parseJson(String inputJson) {
        Map<String, List<Edge>> graph = new HashMap<>();

        // Parse JSON string
        inputJson = inputJson.replaceAll("[{}\"]", "");
        String[] parts = inputJson.split(", ");

        // Extract Nodes
        String nodesPart = parts[0];
        String[] nodesArray = nodesPart.split(": ")[1].replaceAll("[\\[\\]]", "").split(", ");
        List<String> nodes = Arrays.asList(nodesArray);

        // Extract Edges
        String edgesPart = parts[1];
        String[] edgesArray = edgesPart.split(": ")[1].split(", ");
        for (String edge : edgesArray) {
            String[] edgeInfo = edge.split(": ");
            String[] fromTo = edgeInfo[0].split(" to ");
            String from = fromTo[0];
            String to = fromTo[1];
            double cost = Double.parseDouble(edgeInfo[2]);
            if (!graph.containsKey(from)) {
                graph.put(from, new ArrayList<>());
            }
            graph.get(from).add(new Edge(to, cost));
        }
        return graph;
    }
}
