import java.awt.image.BufferedImage;

public class Image {

    private String name;
    private String format;
    private BufferedImage bufferedImage;

    public Image(String name, BufferedImage bufferedImage, String format){
        this.bufferedImage = bufferedImage;
        this.name = name;
        this.format = format;
    }

    public int get(int x, int y){
        if (x < 0 || x >= getWidth()) return 0;
        if (y < 0 || y >= getHeight()) return 0;
        return bufferedImage.getRaster().getSample(x, y, 0);
    }

    public int getWidth(){
        return bufferedImage.getWidth();
    }

    public int getHeight(){
        return bufferedImage.getHeight();
    }

    public void set(int x, int y, int value){
        if (x < 0 || x >= getWidth()) return;
        if (y < 0 || y >= getHeight()) return;
        bufferedImage.getRaster().setSample(x, y, 0, value);
        //bufferedImage.getRaster().setSample(x, y, 1, value);
        //bufferedImage.getRaster().setSample(x, y, 2, value);
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public String getName() {
        return name;
    }

    public String getFormat() {
        return format;
    }

    public void processedBy(String processor){
        name = processor + "." + name;
    }
}
