import java.awt.event.*;

/*
 * Controls mouse input
 */
public class UserInput implements MouseListener {

    Node node;

    public UserInput(Node node) {
        this.node = node;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            node.setSolidNode();
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            node.removeSolidNode();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
