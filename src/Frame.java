import javax.swing.*;
import static javax.swing.SwingUtilities.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

/*
* Controls the JPanel and JFrame, creates a grid of Nodes, contains
* all methods related to A* Pathfinding.
*/
public class Frame {

    final int maxRow = 21;
    final int maxCol = 21;
    boolean goalReached = false;

    // NODE
    Node[][] grid = new Node[maxRow][maxCol];
    Node startNode, targetNode, currentNode;
    ArrayList<Node> openList = new ArrayList<>();
    ArrayList<Node> checkedList = new ArrayList<>();

    public static void main(String[] args) {
        new Frame();
    }

    public Frame() {
        // PANEL
        JPanel panel = new JPanel();
        panel.setBackground(Color.black);
        panel.setLayout(new GridLayout(maxRow, maxCol));
        panel.setFocusable(true);

        // FRAME
        JFrame frame = new JFrame();
        frame.setContentPane(panel);
        frame.setTitle("A Star Pathfinding");
        frame.getContentPane().setPreferredSize(new Dimension(800, 800));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Adding Nodes
        for (int row = 0; row < maxRow; row++) {
            for (int col = 0; col < maxCol; col++) {
                grid[row][col] = new Node(row, col);
                panel.add(grid[row][col]);
            }
        }

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Key Bindings (Focus does not matter)
        InputMap inputMap = getRootPane(frame).getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);

        Action enterAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aStarPathfinding();
            }
        };

        inputMap.put(KeyStroke.getKeyStroke("ENTER"), "enterAction");
        getRootPane(frame).getActionMap().put("enterAction", enterAction);

        // Default nodes
        startNode(10, 1);
        targetNode(10, 19);

        if (startNode != null && targetNode != null) {
            // Only calculate once nodes are placed
            getCosts();
        }
    }

    public void startNode(int row, int col) {
        grid[row][col].setStartNode();
        startNode = grid[row][col];
        currentNode = startNode;
    }

    public void targetNode(int row, int col) {
        grid[row][col].setTargetNode();
        targetNode = grid[row][col];
    }

    public void openNode(Node node) {
        if (!node.openNode && !node.checkedNode && !node.solidNode) {
            node.setOpenNode();
            node.parent = currentNode;
            openList.add(node);
        }
    }

    public void getCosts() {
        int row = 0;
        int col = 0;

        // Calculates costs for every node
        while (col < maxCol && row < maxRow) {
            // Distance between current node and start node
            grid[row][col].gCost = (Math.abs(grid[row][col].row - startNode.row)) + (Math.abs(grid[row][col].col - startNode.col));

            // Distance from current node to end node
            grid[row][col].hCost = (Math.abs(grid[row][col].row - targetNode.row)) + (Math.abs(grid[row][col].col - targetNode.col));

            // Total cost of node
            grid[row][col].fCost = grid[row][col].gCost + grid[row][col].hCost;

            col++;

            if (col == maxCol) {
                col = 0;
                row++;
            }
        }
    }

    public void pathTracking() {
        Node current = targetNode;

        while (current != startNode) {
            current = current.parent;

            if (current != startNode) {
                current.setPath();
            }
        }
    }

    public void aStarPathfinding() {
        while (!goalReached) {
            int col = currentNode.col;
            int row = currentNode.row;

            if (currentNode != startNode) {
                currentNode.setCheckedNode();
            }
            if (currentNode.solidNode) {
                openList.remove(currentNode);
            }

            checkedList.add(currentNode);
            openList.remove(currentNode);

            // Open nodes in all directions
            if (row - 1 >= 0) {
                openNode(grid[col][row - 1]);
            }
            if (col - 1 >= 0) {
                openNode((grid[col - 1][row]));
            }
            if (row + 1 < maxRow) {
                openNode((grid[col][row + 1]));
            }
            if (col + 1 < maxCol) {
                openNode((grid[col + 1][row]));
            }

            // Find best node
            int bestNodeIndex = 0;
            int bestNodefCost = 999;

            for (int i = 0; i < openList.size(); i++) {

                // Check if fCost is better
                if (openList.get(i).fCost < bestNodefCost) {
                    bestNodeIndex = i;
                    bestNodefCost = openList.get(i).fCost;
                }

                // If fCost is equal, check the gCost
                else if (openList.get(i).fCost == bestNodefCost) {
                    if (openList.get(i).gCost < openList.get(bestNodeIndex).gCost) {
                        bestNodeIndex = i;
                    }
                }
            }
            try {
                currentNode = openList.get(bestNodeIndex);
            } catch (Exception e) {
                // If no path is valid
                System.out.println("Try Again");
                break;
            }

            if (currentNode == targetNode) {
                goalReached = true;
                pathTracking();
            }
        }
    }
}
