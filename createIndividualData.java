import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Edge {
    String from;
    String to;
    double cost;

    Edge(String from, String to, double cost) {
        this.from = from;
        this.to = to;
        this.cost = cost;
    }
}

public class NodeEdgeGenerator {

    static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    static List<String> generateNodes(int numNodes) {
        List<String> nodes = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < numNodes; i++) {
            String node = "" + CHARACTERS.charAt(random.nextInt(26)) + CHARACTERS.charAt(random.nextInt(26));
            nodes.add(node);
        }
        return nodes;
    }

    static List<Edge> generateEdges(List<String> nodes) {
        List<Edge> edges = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < nodes.size(); i++) {
            for (int j = i + 1; j < nodes.size(); j++) {
                if (random.nextDouble() < 0.4) { // Probability for creating edge
                    edges.add(new Edge(nodes.get(i), nodes.get(j), Math.round((random.nextDouble() * 4 + 1) * 10) / 10.0));
                }
            }
        }
        return edges;
    }

    public static void main(String[] args) {
        String email = "abc@email.com"; // Replace with actual email input

        // Generate seed based on email
        long seed = 0;
        for (char c : email.toCharArray()) {
            seed += c;
        }
        Random random = new Random(seed);

        // Generate number of nodes based on seed
        int numNodes = random.nextInt(5) + 4; // Random number of nodes between 4 and 8

        List<String> nodes = generateNodes(numNodes);
        List<Edge> edges = generateEdges(nodes);

        // Prepare output
        StringBuilder jsonOutput = new StringBuilder();
        jsonOutput.append("{\n");
        jsonOutput.append("  \"Nodes\": [");
        for (int i = 0; i < nodes.size(); i++) {
            if (i != 0) jsonOutput.append(", ");
            jsonOutput.append("\"").append(nodes.get(i)).append("\"");
        }
        jsonOutput.append("],\n");
        jsonOutput.append("  \"Edges\": [\n");
        for (int i = 0; i < edges.size(); i++) {
            Edge edge = edges.get(i);
            jsonOutput.append("    {\"from\": \"").append(edge.from).append("\", \"to\": \"").append(edge.to)
                    .append("\", \"cost\": ").append(edge.cost).append("}");
            if (i != edges.size() - 1) jsonOutput.append(",");
            jsonOutput.append("\n");
        }
        jsonOutput.append("  ]\n");
        jsonOutput.append("}");

        // Print output
        System.out.println(jsonOutput.toString());
    }
}
