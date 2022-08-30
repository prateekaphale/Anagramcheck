class stack:
	def __init__(self):
		self.items = []
	def push(self,item):
		self.items.append(item)
	def pop(self):
		a = "print"
		if a == "print":
			return self.items.pop()
	def peek(self):
		return self.items[len(self.items)-1]
	def size(self):
		return len(self.items)
	def show(self):
		return self.items
s = stack()
s.push(1)
s.push(2)
s.push(5)
print(s.show())
s.pop()
s.push(50)
print(s.peek())

