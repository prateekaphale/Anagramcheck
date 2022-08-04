def anagram(inp1,inp2):
	inp1 = inp1.replace(' ','').lower()
	inp2 = inp2.replace(' ','').lower()
	return sorted(inp1) == sorted(inp2)

print(anagram('god','dog'))
