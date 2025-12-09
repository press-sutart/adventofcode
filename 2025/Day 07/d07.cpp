#include <cstring>
#include <iostream>

const int MAXDIM = 200;
const char SOURCE = 'S';
const char SPLIT = '^';
const char EMPTY = '.';

int main() {
    char grid[MAXDIM][MAXDIM];
    long long beam[MAXDIM][MAXDIM];
    int rows = 0, cols;
    long long ans_p1 = 0;
    long long ans_p2 = 0;

    while (std::cin.getline(grid[rows], MAXDIM)) rows++;
    cols = strlen(grid[0]);

    for (int c = 0; c < cols; c++) {
        beam[0][c] = grid[0][c] == SOURCE ? 1 : 0;
    }

    for (int r = 0; r < rows-1; r++) {
        // set next row to default: no beam
        for (int c = 0; c < cols; c++) {
            beam[r+1][c] = 0;
        }

        // use this row to create beams in next row
        for (int c = 0; c < cols; c++) {
            if (!beam[r][c]) continue;

            if (grid[r+1][c] == EMPTY) {
                beam[r+1][c] += beam[r][c];
                continue;
            }

            // otherwise, grid[r+1][c] has a splitter
            if (c-1 >= 0) beam[r+1][c-1] += beam[r][c];
            if (c+1 < cols) beam[r+1][c+1] += beam[r][c];
            ans_p1++;
        }
    }

    for (int c = 0; c < cols; c++) {
        ans_p2 += beam[rows-1][c];
    }

    std::cout << ans_p1 << '\n';
    std::cout << ans_p2 << '\n';

    return 0;
}