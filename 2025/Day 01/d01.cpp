#include <iostream>

int main() {
    const int DIAL_NUMBERS = 100;
    int dial_position = 50;
    int zero_end_count = 0;
    int zero_any_count = 0;
    char direction;
    int clicks;

    while (std::cin >> direction >> clicks) {
        if (direction == 'R') {
            zero_any_count += (dial_position + clicks) / DIAL_NUMBERS;
            dial_position = (dial_position + clicks) % DIAL_NUMBERS;
        } else { // direction == 'L'
            if (dial_position == 0) {
                zero_any_count += clicks / DIAL_NUMBERS;
            } else if (clicks >= dial_position) {
                zero_any_count += 1 + (clicks - dial_position) / DIAL_NUMBERS;
            }

            dial_position = ((dial_position - clicks) % DIAL_NUMBERS + DIAL_NUMBERS) % DIAL_NUMBERS;
        }

        if (dial_position == 0) {
            ++zero_end_count;
        }
    }

    std::cout << zero_end_count << '\n';
    std::cout << zero_any_count << '\n';

    return 0;
}