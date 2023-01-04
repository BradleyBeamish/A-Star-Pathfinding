import javax.swing.*;
import java.awt.*;

/*
 * Template for each node on the grid.
 */
public class Node extends JButton {

    Node parent;
    int col, row;
    int fCost, gCost, hCost;
    boolean startNode, targetNode, solidNode, openNode, checkedNode;

    public Node(int col, int row) {
        this.row = row;
        this.col = col;
        setBackground(Color.white);
        this.addMouseListener(new UserInput(this));
    }

    public void setStartNode() {
        startNode = true;
        setBackground(Color.magenta);
    }

    public void setTargetNode() {
        targetNode = true;
        setBackground(Color.red);
    }

    public void setSolidNode() {
        solidNode = true;
        setBackground(Color.black);
    }

    public void setOpenNode() {
        openNode = true;
    }

    public void setCheckedNode() {
        checkedNode = true;
        setBackground(Color.lightGray);
    }

    public void setPath() {
        setBackground(Color.green);
    }

    public void removeSolidNode() {
        solidNode = false;
        setBackground(Color.white);
    }
}
