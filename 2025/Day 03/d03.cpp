#include <algorithm>
#include <iostream>
#include <string>

long long solve(std::string bank, int batteries) {
    int len = bank.length();
    int left_pt = 0;
    int right_pt = 0;
    long long val = 0;
    int digit_count[10];
    std::fill(digit_count, digit_count + 10, 0);

    for (int i = 0; i < batteries; i++) {
        // advance right pointer as far right as possible
        while (right_pt <= len - batteries + i) {
            digit_count[bank[right_pt] - '0']++;
            right_pt++;
        }

        // greedily choose max available digit in range
        int max_digit = 9;
        while (max_digit > 0 && digit_count[max_digit] == 0) {
            max_digit--;
        }
        val = 10LL * val + max_digit;

        // advance left pointer past first occurrence of max digit
        while (bank[left_pt] - '0' != max_digit) {
            digit_count[bank[left_pt] - '0']--;
            left_pt++;
        }
        digit_count[max_digit]--;
        left_pt++;
    }

    return val;
}

int main() {
    long long sum_p1 = 0, sum_p2 = 0;
    std::string bank;

    while (std::cin >> bank) {
        sum_p1 += solve(bank, 2);
        sum_p2 += solve(bank, 12);
    }

    std::cout << sum_p1 << '\n';
    std::cout << sum_p2 << '\n';

    return 0;
}