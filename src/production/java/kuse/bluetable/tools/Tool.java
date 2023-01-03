package kuse.bluetable.tools;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.ImageCursor;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public interface Tool {

    class Dot{
        public static final BufferedImage dot;

        static {
            BufferedImage dot1;
            try {
                dot1 = ImageIO.read(Objects.requireNonNull(Tool.class.getResource("/assets/image/tools/dot.png")));
            } catch (IOException e) {
                dot1 = null;
                e.printStackTrace();
            }
            dot = dot1;
        }
    }
     void activate();

    void deactivate();

    ImageCursor getImageCursor();

     static ImageCursor mergeDotInImageCursor(String imagePath){
        BufferedImage image;

        try {
            image = ImageIO.read(Objects.requireNonNull(Tool.class.getResource(imagePath)));
        } catch (Exception ignored){
            return null;
        }

        BufferedImage result = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
        result.getGraphics().drawImage(image,0,0,null);
        result.getGraphics().drawImage(Dot.dot,0,0,null);
        result.getGraphics().dispose();

        return new ImageCursor(SwingFXUtils.toFXImage(result,null));
    }
}
