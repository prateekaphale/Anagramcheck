#n number of rooms and c coins.
a = [1,2,3,4,5,6,7,8,9,10,11,12,13]
k = 57
i = 0
j = 1
current_sum = a[i]
while current_sum != k:
	current_sum  = current_sum + a[j] 
	if current_sum < k:
		j = j + 1
	elif current_sum > k:
		i = i + 1
		j = i + 1
		current_sum = a[i]
	else:
		print(a[i],a[j])
		break
