class queue():
	"""docstring for ClassName"""
	def __init__(self):
		self.items = []
	def enqueue(self,item):
		self.items.insert(0,item)
	def show(self):
		print(self.items)
	def dequeue(self):
		print(self.items.pop())
	def size(self):
		print(len(self.items))
q = queue()
q.size()
q.enqueue(12)
q.enqueue(13)
q.enqueue(14)
q.show()
q.dequeue()
q.show()
q.dequeue()
q.show()
q.dequeue()
q.show()
