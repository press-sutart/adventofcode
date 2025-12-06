#include <iostream>
#include <vector>
#include <map>
#include <algorithm>
using namespace std;

vector <int> v[2];
map <int, int> freq;
int sum = 0, length = 0;

void input()
{
    int a, b;
    
    while (cin >> a >> b)
    {
        v[0].push_back(a);
        v[1].push_back(b);
        length++;
    }
}

int main() {
    input();

    for (int i = 0; i < length; i++)
    {
        freq[v[1][i]] = freq.find(v[1][i]) == freq.end()
                        ? 1
                        : freq[v[1][i]] + 1;
    }

    for (int i = 0; i < length; i++)
    {
        int f = freq.find(v[0][i]) == freq.end()
                ? 0
                : freq[v[0][i]];
        sum += v[0][i] * f;
    }

    cout << sum << '\n';

    return 0;
}