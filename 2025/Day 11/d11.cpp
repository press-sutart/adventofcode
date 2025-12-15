#include <iostream>
#include <sstream>
#include <string>
#include <unordered_map>
#include <vector>

std::vector<std::string> split_by_delimiter(std::string str, char delim) {
    std::vector<std::string> words;
    std::istringstream iss(str);
    std::string word;

    while (std::getline(iss, word, delim)) {
        words.push_back(word);
    }

    return words;
}

long long _count_paths(
    std::string dev,
    std::unordered_map<std::string, long long> &paths,
    std::unordered_map<std::string, std::vector<std::string>> &input_devices
) {
    if (paths.find(dev) != paths.end()) {
        return paths[dev];
    }

    long long ans = 0;

    for (std::string in : input_devices[dev]) {
        ans += _count_paths(in, paths, input_devices);
    }

    return paths[dev] = ans;
}

long long count_paths(
    std::string source, std::string sink,
    std::unordered_map<std::string, std::vector<std::string>> &input_devices
) {
    std::unordered_map<std::string, long long> paths;
    paths[source] = 1;
    return _count_paths(sink, paths, input_devices);
}

int main() {
    std::string input_line;
    std::unordered_map<std::string, std::vector<std::string>> output_devices;
    std::unordered_map<std::string, std::vector<std::string>> input_devices;

    while (std::getline(std::cin, input_line)) {
        std::vector<std::string> devices = split_by_delimiter(input_line, ' ');
        devices[0].pop_back();

        std::string curr_device = devices[0];
        devices.erase(devices.begin());

        output_devices[curr_device] = devices;
        input_devices[curr_device] = std::vector<std::string>();
    }

    output_devices["out"] = std::vector<std::string>();
    input_devices["out"] = std::vector<std::string>();

    for (auto it = output_devices.begin(); it != output_devices.end(); it++) {
        std::string dev = it->first;
        std::vector<std::string> outs = it->second;
        for (std::string out : outs) {
            input_devices[out].push_back(dev);
        }
    }

    long long ans_p1 = count_paths("you", "out", input_devices);
    long long ans_p2;
    long long test_mid = count_paths("dac", "fft", input_devices);

    if (test_mid == 0) {
        // svr -> fft -> dac -> out
        ans_p2 = count_paths("svr", "fft", input_devices)
                    * count_paths("fft", "dac", input_devices)
                    * count_paths("dac", "out", input_devices);
    } else {
        // svr -> dac -> fft -> out
        ans_p2 = count_paths("svr", "dac", input_devices)
                    * test_mid
                    * count_paths("fft", "out", input_devices);
    }

    std::cout << ans_p1 << '\n';
    std::cout << ans_p2 << '\n';

    return 0;
}