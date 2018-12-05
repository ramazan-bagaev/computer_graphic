import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImageReader {

    public ImageReader(){

    }

    public Image read(String file, String name, String format){
        BufferedImage img = null;
        try {
            InputStream in = getClass().getResourceAsStream(file);
            img = ImageIO.read(in);
            //img = ImageIO.read(new File(file));
        } catch (IOException e) {
        }
        return new Image(name, img, format);
    }
}
