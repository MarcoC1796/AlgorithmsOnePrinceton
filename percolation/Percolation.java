/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] grid;
    private int[][] index;
    private int numOpen;
    private int gridSize;
    private WeightedQuickUnionUF uf;

    public Percolation(int n) { // create n-by-n grid, with all sites blocked
        gridSize = n;
        grid = new boolean[gridSize][gridSize];
        index = new int[n][n];
        numOpen = 0;
        uf = new WeightedQuickUnionUF(n * n + 2);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = false;
                index[i][j] = (i * n) + (j + 1);
            }
        }
    }

    public void open(int row, int col) { // open site (row, col) if it is not open already
        if ((0 < row && row <= gridSize) && (0 < col && col <= gridSize)) {
            if (!isOpen(row, col)) {
                grid[row - 1][col - 1] = true;
                numOpen++;
                if (row == 1) {
                    uf.union(0, index[0][col - 1]);
                    if (isOpen(row + 1, col))
                        uf.union(index[row - 1][col - 1], index[row][col - 1]);
                    if (col == 1) {
                        if (isOpen(1, 2)) {
                            uf.union(index[row - 1][col - 1], index[row - 1][col]);
                        }
                    }
                    else if (col == gridSize) {
                        if (isOpen(1, col - 1)) {
                            uf.union(index[row - 1][col - 1], index[row - 1][col - 2]);
                        }
                    }
                    else {
                        if (isOpen(1, col - 1)) {
                            uf.union(index[row - 1][col - 1], index[row - 1][col - 2]);
                        }
                        if (isOpen(1, col + 1)) {
                            uf.union(index[row - 1][col - 1], index[row - 1][col]);
                        }
                    }

                }
                else if (row == gridSize) {
                    uf.union(gridSize * gridSize + 1, index[row - 1][col - 1]);
                    if (isOpen(row - 1, col))
                        uf.union(index[row - 1][col - 1], index[row - 2][col - 1]);
                    if (col == 1) {
                        if (isOpen(gridSize, 2)) {
                            uf.union(index[row - 1][col - 1], index[row - 1][col]);
                        }
                    }
                    else if (col == gridSize) {
                        if (isOpen(gridSize, gridSize - 1)) {
                            uf.union(index[row - 1][col - 1], index[row - 1][col - 2]);
                        }
                    }
                    else {
                        if (isOpen(row, col - 1)) {
                            uf.union(index[row - 1][col - 1], index[row - 1][col - 2]);
                        }
                        if (isOpen(row, col + 1)) {
                            uf.union(index[row - 1][col - 1], index[row - 1][col]);
                        }
                    }
                }
                else {
                    if (isOpen(row - 1, col))
                        uf.union(index[row - 1][col - 1], index[row - 2][col - 1]);
                    if (isOpen(row + 1, col))
                        uf.union(index[row - 1][col - 1], index[row][col - 1]);
                    if (col == 1) {
                        if (isOpen(row, col + 1)) {
                            uf.union(index[row - 1][col - 1], index[row - 1][col]);
                        }
                    }
                    else if (col == gridSize) {
                        if (isOpen(row, col - 1)) {
                            uf.union(index[row - 1][col - 1], index[row - 1][col - 2]);
                        }
                    }
                    else {
                        if (isOpen(row, col - 1)) {
                            uf.union(index[row - 1][col - 1], index[row - 1][col - 2]);
                        }
                        if (isOpen(row, col + 1)) {
                            uf.union(index[row - 1][col - 1], index[row - 1][col]);
                        }
                    }
                }
            }
        }
        else
            throw new IllegalArgumentException();
    }

    public boolean isOpen(int row, int col) { // is site (row, col) open?
        if (!((1 <= row && row <= gridSize) || (1 <= col && col <= gridSize)))
            throw new IllegalArgumentException();
        return grid[row - 1][col - 1];
    }

    public boolean isFull(int row, int col) { // is site (row, col) full?
        boolean answer;
        if (!((1 <= row && row <= gridSize) || (1 <= col && col <= gridSize))) {
            throw new IllegalArgumentException();
        }
        else if (isOpen(row, col)) {
            answer = uf.connected(0, index[row - 1][col - 1]);
        }
        else {
            answer = false;
        }
        return answer;
    }

    public int numberOfOpenSites() { // number of open sites
        return numOpen;
    }

    public boolean percolates() { // does the system percolate?
        return uf.connected(0, gridSize * gridSize + 1);
    }

    public int[][] getIndex() {
        return index;
    }

    public static void main(String[] args) {
        Percolation pc = new Percolation(10);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                System.out.println(pc.getIndex()[i][j]);
            }
        }
    }
}
