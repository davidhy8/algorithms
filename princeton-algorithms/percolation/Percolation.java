import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int N;
    private boolean[] open;
    private boolean[] connectTop;
    private boolean[] connectBottom;
    private WeightedQuickUnionUF uf;
    private boolean percolateFlag = false;


    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();

        this.N = n;
        uf = new WeightedQuickUnionUF(N * N);
        open = new boolean[N * N];
        connectTop = new boolean[N * N];
        connectBottom = new boolean[N * N];

        for (int i = 0; i < N * N; i++) {
            open[i] = false;
            connectBottom[i] = false;
            connectTop[i] = false;
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validateRowCol(row, col);
        int index = xyTo1D(row, col);
        open[index] = true;
        boolean top = false;
        boolean bottom = false;

        if (row > 1 && open[index - N]) {
            if (connectTop[uf.find(index - N)] || connectTop[uf.find(index)]) {
                top = true;
            }
            if (connectBottom[uf.find(index - N)] || connectBottom[uf.find(index)]) {
                bottom = true;
            }
            uf.union(index, index - N);
        }

        if (row < N && open[index + N]) {
            if (connectTop[uf.find(index + N)] || connectTop[uf.find(index)]) {
                top = true;
            }
            if (connectBottom[uf.find(index + N)] || connectBottom[uf.find(index)]) {
                bottom = true;
            }
            uf.union(index, index + N);
        }

        if (col > 1 && open[index - 1]) {
            if (connectTop[uf.find(index - 1)] || connectTop[uf.find(index)]) {
                top = true;
            }
            if (connectBottom[uf.find(index - 1)] || connectBottom[uf.find(index)]) {
                bottom = true;
            }
            uf.union(index, index - 1);
        }

        if (col < N && open[index + 1]) {
            if (connectTop[uf.find(index + 1)] || connectTop[uf.find(index)]) {
                top = true;
            }
            if (connectBottom[uf.find(index + 1)] || connectBottom[uf.find(index)]) {
                bottom = true;
            }
            uf.union(index, index + 1);
        }

        if (row == 1) {
            top = true;
        }
        if (row == N) {
            bottom = true;
        }

        connectTop[uf.find(index)] = top;
        connectBottom[uf.find(index)] = bottom;

        if (connectTop[uf.find(index)] && connectBottom[uf.find(index)]) {
            percolateFlag = true;
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validateRowCol(row, col);
        return open[xyTo1D(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validateRowCol(row, col);
        return connectTop[uf.find(xyTo1D(row, col))];
    }

    private void validateRowCol(int row, int col) {
        if (!(row >= 1 && row <= N && col >= 1 && col <= N)) {
            throw new IndexOutOfBoundsException("Index is not betwwen 1 and N");
        }
    }

    private int xyTo1D(int row, int col) {
        validateRowCol(row, col);
        return col + (row - 1) * N - 1;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        int count = 0;

        for (int i = 0; i < N * N; i++) {
            if (open[i]) {
                count += 1;
            }
        }

        return count;
    }

    // does the system percolate?
    public boolean percolates() {
        return percolateFlag;
    }

    // test client (optional)
    public static void main(String[] args) {
    }
}
