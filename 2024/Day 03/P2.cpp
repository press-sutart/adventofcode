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

    std::string muls_pattern = "mul\\([0-9]{1,3},[0-9]{1,3}\\)";
    std::string dos_pattern = "do\\(\\)";
    std::string donts_pattern = "don't\\(\\)";
    std::string ops_pattern = muls_pattern + "|" +
                              dos_pattern + "|" +
                              donts_pattern;

    std::regex muls_regex(muls_pattern);
    std::regex dos_regex(dos_pattern);
    std::regex donts_regex(donts_pattern);
    std::regex ops_regex(ops_pattern);
    auto ops_begin = std::sregex_iterator
    (
        input.begin(), input.end(), ops_regex
    );
    auto ops_end = std::sregex_iterator();

    int muls_sum = 0;
    bool is_do = true;

    for (auto i = ops_begin; i != ops_end; i++)
    {
        std::string ops_match_str = (*i).str();

        if (std::regex_match(ops_match_str, dos_regex))
        {   // is a do() instruction
            is_do = true;
        }
        else if (std::regex_match(ops_match_str, donts_regex))
        {   // is a don't() instruction
            is_do = false;
        }
        else if (is_do)
        {   // is a mul(x,y) instruction which only runs if enabled
            std::regex nums_regex("[0-9]{1,3}");
            auto nums_begin = std::sregex_iterator
            (
                ops_match_str.begin(), ops_match_str.end(), nums_regex
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
    }

    std::cout << muls_sum << "\n";

    return 0;
}