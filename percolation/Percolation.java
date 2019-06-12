/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] grid;
    private int[][] index;
    private boolean[][] fill;
    private int numOpen;
    private int gridSize;
    private WeightedQuickUnionUF uf;

    public Percolation(int n) { // create n-by-n grid, with all sites blocked
        gridSize = n;
        grid = new boolean[gridSize][gridSize];
        fill = new boolean[gridSize][gridSize];
        index = new int[n][n];
        numOpen = 0;
        uf = new WeightedQuickUnionUF(n * n + 2);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = false;
                fill[i][j] = false;
                index[i][j] = (i * n) + (j + 1);
            }
        }
    }

    public void open(int row, int col) { // open site (row, col) if it is not open already
        if (!((0 < row && row <= gridSize) || (0 < col && col <= gridSize)))
            throw new IllegalArgumentException();
        if (!isOpen(row, col)) {
            grid[row - 1][col - 1] = true;
            numOpen++;
            if (row == 1) {
                if (col == 1) {
                    uf.union(0, 1);
                    if (isOpen(1, 2)) {
                        uf.union(1, 2);
                    }
                    if (isOpen(2, 1)) {
                        uf.union(1, gridSize + 1);
                    }
                }
                else if (col == gridSize) {
                    uf.union(0, gridSize);
                    if (isOpen(1, gridSize - 1)) {
                        uf.union(gridSize, gridSize - 1);
                    }
                    if (isOpen(2, gridSize)) {
                        uf.union(gridSize, 2 * gridSize);
                    }
                }
                else {
                    uf.union(0, index[0][col - 1]);
                    if (isOpen(1, col - 1)) {
                        uf.union(index[0][col - 1], index[0][col - 2]);
                    }
                    if (isOpen(2, col)) {
                        uf.union(index[0][col - 1], index[1][col - 1]);
                    }
                    if (isOpen(2, col + 1)) {
                        uf.union(index[0][col - 1], index[0][col]);
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

    public boolean isOpen(int row, int col) { // is site (row, col) open?
        if (!((1 <= row && row <= gridSize) || (1 <= col && col <= gridSize)))
            throw new IllegalArgumentException();
        return grid[row - 1][col - 1];
    }

    public boolean isFull(int row, int col) { // is site (row, col) full?
        if (!((1 <= row && row <= gridSize) || (1 <= col && col <= gridSize)))
            throw new IllegalArgumentException();
        return fill[row - 1][col - 1];
    }

    public int numberOfOpenSites() { // number of open sites
        return numOpen;
    }

    public boolean percolates() { // does the system percolate?
        return uf.connected(0, gridSize * gridSize + 1);
    }

    public static void main(String[] args) {
        Percolation pc = new Percolation(10);
        for (int i = 1; i < 11; i++) {
            pc.open(i, 5);
        }
        System.out.print(pc.percolates());
    }
}
