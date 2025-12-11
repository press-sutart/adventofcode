#include <algorithm>
#include <iostream>
#include <sstream>
#include <string>
#include <vector>

const int DIM = 3;
const int CONNECT_PAIRS = 1000;

struct Edge {
    int u, v; // endpoints
    long long w; // weight

    Edge(int _u, int _v, long long _w) : u(_u), v(_v), w(_w) {}

    bool operator <(const Edge& rhs) const {
        return w < rhs.w;
    }
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

int find_head(int u, std::vector<int> &parent) {
    if (u == parent[u]) return u;
    return parent[u] = find_head(parent[u], parent);
}

bool connect(Edge edge, std::vector<int> &parent, std::vector<int> &set_size) {
    int u = edge.u, v = edge.v;

    // merge smaller set (containing v) into larger set (containing u)
    int head_u = find_head(u, parent);
    int head_v = find_head(v, parent);

    if (head_u == head_v) return false;

    if (set_size[head_u] < set_size[head_v]) {
        std::swap(head_u, head_v);
    }

    parent[head_v] = head_u;
    set_size[head_u] += set_size[head_v];
    return true;
}

int main() {
    std::string input_line;
    std::vector<int> coords[DIM];

    while (std::getline(std::cin, input_line)) {
        std::vector<std::string> numbers = split_by_delimiter(input_line, ',');
        for (int d = 0; d < DIM; d++) {
            int coord = stoi(numbers[d]);
            coords[d].push_back(coord);
        }
    }

    int num_nodes = coords[0].size();
    int sets = num_nodes;
    std::vector<Edge> edge_list;
    std::vector<int> parent(num_nodes);
    std::vector<int> set_size(num_nodes, 1);

    for (int u = 0; u < num_nodes; u++) {
        parent[u] = u;

        for (int v = u + 1; v < num_nodes; v++) {
            long long squared_dist = 0LL;

            for (int d = 0; d < DIM; d++) {
                long long diff = coords[d][u] - coords[d][v];
                squared_dist += diff * diff;
            }

            Edge edge(u, v, squared_dist);
            edge_list.push_back(edge);
        }
    }

    std::sort(edge_list.begin(), edge_list.end());

    for (int i = 0; i < CONNECT_PAIRS; i++) {
        Edge edge = edge_list[i];
        if (connect(edge, parent, set_size)) {
            sets--;
        }
    }

    std::vector<int> set_sizes;

    for (int u = 0; u < num_nodes; u++) {
        if (parent[u] == u) {
            set_sizes.push_back(set_size[u]);
        }
    }

    std::sort(set_sizes.begin(), set_sizes.end(), std::greater<int>());

    long long ans_p1 = 1;

    for (int i = 0; i < 3; i++) {
        ans_p1 *= set_sizes[i];
    }

    int edge_index = CONNECT_PAIRS - 1;

    while (sets != 1) {
        edge_index++;
        Edge edge = edge_list[edge_index];
        if (connect(edge, parent, set_size)) {
            sets--;
        }
    }

    Edge final_edge = edge_list[edge_index];
    int final_u = final_edge.u, final_v = final_edge.v;
    int ans_p2 = coords[0][final_u] * coords[0][final_v];

    std::cout << ans_p1 << '\n';
    std::cout << ans_p2 << '\n';

    return 0;
}