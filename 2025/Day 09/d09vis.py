import sys
import matplotlib.pyplot as plt

x = []
y = []

for line in sys.stdin:
    words = line.split(',')
    x.append(int(words[0]))
    y.append(int(words[1]))

x.append(x[0])
y.append(y[0])

_, ax = plt.subplots()
ax.set_aspect('equal')
plt.plot(x, y)
plt.savefig('2025/Day 09/plot.png')
