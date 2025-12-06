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
    const std::string target = "XMAS";
    const int tlen = target.length();
    const int dirs = 8;
    const int dr[8] = {1, 1, 0, -1, -1, -1, 0, 1};
    const int dc[8] = {0, 1, 1, 1, 0, -1, -1, -1};
    int cnt = 0;
    
    for (int r = 0; r < rows; r++)
    {
        for (int c = 0; c < cols; c++)
        {
            for (int d = 0; d < dirs; d++)
            {
                int rr = r;
                int cc = c;
                int i = 0;

                while (i < tlen &&
                       0 <= rr && rr < rows &&
                       0 <= cc && cc < cols &&
                       puzzle[rr][cc] == target[i])
                {
                    i++;
                    rr += dr[d];
                    cc += dc[d];
                }

                if (i == tlen)
                {
                    cnt++;
                }
            }
        }
    }

    std::cout << cnt << "\n";

    return 0;
}