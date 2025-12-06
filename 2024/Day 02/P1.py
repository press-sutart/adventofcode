from functools import partial

def check_adjacent(f, vals):
    n = len(vals)
    res = True
    for i in range(n - 1):
        res = res and f(vals[i], vals[i + 1])
    return res

def is_safe(vals):
    is_increasing = partial(check_adjacent, lambda x, y: x < y)
    is_decreasing = partial(check_adjacent, lambda x, y: x > y)
    has_small_difference = partial(check_adjacent, lambda x, y: abs(x - y) <= 3)
    return (is_increasing(vals) or is_decreasing(vals)) and has_small_difference(vals)

cnt = 0

for line in open("Day 02/input.txt", "r"):
    report = list(map(lambda x: int(x), line.split()))
    if is_safe(report):
        cnt = cnt + 1

print(cnt)