#include <algorithm>
#include <fstream>
#include <iostream>
#include <queue>
#include <string>
#include <vector>

inline long long triangle(long long n) {
    return n * (n + 1) / 2;
}

int main() {
    typedef std::priority_queue<
        long long,
        std::vector<long long>,
        std::greater<long long>
    > min_priority_queue_ll;

    std::ifstream input_stream("Day 09/input.txt");
    std::string disk_map;
    input_stream >> disk_map;
    input_stream.close();

    std::vector<long long> file_size;
    std::vector<long long> file_location;
    min_priority_queue_ll free_locations[10];
    long long curr_location = 0;
    bool is_file = true;

    for (char ch : disk_map) {
        int block_size = ch - '0';

        if (is_file) {
            file_size.push_back(block_size);
            file_location.push_back(curr_location);
        } else {
            free_locations[block_size].push(curr_location);
        }

        curr_location += block_size;
        is_file = !is_file;
    }

    // move files
    int file_cnt = file_size.size();
    for (int file_id = file_cnt - 1; file_id >= 0; file_id--) {
        int this_size = file_size[file_id];
        int this_location = file_location[file_id];
        int move_to_location = this_location;
        int location_width = -1;

        for (int free_width = this_size; free_width < 10; free_width++) {
            if (
                !free_locations[free_width].empty() &&
                free_locations[free_width].top() < move_to_location
            ) {
                move_to_location = free_locations[free_width].top();
                location_width = free_width;
            }
        }

        if (move_to_location != this_location) {
            file_location[file_id] = move_to_location;
            free_locations[location_width].pop();

            if (location_width > this_size) {
                int new_free_width = location_width - this_size;
                int new_free_location = move_to_location + this_size;
                free_locations[new_free_width].push(new_free_location);
            }
        }
    }

    long long checksum = 0;

    for (long long file_id = 0; file_id < file_cnt; file_id++) {
        int this_size = file_size[file_id];
        int this_location = file_location[file_id];
        checksum += file_id *
                    ( triangle(this_location + this_size - 1) -
                      triangle(this_location - 1) );
    }

    std::cout << checksum;

    return 0;
}