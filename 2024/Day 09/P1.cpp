#include <fstream>
#include <iostream>
#include <string>
#include <vector>

int main() {
    std::ifstream input_stream("Day 09/input.txt");
    std::string disk_map;
    input_stream >> disk_map;
    input_stream.close();

    std::vector<long long> disk_files;
    long long file_index = 0;
    bool is_file = true;

    for (char ch : disk_map) {
        int block_size = ch - '0';
        for (int i = 0; i < block_size; i++) {
            disk_files.push_back(is_file ? file_index : -1);
        }

        if (is_file) {
            is_file = false;
            file_index++;
        } else {
            is_file = true;
        }
    }

    int left_i = 0;
    int right_i = (int)disk_files.size() - 1;
    long long checksum = 0;

    while (left_i <= right_i) {
        if (disk_files[left_i] == -1) {
            while (left_i <= right_i && disk_files[right_i] == -1)
                right_i--;
            if (left_i > right_i) break;
            checksum += disk_files[right_i] * left_i;
            left_i++;
            right_i--;
        } else {
            checksum += disk_files[left_i] * left_i;
            left_i++;
        }
    }

    std::cout << checksum;

    return 0;
}