#include <iostream>
#include <cstring>

const char PAPER = '@';
const char EMPTY = '.';
const int MAXDIM = 200;

int count_adjacent(char grid[MAXDIM][MAXDIM], int rows, int cols, int r, int c) {
    if (grid[r][c] != PAPER) return 0;

    const int DR[8] = {1, 1, 1, 0, -1, -1, -1, 0};
    const int DC[8] = {1, 0, -1, -1, -1, 0, 1, 1};
    int cnt = 0;

    for (int d = 0; d < 8; d++) {
        int nr = r + DR[d], nc = c + DC[d];
        if (nr < 0 || nr >= rows || nc < 0 || nc >= cols) continue;
        if (grid[nr][nc] == PAPER) cnt++;
    }

    return cnt;
}

int remove_and_count(char grid[MAXDIM][MAXDIM], int rows, int cols, int r, int c) {
    if (grid[r][c] != PAPER) return 0;

    const int DR[8] = {1, 1, 1, 0, -1, -1, -1, 0};
    const int DC[8] = {1, 0, -1, -1, -1, 0, 1, 1};
    int cnt = 1; // self
    grid[r][c] = EMPTY;

    for (int d = 0; d < 8; d++) {
        int nr = r + DR[d], nc = c + DC[d];
        if (nr < 0 || nr >= rows || nc < 0 || nc >= cols) continue;
        if (grid[nr][nc] == PAPER && count_adjacent(grid, rows, cols, nr, nc) < 4) {
            cnt += remove_and_count(grid, rows, cols, nr, nc);
        }
    }

    return cnt;
}

int main() {
    int rows = 0, cols;
    char grid[MAXDIM][MAXDIM];

    while (std::cin.getline(grid[rows], MAXDIM)) rows++;
    cols = strlen(grid[0]);
    
    int count_p1 = 0;
    int count_p2 = 0;

    for (int r = 0; r < rows; r++) {
        for (int c = 0; c < cols; c++) {
            if (grid[r][c] == PAPER && count_adjacent(grid, rows, cols, r, c) < 4) {
                count_p1++;
            }
        }
    }

    for (int r = 0; r < rows; r++) {
        for (int c = 0; c < cols; c++) {
            if (grid[r][c] == PAPER && count_adjacent(grid, rows, cols, r, c) < 4) {
                count_p2 += remove_and_count(grid, rows, cols, r, c);
            }
        }
    }

    std::cout << count_p1 << '\n';
    std::cout << count_p2 << '\n';

    return 0;
}