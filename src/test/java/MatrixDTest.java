import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class MatrixDTest {

    @Test
    void additionMatrix() {
        var m1 = MatrixD
                .builder()
                .rows(2)
                .columns(2)
                .insert(
                        2, 2,
                        4, 1
                )
                .build();
        var m2 = MatrixD
                .builder()
                .rows(2)
                .columns(2)
                .insert(
                        2, 3,
                        1, 4
                )
                .build();

        assertArrayEquals(new double[] {
                4, 5,
                5, 5
        }, m1.addition(m2).getMatrix());
    }

    @Test
    void scalarMultiplication() {
        var matrixD = MatrixD
                .builder()
                .rows(2)
                .columns(2)
                .insert(
                        2, 2,
                        4, 1
                )
                .build();
        assertArrayEquals(new double[] {
                1, 1,
                2, 0.5
        }, matrixD.multiplication(0.5).getMatrix());
    }

    @Test
    void matrixMultiplication() {
        var m1 = MatrixD
                .builder()
                .rows(2)
                .columns(2)
                .insert(
                        2, 2,
                        4, 1
                )
                .build();
        var m2 = MatrixD
                .builder()
                .rows(2)
                .columns(2)
                .insert(
                        2, 3,
                        1, 4
                )
                .build();
        assertArrayEquals(new double[] {
                6, 14,
                9, 16
        }, m1.multiplication(m2).getMatrix());
    }


    @Test
    void transposeMatrix() {
        var matrixD = MatrixD
                .builder()
                .rows(2)
                .columns(4)
                .insert(
                        4, 4, -2, -3,
                        3, -1, -1, 2
                        )
                .build();
        assertArrayEquals(new double[] {
                4, 3,
                4, -1,
                -2, -1,
                -3, 2
        }, matrixD.transpose().getMatrix());
    }

}
