class deque():
	def __init__(self):
		self.items = []
	def addinfront(self,item):
		self.items.append(item)
	def addinrear(self,item):
		self.items.insert(0,item)
	def removefront(self):
		print(self.items.pop())
	def removerear(self):
		print(self.items.pop(0))
	def show(self):
		print(self.items)
d = deque()
d.addinfront(5)
d.addinfront(6)
d.addinfront(7)
d.addinfront(8)

d.show()
d.removefront()
d.removerear()
d.show()
