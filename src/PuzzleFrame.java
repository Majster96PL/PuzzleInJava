import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;

public class PuzzleFrame extends JFrame {

    private JPanel puzzlePanel;
    private BufferedImage sourceImage;
    private BufferedImage resizedImage;
    private Image image;
    private List<PuzzleButtons> buttonsList;
    private List<Point> elements;
    private int width, height;
    private PuzzleButtons lastButton;
    private final int NUMBER_OF_BUTTONS = 12;


    public PuzzleFrame(){
        initUI();
    }

    private void initUI(){
        elements = new ArrayList<>();

        elements.add(new Point(0,0));
        elements.add(new Point(0,1));
        elements.add(new Point(0,2));
        elements.add(new Point(1,0));
        elements.add(new Point(1,1));
        elements.add(new Point(1,2));
        elements.add(new Point(2,0));
        elements.add(new Point(2,1));
        elements.add(new Point(2,2));
        elements.add(new Point(3,0));
        elements.add(new Point(3,1));
        elements.add(new Point(3,2));

        buttonsList = new ArrayList<>();

        puzzlePanel = new JPanel();
        puzzlePanel.setBorder(BorderFactory.createLineBorder(Color.gray));
        puzzlePanel.setLayout(new GridLayout(4,3,0,0));
        loadImageAsButtons();
        width = resizedImage.getWidth(null);
        height = resizedImage.getHeight(null);
        add(puzzlePanel, BorderLayout.CENTER);
        createImageAsButtons();
        pack();
        setTitle("Puzzle w Javie");
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void loadImageAsButtons(){
        try{
            sourceImage = PuzzleImageIcon.loadImage();
            int height = PuzzleImageIcon.getNewHeight(sourceImage.getWidth(), sourceImage.getHeight());
            resizedImage = PuzzleImageIcon.resizeImage(sourceImage, PuzzleImageIcon.DESIRED_WIDTH,height,BufferedImage.TYPE_INT_ARGB);
        }catch (IOException e) {
            Logger.getLogger(PuzzleFrame.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void createImageAsButtons(){
        for (int i = 0; i < 4 ; i++) {
            int j;
            for (j = 0; j < 3; j++) {
                image = createImage(new FilteredImageSource(resizedImage.getSource(),
                        new CropImageFilter(j * width / 3, i * height / 4, (width / 3), (height / 4))));
                PuzzleButtons button = new PuzzleButtons(image);
                button.putClientProperty("position", new Point(i, j));
                if (i == 3 && j == 2){
                    lastButton = new PuzzleButtons();
                    lastButton.setBorderPainted(false);
                    lastButton.setContentAreaFilled(false);
                    lastButton.setLastButtons();
                    lastButton.putClientProperty("position", new Point(i, j));
                }else{
                    buttonsList.add(button);
                }
            }
        }
        Collections.shuffle(buttonsList);
        buttonsList.add(lastButton);
        for (int i = 0; i < NUMBER_OF_BUTTONS; i++) {
            PuzzleButtons btn = buttonsList.get(i);
            puzzlePanel.add(btn);
            btn.setBorder(BorderFactory.createLineBorder(Color.gray));
            btn.addActionListener(new PuzzleAction());
        }
    }

    private class PuzzleAction extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            checkButton(e);
            checkSolution();
        }

        private void checkButton(ActionEvent e){
            int indexOfList = 0;
            for(PuzzleButtons button : buttonsList ){
                if(button.isLastButtons()){
                    indexOfList = buttonsList.indexOf(button);
                }
            }
            JButton jButton =(JButton) e.getSource();
            int indexOfButton = buttonsList.indexOf(jButton);
            if((indexOfButton - 1 == indexOfList) || (indexOfButton + 1 == indexOfList) ||
                    (indexOfButton - 3 == indexOfList) || (indexOfButton + 3 == indexOfList)){
                Collections.swap(buttonsList, indexOfButton, indexOfList);
                updateButtons();
            }
        }

        private void updateButtons() {
            puzzlePanel.removeAll();
            for (JComponent btn : buttonsList){
                puzzlePanel.add(btn);
            }
            puzzlePanel.validate();
        }

        private void checkSolution(){
            List<Point> current = new ArrayList<>();
            for (JComponent btn : buttonsList){
                current.add((Point) btn.getClientProperty("position"));
            }
            if(compareList(elements, current)){
                JOptionPane.showMessageDialog(puzzlePanel, "Finished",
                        "Congratulation", JOptionPane.INFORMATION_MESSAGE);
            }
        }

        public boolean compareList(List<Point> firstList, List<Point> secondList){
            return firstList.toString().contentEquals(secondList.toString());
        }
    }
}

