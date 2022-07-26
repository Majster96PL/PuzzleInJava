import javax.imageio.ImageIO31;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PuzzleImageIcon {

    public static final int DESIRED_WIDTH = 500;

    public static BufferedImage loadImage() throws IOException {
        BufferedImage bufferedImage = ImageIO.read(new File("image/hqdefault.jpg"));
        return bufferedImage;
    }

    public static BufferedImage resizeImage(BufferedImage image,
                                       int width, int height, int type) throws IOException{
        BufferedImage resizedImage = new BufferedImage(width, height, type);
        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.drawImage(image, 0 , 0, width, height, null);
        graphics2D.dispose();
        return resizedImage;
    }

    public static int getNewHeight(int w, int h){
        double ratio = DESIRED_WIDTH/ (double) w;
        int newHeight =(int) (h* ratio);
        return newHeight;
    }
}
