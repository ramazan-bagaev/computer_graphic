public class DitherMatrix extends Matrix {


    public DitherMatrix(int size) throws Exception {
        super(size);
        init();
    }

    private void init() throws Exception {
        if (size < 2) return;
        int curSize = 2;
        Matrix curMatrix = new Matrix(curSize);
        curMatrix.set(0, 0, 0);
        curMatrix.set(1, 0, 2);
        curMatrix.set(0, 1, 3);
        curMatrix.set(1, 1, 1);
        while(true){
            if (size == curSize) break;
            if (curSize > size) throw new Exception();
            curSize = curSize * 2;
            Matrix newMatrix = new Matrix(curSize);
            initTopLeft(newMatrix, curMatrix);
            initTopRight(newMatrix, curMatrix);
            initBottomLeft(newMatrix, curMatrix);
            initBottomRight(newMatrix, curMatrix);
            curMatrix = newMatrix;
        }

        for(int y = 0; y < size; y++){
            for(int x = 0; x < size; x++){
                set(x, y, 256 * curMatrix.get(x, y)/(size * size));
            }
        }
    }

    private void initTopLeft(Matrix m, Matrix prev){
        for(int y = 0; y < prev.size; y++){
            for(int x = 0; x < prev.size; x++){
                m.set(x, y, 4 * prev.get(x, y));
            }
        }
    }

    private void initTopRight(Matrix m, Matrix prev){
        for(int y = 0; y < prev.size; y++){
            for(int x = 0; x < prev.size; x++){
                m.set(x + prev.size, y, 4 * prev.get(x, y) + 2);
            }
        }
    }

    private void initBottomLeft(Matrix m, Matrix prev){
        for(int y = 0; y < prev.size; y++){
            for(int x = 0; x < prev.size; x++){
                m.set(x, y + prev.size, 4 * prev.get(x, y) + 3);
            }
        }
    }

    private void initBottomRight(Matrix m, Matrix prev){
        for(int y = 0; y < prev.size; y++){
            for(int x = 0; x < prev.size; x++){
                m.set(x + prev.size, y + prev.size, 4 * prev.get(x, y) + 1);
            }
        }
    }
}
