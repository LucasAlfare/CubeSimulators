package oyyq.cube.simulator.cube;

import static oyyq.cube.simulator.util.Util.*;
import java.util.Arrays;
import java.util.Random;

public class CubeNNN {

    // faces and colors
    public static final int U                = 0;
    public static final int R                = 1;
    public static final int F                = 2;
    public static final int D                = 3;
    public static final int L                = 4;
    public static final int B                = 5;
    public static final int NONE             = 6;

    // axes
    public static final int X                = 0;
    public static final int Y                = 1;
    public static final int Z                = 2;

    private Cubie[][][]     cubies;
    private int             size;
    private boolean         hasBeenScrambled = false;

    public CubeNNN() {
        this(3);
    }

    public CubeNNN(int size) {
        this.size = size;
        this.cubies = new Cubie[size][size][size];
        reset();
    }

    public void reset() {
        hasBeenScrambled = false;
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                for (int z = 0; z < size; z++) {
                    Cubie cubie = new Cubie();
                    if (x == 0) {
                        cubie.setVisible(L, true);
                        cubie.setColor(L, L);
                    }
                    if (x == size - 1) {
                        cubie.setVisible(R, true);
                        cubie.setColor(R, R);
                    }
                    if (y == 0) {
                        cubie.setVisible(D, true);
                        cubie.setColor(D, D);
                    }
                    if (y == size - 1) {
                        cubie.setVisible(U, true);
                        cubie.setColor(U, U);
                    }
                    if (z == 0) {
                        cubie.setVisible(B, true);
                        cubie.setColor(B, B);
                    }
                    if (z == size - 1) {
                        cubie.setVisible(F, true);
                        cubie.setColor(F, F);
                    }
                    cubies[x][y][z] = cubie;
                }
            }
        }
    }

    public void scramble() {
        Random r = new Random();
        int n = 1000;
        for (int i = 0; i < n; i++) {
            int axis = r.nextInt(6);
            int shift = r.nextInt(size >> 1) + 1;
            int amount = r.nextInt(4);
            switch (axis) {
                case U:
                    rotateU(shift, amount);
                    break;
                case R:
                    rotateR(shift, amount);
                    break;
                case F:
                    rotateF(shift, amount);
                    break;
                case D:
                    rotateD(shift, amount);
                    break;
                case L:
                    rotateL(shift, amount);
                    break;
                case B:
                    rotateB(shift, amount);
                    break;
            }
        }
        hasBeenScrambled = true;
    }

    public boolean isSolvedByUser() {
        if (!hasBeenScrambled) {
            return false;
        }
        return isSolved();
    }

    public boolean isSolved() {
        int[] colors = new int[6];
        Arrays.fill(colors, -1);
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                for (int z = 0; z < size; z++) {
                    if (x == 0) {
                        if (colors[L] != -1 && colors[L] != cubies[x][y][z].colors[L]) {
                            return false;
                        }
                        colors[L] = cubies[x][y][z].colors[L];
                    }
                    if (y == 0) {
                        if (colors[D] != -1 && colors[D] != cubies[x][y][z].colors[D]) {
                            return false;
                        }
                        colors[D] = cubies[x][y][z].colors[D];
                    }
                    if (z == 0) {
                        if (colors[B] != -1 && colors[B] != cubies[x][y][z].colors[B]) {
                            return false;
                        }
                        colors[B] = cubies[x][y][z].colors[B];
                    }
                    if (x == size - 1) {
                        if (colors[R] != -1 && colors[R] != cubies[x][y][z].colors[R]) {
                            return false;
                        }
                        colors[R] = cubies[x][y][z].colors[R];
                    }
                    if (y == size - 1) {
                        if (colors[U] != -1 && colors[U] != cubies[x][y][z].colors[U]) {
                            return false;
                        }
                        colors[U] = cubies[x][y][z].colors[U];
                    }
                    if (z == size - 1) {
                        if (colors[F] != -1 && colors[F] != cubies[x][y][z].colors[F]) {
                            return false;
                        }
                        colors[F] = cubies[x][y][z].colors[F];
                    }
                }
            }
        }
        return true;
    }

    public int getSize() {
        return size;
    }

    public Cubie getCubie(int x, int y, int z) {
        return cubies[x][y][z];
    }

    public void rotateR(int shift, int amount) {
        for (int i = size - 1; shift-- > 0; i--) {
            rotateX(i, amount);
        }
    }

    public void rotateU(int shift, int amount) {
        for (int i = size - 1; shift-- > 0; i--) {
            rotateY(i, amount);
        }
    }

    public void rotateF(int shift, int amount) {
        for (int i = size - 1; shift-- > 0; i--) {
            rotateZ(i, amount);
        }
    }

    public void rotateL(int shift, int amount) {
        for (int i = 0; i < shift; i++) {
            rotateX(i, 4 - amount);
        }
    }

    public void rotateD(int shift, int amount) {
        for (int i = 0; i < shift; i++) {
            rotateY(i, 4 - amount);
        }
    }

    public void rotateB(int shift, int amount) {
        for (int i = 0; i < shift; i++) {
            rotateZ(i, 4 - amount);
        }
    }

    public void rotateX(int amount) {
        for (int i = 0; i < size; i++) {
            rotateX(i, amount);
        }
    }

    public void rotateX(int slice, int amount) {
        if (amount < 0) {
            amount = 4 - ((-amount) & 3);
        }
        amount = amount & 3;
        for (int i = 0; i < amount; i++) {
            int x = slice;
            if (x < 0) {
                x = 0;
            }
            if (x > size - 1) {
                x = size - 1;
            }
            for (int y = 0, yr = size - 1; y < size >> 1; y++, yr--) {
                for (int z = 0, zr = size - 1; z < (size + 1) >> 1; z++, zr--) {
                    Cubie tmp = cubies[x][y][z];
                    cubies[x][y][z] = cubies[x][zr][y];
                    cubies[x][zr][y] = cubies[x][yr][zr];
                    cubies[x][yr][zr] = cubies[x][z][yr];
                    cubies[x][z][yr] = tmp;
                }
            }
            for (int y = 0; y < size; y++) {
                for (int z = 0; z < size; z++) {
                    cubies[x][y][z].rotateX(1);
                }
            }
        }

    }

    public void rotateY(int amount) {
        for (int i = 0; i < size; i++) {
            rotateY(i, amount);
        }
    }

    public void rotateY(int slice, int amount) {
        if (amount < 0) {
            amount = 4 - ((-amount) & 3);
        }
        amount = amount & 3;
        for (int i = 0; i < amount; i++) {
            int y = slice;
            if (y < 0) {
                y = 0;
            }
            if (y > size - 1) {
                y = size - 1;
            }
            for (int x = 0, xr = size - 1; x < size >> 1; x++, xr--) {
                for (int z = 0, zr = size - 1; z < (size + 1) >> 1; z++, zr--) {
                    Cubie tmp = cubies[x][y][z];
                    cubies[x][y][z] = cubies[z][y][xr];
                    cubies[z][y][xr] = cubies[xr][y][zr];
                    cubies[xr][y][zr] = cubies[zr][y][x];
                    cubies[zr][y][x] = tmp;
                }
            }
            for (int x = 0; x < size; x++) {
                for (int z = 0; z < size; z++) {
                    cubies[x][y][z].rotateY(1);
                }
            }
        }

    }

    public void rotateZ(int amount) {
        for (int i = 0; i < size; i++) {
            rotateZ(i, amount);
        }
    }

    public void rotateZ(int slice, int amount) {
        if (amount < 0) {
            amount = 4 - ((-amount) & 3);
        }
        amount = amount & 3;
        for (int i = 0; i < amount; i++) {
            int z = slice;
            if (z < 0) {
                z = 0;
            }
            if (z > size - 1) {
                z = size - 1;
            }
            for (int x = 0, xr = size - 1; x < size >> 1; x++, xr--) {
                for (int y = 0, yr = size - 1; y < (size + 1) >> 1; y++, yr--) {
                    Cubie tmp = cubies[x][y][z];
                    cubies[x][y][z] = cubies[yr][x][z];
                    cubies[yr][x][z] = cubies[xr][yr][z];
                    cubies[xr][yr][z] = cubies[y][xr][z];
                    cubies[y][xr][z] = tmp;
                }
            }
            for (int x = 0; x < size; x++) {
                for (int y = 0; y < size; y++) {
                    cubies[x][y][z].rotateZ(1);
                }
            }
        }

    }

    public static class Cubie {

        private int   visibility = 0;
        private int[] colors     = new int[6];

        private Cubie() {
            this.visibility = 0;
            Arrays.fill(colors, NONE);
        }

        private void setColor(int idx, int color) {
            colors[idx] = color;
        }

        public int getColor(int idx) {
            return colors[idx];
        }

        private void setVisible(int idx, boolean visible) {
            final int mask = 1 << idx;
            visibility |= mask;
            if (!visible) {
                visibility ^= mask;
            }
        }

        public int getVisibility() {
            return visibility;
        }

        private void rotateX(int n) {
            for (int i = 0; i < n; i++) {
                rotate(colors, U, F, D, B);
                rotateVisibility(U, F, D, B);
            }
        }

        private void rotateY(int n) {
            for (int i = 0; i < n; i++) {
                rotate(colors, F, R, B, L);
                rotateVisibility(F, R, B, L);
            }
        }

        private void rotateZ(int n) {
            for (int i = 0; i < n; i++) {
                rotate(colors, U, L, D, R);
                rotateVisibility(U, L, D, R);
            }
        }

        private void rotateVisibility(int b1, int b2, int b3, int b4) {
            int tmp = (visibility >>> b1) & 1;
            setVisible(b1, ((visibility >>> b2) & 1) == 1);
            setVisible(b2, ((visibility >>> b3) & 1) == 1);
            setVisible(b3, ((visibility >>> b4) & 1) == 1);
            setVisible(b4, tmp == 1);
        }
    }
}
