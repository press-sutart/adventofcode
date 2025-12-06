#include <iostream>
#include <regex>
#include <string>

int main()
{
    std::string input = "";
    std::string temp;

    while (std::cin >> temp)
    {
        input = input + temp + "\n";
    }

    std::regex muls_regex("mul\\([0-9]{1,3},[0-9]{1,3}\\)");
    auto muls_begin = std::sregex_iterator
    (
        input.begin(), input.end(), muls_regex
    );
    auto muls_end = std::sregex_iterator();
    int muls_sum = 0;

    for (auto i = muls_begin; i != muls_end; i++)
    {
        std::string muls_match_str = (*i).str();
        std::regex nums_regex("[0-9]{1,3}");
        auto nums_begin = std::sregex_iterator
        (
            muls_match_str.begin(), muls_match_str.end(), nums_regex
        );
        auto nums_end = std::sregex_iterator();
        int muls_product = 1;

        for (auto j = nums_begin; j != nums_end; j++)
        {
            std::string nums_match_str = (*j).str();
            int num = std::stoi(nums_match_str);
            muls_product = muls_product * num;
        }

        muls_sum = muls_sum + muls_product;
    }

    std::cout << muls_sum << "\n";

    return 0;
}