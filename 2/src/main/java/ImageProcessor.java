import java.util.concurrent.ThreadLocalRandom;

public class ImageProcessor {


    public static void processThresholding(Image image){
        for(int i = 0; i < image.getHeight(); i++){
            for(int j = 0; j < image.getWidth(); j++){
                image.set(j, i, image.get(j, i) < 127 ? 0 : 255);
            }
        }
    }

    public static void processRandomDithering(Image image){
        for(int i = 0; i < image.getHeight(); i++){
            for(int j = 0; j < image.getWidth(); j++){
                image.set(j, i, image.get(j, i) < ThreadLocalRandom.current().nextInt(0, 256) ? 0 : 255);
            }
        }
    }

    public static void processOrderingDithering(Image image, int size) throws Exception {
        DitherMatrix dm = new DitherMatrix(size);
        for(int i = 0; i < image.getHeight(); i++){
            for(int j = 0; j < image.getWidth(); j++){
                image.set(j, i, image.get(j, i) < dm.get(j % size, i % size) ? 0 : 255);
            }
        }
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
    }

    public static void processFloydSteinburg(Image image, boolean bothDirections){
        for(int i = 0; i < image.getHeight(); i++){
            int y = i;
            for(int j = 0; j < image.getWidth(); j++){
                int x = (i % 2 == 0 || !bothDirections) ? j : image.getWidth() - j - 1;
                int error = image.get(x, y);
                if (error < 127){
                    image.set(x, y, 0);
                }
                else {
                    image.set(x, y, 255);
                    error -= 255;
                }
                int dir = (i % 2 == 0 || !bothDirections) ? 1 : -1;
                image.set(x + dir, y, 7*error/16 + image.get(x + dir, y));
                image.set(x - dir, y + 1, 3*error/16 + image.get(x - dir, y + 1));
                image.set(x, y + 1 ,5*error/16 + image.get(x, y + 1));
                image.set(x + dir, y + 1, error/16 + image.get(x + dir, y + 1));

            }
        }
    }
}
