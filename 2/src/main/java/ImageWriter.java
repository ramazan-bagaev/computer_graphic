import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class ImageWriter {


    public static void write(Image image) throws IOException {
        ImageIO.write(image.getBufferedImage(), image.getFormat(), new File("processed." + image.getName() + "." + image.getFormat()));
    }
}
