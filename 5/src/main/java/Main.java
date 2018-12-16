import java.io.IOException;

public class Main {

    public static void main(String[] argv) throws IOException {
        String comand = argv[0];
        String dir = argv[1];
        if (comand.equals("encode")){
            Code.encode(dir);
        }
        if (comand.equals("decode")){
            Code.decode(dir);
        }
        return;
    }
}
