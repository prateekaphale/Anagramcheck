def anagram(inp1,inp2):
	inp1 = inp1.replace(' ','').lower()
	inp2 = inp2.replace(' ','').lower()
	count = True
	for i in range(len(inp1)):
		x = a.count(inp1[i])
		y = b.count(inp1[i])
		if x != y:
			count = False
	print(count)
a = "clint eastwood"
b = "old west action"
anagram(a,b)
