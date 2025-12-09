#include <algorithm>
#include <iostream>
#include <string>
#include <utility>
#include <vector>

typedef std::pair<long long, long long> pll;

bool cmp(pll lhs, pll rhs) {
    if (lhs.first == rhs.first) {
        return lhs.second > rhs.second;
    }
    return lhs.first < rhs.first;
}

int main() {
    std::string input_line;
    std::vector<pll> endpoints;

    while (std::getline(std::cin, input_line) && input_line.length() > 0) {
        size_t hyphen_pos = input_line.find('-');
        long long range_start = stoll(input_line.substr(0, hyphen_pos));
        long long range_end = stoll(input_line.substr(hyphen_pos + 1));
        endpoints.emplace_back(range_start, 1);
        endpoints.emplace_back(range_end, -1);
    }

    // merge input ranges to form disjoint ranges
    sort(endpoints.begin(), endpoints.end(), cmp);
    long long layer = 0;
    long long start = -1;
    std::vector<pll> ranges;

    for (pll endpoint : endpoints) {
        if (layer == 0 && endpoint.second == 1) {
            start = endpoint.first;
        }

        else if (layer == 1 && endpoint.second == -1) {
            ranges.emplace_back(start, endpoint.first);
        }

        layer += endpoint.second;
    }

    int ans_p1 = 0;
    long long id;

    while (std::cin >> id) {
        // first range strictly after id
        std::vector<pll>::iterator it = std::lower_bound(
            ranges.begin(), ranges.end(),
            std::make_pair(id + 1, LONG_LONG_MIN)
        );

        if (it == ranges.begin()) continue;

        // range either contains id or strictly before id
        it = prev(it);
        if (it->first <= id && id <= it->second) {
            ans_p1++;
        }
    }

    long long ans_p2 = 0;

    for (pll range : ranges) {
        long long len = range.second - range.first + 1;
        ans_p2 += len;
    }

    std::cout << ans_p1 << '\n';
    std::cout << ans_p2 << '\n';

    return 0;
}