# ok, let's try this in python

s = "./Day10-input.txt"
t = "./gija.txt"
u = "./zeno.txt"


adapters = [int(x) for x in open(u,"r",encoding="utf-8").read().splitlines()]
def num_combs(start):
	if start == len(adapters)-1:
		return 1
	i = start + 1
	result = 0
	while adapters[i] - adapters[start] < 4:
		result += num_combs(i)
		i += 1
