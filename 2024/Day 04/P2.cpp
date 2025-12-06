#include <fstream>
#include <iostream>
#include <string>
#include <vector>

int main()
{
    std::ifstream infile("Day 04/input.txt");
    std::vector<std::string> puzzle;
    std::string temp;

    while (infile >> temp)
    {
        puzzle.push_back(temp);
    }

    const int rows = puzzle.size();
    const int cols = puzzle[0].length();
    int cnt = 0;
    
    for (int r = 1; r < rows - 1; r++)
    {
        for (int c = 1; c < cols - 1; c++)
        {
            bool cond_centre = puzzle[r][c] == 'A';
            bool cond_diag1 =
                (puzzle[r-1][c-1] == 'M' && puzzle[r+1][c+1] == 'S') ||
                (puzzle[r-1][c-1] == 'S' && puzzle[r+1][c+1] == 'M');
            bool cond_diag2 =
                (puzzle[r-1][c+1] == 'M' && puzzle[r+1][c-1] == 'S') ||
                (puzzle[r-1][c+1] == 'S' && puzzle[r+1][c-1] == 'M');
            if (cond_centre && cond_diag1 && cond_diag2)
                cnt++;
        }
    }

    std::cout << cnt << "\n";

    return 0;
}