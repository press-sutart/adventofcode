#include <iostream>
#include <sstream>
#include <string>
#include <vector>

int main() {
    std::vector<std::string> input_lines;
    std::string input_line;
    while (std::getline(std::cin, input_line)) {
        input_lines.push_back(input_line);
    }

    /* ==================== PART 1 ==================== */
    int numbers_per_op = input_lines.size() - 1;
    std::vector<long long> numbers[numbers_per_op];
    std::vector<char> operations;

    for (int i = 0; i < numbers_per_op + 1; i++) {
        std::istringstream iss(input_lines[i]);

        if (i == numbers_per_op) {
            char op;
            while (iss >> op) {
                operations.push_back(op);
            }
        } else {
            long long x;
            while (iss >> x) {
                numbers[i].push_back(x);
            }
        }
    }

    int n = operations.size();
    long long ans_p1 = 0;

    for (int i = 0; i < n; i++) {
        long long res;

        if (operations[i] == '+') {
            res = 0;
            for (int j = 0; j < numbers_per_op; j++) {
                res += numbers[j][i];
            }
        } else { // '*'
            res = 1;
            for (int j = 0; j < numbers_per_op; j++) {
                res *= numbers[j][i];
            }
        }

        ans_p1 += res;
    }

    std::cout << ans_p1 << '\n';

    /* ==================== PART 2 ==================== */

    int height = input_lines.size();
    int width = input_lines[0].length();
    long long ans_p2 = 0;
    std::vector<long long> terms;

    for (int c = width - 1; c >= 0; c--) {
        int v = 0;
        for (int r = 0; r < height - 1; r++) {
            char ch = input_lines[r][c];
            if (ch == ' ') continue;
            v = 10 * v + ch - '0';
        }
        terms.push_back(v);

        char ch = input_lines[height - 1][c];
        if (ch == ' ') continue;

        long long res;
        if (ch == '+') {
            res = 0;
            for (long long x : terms) {
                res += x;
            }
        } else { // '*'
            res = 1;
            for (long long x : terms) {
                res *= x;
            }
        }

        ans_p2 += res;
        terms.clear();
        c--; // skip 1 column
    }

    std::cout << ans_p2 << '\n';

    return 0;
}