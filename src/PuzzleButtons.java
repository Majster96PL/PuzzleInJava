import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PuzzleButtons extends JButton {
    private boolean isLastButtons;

    public PuzzleButtons(){
        super();
        initButtons();
    }

    public PuzzleButtons(Image image){
        super(new ImageIcon(image));
        initButtons();
    }

    private void initButtons(){
        isLastButtons = false;
        BorderFactory.createLineBorder(Color.gray);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBorder(BorderFactory.createLineBorder(Color.MAGENTA));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBorder(BorderFactory.createLineBorder(Color.gray));
            }
        });
    }

    public void setLastButtons(){
        isLastButtons = true;
    }

    public boolean isLastButtons(){
        return isLastButtons;
    }
}
