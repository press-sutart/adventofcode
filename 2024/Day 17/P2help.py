# let a_i be the i-th least significant bit of register A
#   3 LSBs of A                      output
#   ===========                =================
#       000                     a[4]  a[3]    1
#       001                     a[5]  a[4]  a[3]
#       010                       0     0     1
#       011                     a[3]    1     1
#       100                    ~a[8]  a[7] ~a[6]
#       101                    ~a[9]  a[8]  a[7]
#       110                    ~a[6] ~a[5] ~a[4]
#       111                    ~a[7] ~a[6]  a[5]

def c(A, i):
    temp = A >> (i - 3)
    return temp & 1

def n(b):
    return 1 - b

def make_3bit(x, y, z):
    return (x << 2) + (y << 1) + z

def bruteforce_helper(A, out):
    #print("called bhelp A = ", A, " out = ", out)
    if not out:
        return [A]
    
    all_3bits = [
        make_3bit(  c(A, 4),    c(A, 3),         1),
        make_3bit(  c(A, 5),    c(A, 4),    c(A, 3)),
        make_3bit(       0,          0,          1),
        make_3bit(  c(A, 3),         1,          1),
        make_3bit(n(c(A, 8)),   c(A, 7),  n(c(A, 6))),
        make_3bit(n(c(A, 9)),   c(A, 8),    c(A, 7)),
        make_3bit(n(c(A, 6)), n(c(A, 5)), n(c(A, 4))),
        make_3bit(n(c(A, 7)), n(c(A, 6)),   c(A, 5))
    ]

    first_out = out[0]
    candidates = []

    for i in range(8):
        if first_out == all_3bits[i]:
            candidates += bruteforce_helper(A * 8 + i, out[1:])
    
    return candidates

def bruteforce(out):
    out.reverse()
    all_candidates = bruteforce_helper(0, out)
    all_candidates.sort()
    return all_candidates

output = [2, 4, 1, 2, 7, 5, 4, 1, 1, 3, 5, 5, 0, 3, 3, 0]
print(bruteforce(output))