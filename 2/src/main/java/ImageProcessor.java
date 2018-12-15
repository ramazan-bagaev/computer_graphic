import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class ImageProcessor {

    public static void processThresholding(Image image){
        for(int i = 0; i < image.getHeight(); i++){
            for(int j = 0; j < image.getWidth(); j++){
                image.set(j, i, image.get(j, i) < 127 ? 0 : 255);
            }
        }
        image.processedBy("thresholding");
    }

    public static void processRandomDithering(Image image){
        for(int i = 0; i < image.getHeight(); i++){
            for(int j = 0; j < image.getWidth(); j++){
                image.set(j, i, image.get(j, i) < ThreadLocalRandom.current().nextInt(0, 256) ? 0 : 255);
            }
        }
        image.processedBy("randomDithering");
    }

    public static void processOrderingDithering(Image image, int size) throws Exception {
        DitherMatrix dm = new DitherMatrix(size);
        for(int i = 0; i < image.getHeight(); i++){
            for(int j = 0; j < image.getWidth(); j++){
                image.set(j, i, image.get(j, i) < dm.get(j % size, i % size) ? 0 : 255);
            }
        }
        image.processedBy("orderingDithering.size(" + String.valueOf(size) + ")");
    }

    public static void processErrorDiffusion(Image image, boolean bothDirections){
        int error = 0;
        for(int i = 0; i < image.getHeight(); i++){
            int y = i;
            for(int j = 0; j < image.getWidth(); j++){
                int x = (i % 2 == 0 || !bothDirections) ? j : image.getWidth() - j - 1;
                error += image.get(x, y);
                if (error < 127) image.set(x, y, 0);
                else {
                    image.set(x, y, 255);
                    error -= 255;
                }
            }
        }
        String dir;
        if (bothDirections) dir = "bothDirections";
        else dir = "oneDirection";
        image.processedBy("errorDiffusion." + dir);
    }

    public static void processFloydSteinburg(Image image, boolean bothDirections){
        Map<Integer, Double> map = new HashMap();
        int index;
        for(int i = 0; i < image.getHeight(); i++){
            int y = i;
            for(int j = 0; j < image.getWidth(); j++){
                int x = (i % 2 == 0 || !bothDirections) ? j : image.getWidth() - j - 1;
                index = y * image.getWidth() + x;
                double error = image.get(x, y) + map.getOrDefault(index, Double.valueOf(0));
                if (error < 127){
                    image.set(x, y, 0);
                }
                else {
                    image.set(x, y, 255);
                    error -= 255;
                }
                int dir = (i % 2 == 0 || !bothDirections) ? 1 : -1;
                map.put(index + dir, 7*error/16 + map.getOrDefault(index + dir, Double.valueOf(0)));
                map.put(index - dir + image.getWidth(), 3*error/16 + map.getOrDefault(index - dir + image.getWidth(), Double.valueOf(0)));
                map.put(index + image.getWidth(), 5*error/16 + map.getOrDefault(index + image.getWidth(), Double.valueOf(0)));
                map.put(index + dir + image.getWidth(), error/16 + map.getOrDefault(index + dir + image.getWidth(), Double.valueOf(0)));

            }
        }
        String dir;
        if (bothDirections) dir = "bothDirections";
        else dir = "oneDirection";
        image.processedBy("floydSteinburg." + dir);

    }
}
