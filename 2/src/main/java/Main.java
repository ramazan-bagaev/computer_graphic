

public class Main {

    public static void main(String[] argv) throws Exception {
        ImageReader reader = new ImageReader();

        Image im = reader.read("images/gradient.png", "gradient", "png");
        Image im1 = reader.read("images/cat.jpg", "cat", "jpg");
        Image im2 = reader.read("images/iguana.jpg", "iguana", "jpg");
        Image im3 = reader.read("images/lenna.jpg", "lenna", "jpg");


        ImageProcessor.processFloydSteinburg(im, true);
        ImageProcessor.processFloydSteinburg(im1, true);
        ImageProcessor.processFloydSteinburg(im2, true);
        //ImageProcessor.processFloydSteinburg(im3, true);


        ImageWriter.write(im);
        ImageWriter.write(im1);
        ImageWriter.write(im2);
        //ImageWriter.write(im3);
    }
}
