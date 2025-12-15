#include <iostream>
#include <sstream>
#include <string>
#include <vector>

struct Point {
    int x, y;

    Point(int _x, int _y) : x(_x), y(_y) {}
};

std::vector<std::string> split_by_delimiter(std::string str, char delim) {
    std::vector<std::string> words;
    std::istringstream iss(str);
    std::string word;

    while (std::getline(iss, word, delim)) {
        words.push_back(word);
    }

    return words;
}

int main() {
    std::string input_line;
    std::vector<Point> points;

    while (std::getline(std::cin, input_line)) {
        std::vector<std::string> numbers = split_by_delimiter(input_line, ',');
        Point point(stoi(numbers[0]), stoi(numbers[1]));
        points.push_back(point);
    }

    int num_points = points.size();
    long long ans_p1 = 0;
    long long ans_p2 = 0;

    for (int u = 0; u < num_points; u++) {
        for (int v = u + 1; v < num_points; v++) {
            long long dx = 1 + abs(points[u].x - points[v].x);
            long long dy = 1 + abs(points[u].y - points[v].y);
            long long area = dx * dy;
            ans_p1 = std::max(ans_p1, area);
        }
    }

    std::cout << ans_p1 << '\n';

    return 0;
}