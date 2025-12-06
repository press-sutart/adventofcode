#include <iostream>
#include <vector>
#include <algorithm>
using namespace std;

vector <int> v[2];
int sum_d = 0;

void input()
{
    int a, b;
    
    while (cin >> a >> b)
    {
        v[0].push_back(a);
        v[1].push_back(b);
    }
}

int main()
{
    input();

    sort(v[0].begin(), v[0].end());
    sort(v[1].begin(), v[1].end());

    for (int i = 0; i < (int)v[0].size(); i++)
    {
        sum_d += abs(v[0][i] - v[1][i]);
    }

    cout << sum_d << '\n';

    return 0;
}