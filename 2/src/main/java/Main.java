

public class Main {

    public static void main(String[] argv) throws Exception {
        ImageReader reader = new ImageReader();

        String dir = argv[0];
        String[] splited = dir.split("\\.|/");
        String name = splited[splited.length - 2];
        String format = splited[splited.length - 1];

        Image im = reader.read(dir, name, format);
        Image im1 = reader.read(dir, name, format);
        Image im2 = reader.read(dir, name, format);
        Image im3 = reader.read(dir, name, format);
        Image im4 = reader.read(dir, name, format);
        Image im5 = reader.read(dir, name, format);
        Image im6 = reader.read(dir, name, format);


        ImageProcessor.processThresholding(im);
        ImageProcessor.processRandomDithering(im1);
        ImageProcessor.processOrderingDithering(im2, 4);
        ImageProcessor.processErrorDiffusion(im3, false);
        ImageProcessor.processErrorDiffusion(im4, true);
        ImageProcessor.processFloydSteinburg(im5, false);
        ImageProcessor.processFloydSteinburg(im6, true);


        ImageWriter.write(im);
        ImageWriter.write(im1);
        ImageWriter.write(im2);
        ImageWriter.write(im3);
        ImageWriter.write(im4);
        ImageWriter.write(im5);
        ImageWriter.write(im6);

    }
}
