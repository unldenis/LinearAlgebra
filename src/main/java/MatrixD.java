import java.text.*;
import java.util.*;

public class MatrixD {

    public static final MatrixD IDENTITY = new MatrixD(1, 1, 1D);

    private int rows;
    private int columns;
    private double[] matrix;


    public MatrixD(int rows, int columns, Double... elements) {
        this.rows = rows;
        this.columns = columns;
        this.matrix = new double[rows * columns];
        for(int j = 0; j < matrix.length; j++) {
            matrix[j] = elements[j];
        }
    }

    private double getAt(int row, int column) {
        return matrix[getIndexAt(row, column, columns)];
    }

    private boolean checkSize(MatrixD o) {
        return rows == o.rows && columns == o.columns;
    }

    /**
     * @param o - the other matrix
     * @return this matrix updated
     */
    public MatrixD addition(MatrixD o) {
        if(!checkSize(o)) {
            throw new IllegalArgumentException("Matrix different lenght");
        }
        for(int j = 0; j < matrix.length; j++) {
            matrix[j] = matrix[j] + o.matrix[j];
        }
        return this;
    }

    /**
     * @param scalar
     * @return this matrix updated
     */
    public MatrixD multiplication(double scalar) {
        for(int j = 0; j < matrix.length; j++) {
            matrix[j] = matrix[j] * scalar;
        }
        return this;
    }

    /**
     * @param o - the other matrix
     * @return this matrix updated
     */
    public MatrixD multiplication(MatrixD o) {
        if(columns != o.rows) {
            throw new IllegalArgumentException("Matrix different columns / rows");
        }
        final int R = rows,
                C = o.columns;
        double[] result = new double[R * C];

        for(int row = 0; row < R; row++) {
            for(int column = 0; column < C; column++) {

                // position in result matrix
                double res = 0d;
                int i = 0;
                while(i < columns) {
                    double r0 = getAt(row, i);
                    double c0 = o.getAt(i, column);
                    res += r0 * c0;
                    i++;
                }
                result[getIndexAt(row, column, C)] = res;
            }
        }
        this.rows = R;
        this.columns = C;
        this.matrix = result;
        return this;
    }

    /**
     * @return this matrix updated
     */
    public MatrixD transpose() {
        final int C = columns, R = rows;
        double[][] transpose = new double[C][R];

        // put
        for(int r = 0; r < transpose.length; r++) {
            for(int c = 0; c < transpose[r].length; c++) {
                transpose[r][c] = getAt(c, r);
            }
        }

        // update
        {
            int i = 0;
            for(double[] r: transpose) {
                for(double c: r) {
                    matrix[i++] = c;
                }
            }
        }
        columns = R;
        rows = C;

        return this;
    }


    public String prettyPrint(int spaces) {
        final StringBuilder sb = new StringBuilder();
        DecimalFormat format = new DecimalFormat("0.#");
        for(int i = 0; i < rows; i ++) {
            StringJoiner joiner = new StringJoiner(" ".repeat(spaces));
            sb.append("[");
            for(int j = 0; j < columns; j++) {
                joiner.add(format.format(getAt(i, j)));
            }
            sb.append(joiner).append("]").append("\n");
        }
        return sb.toString();
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public double[] getMatrix() {
        return Arrays.copyOf(matrix, matrix.length);
    }

    @Override
    public String toString() {
        return prettyPrint(4);
    }

    private static int getIndexAt(int row, int column, int COLUMNS) {
        return column + (row * COLUMNS);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private int rows = -1;
        private int columns = -1;
        private final List<Double> matrix = new ArrayList<>();

        public Builder() {
        }

        public Builder rows(int rows) {
            this.rows = rows;
            return this;
        }

        public Builder columns(int columns) {
            this.columns = columns;
            return this;
        }

        public Builder insert(double... elements) {
            for(double e: elements) {
                matrix.add(e);
            }
            return this;
        }

        public MatrixD build() {
            if(rows + columns < 2) {
                throw new IllegalArgumentException("Matrix min 1 * 1");
            }
            return new MatrixD(rows, columns, matrix.toArray(new Double[0]));

        }

    }

}
