import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

public class Code {



    public static void encode(String file) throws IOException {
        String text = new String(Files.readAllBytes(Paths.get(file)));
        Map<Character, String> codeMap = HuffmanTree.createHuffmanTree(text).getHuffmanCode();
        BitSet bitSet = new BitSet();
        int index = 0;
        for(Character c: text.toCharArray()){
            String code = codeMap.get(c);
            for(Character cc: code.toCharArray()){
                if (cc == '1') bitSet.set(index, true);
                if (cc == '0') bitSet.set(index, false);
                index++;
            }
        }
        bitSet.set(index, true);

        writeToFile(codeMap, bitSet, index);
    }

    private static void writeToFile(Map<Character, String> codeMap, BitSet code, int size) throws IOException {
        FileOutputStream out = new FileOutputStream("encoded");
        out.write(code.toByteArray());
        out.write("|||".getBytes());
        out.write(String.valueOf(size).getBytes());
        out.write("|||".getBytes());
        out.write(getCodeMapString(codeMap).getBytes());
        out.close();
    }

    private static String getCodeMapString(Map<Character, String> codeMap){
        String res = "";
        for(Character key: codeMap.keySet()){
            res += key+"@:@"+codeMap.get(key)+"@:@";
        }
        return res;
    }

    public static void decode(String file) throws IOException {
        byte[] wholeFile = Files.readAllBytes(Paths.get(file));
        String encodedText = new String(wholeFile);
        String[] parts = encodedText.split("\\|\\|\\|");
        String code = parts[2];

        int size = Integer.parseInt(parts[1]);

        String[] splitedCode = code.split("@:@");

        Map<String, Character> codeMap = new HashMap<>();
        for(int i = 0; i < splitedCode.length; i+=2){
            codeMap.put(splitedCode[i+1], splitedCode[i].charAt(0));
        }
        StringBuilder encodedString = new StringBuilder();
        for(int i = 0; i < size; i++){
            int pos = i % 8;
            int index = i/8;
            int num = wholeFile[index];
            encodedString.append((num & (int) Math.pow(2, pos)) != 0 ? 1 : 0);
        }
        String text = decode(encodedString.toString(), codeMap);
        FileOutputStream out = new FileOutputStream("decoded");
        out.write(text.getBytes());

    }

    private static String decode(String encodedText, Map<String, Character> codeMap){
        String cur = "";
        StringBuilder text = new StringBuilder();
        for(char c: encodedText.toCharArray()){
            cur += c;
            char decoded = codeMap.getOrDefault(cur, (char) 0);
            if (decoded == 0) continue;
            cur = "";
            text.append(decoded);
        }
        return text.toString();
    }


}
