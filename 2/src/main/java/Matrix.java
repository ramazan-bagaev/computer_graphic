public class Matrix {

    protected int[] buf;
    protected int size;

    public Matrix(int size){
        this.size = size;
        buf = new int[size*size];
    }

    public void set(int x, int y, int value){
        if (x < 0 || x >= size) return;
        if (y < 0 || y >= size) return;
        buf[y * size + x] = value;
    }

    public int get(int x, int y){
        if (x < 0 || x >= size) return -1;
        if (y < 0 || y >= size) return -1;
        return buf[y*size + x];
    }

}
