import sys
a = [-2,-1]
maxx = -sys.maxsize - 1 
temp = 0
for i in range(len(a)):
    if temp + a[i] >= a[i]:
        temp = temp + a[i]
        if temp > maxx:
            maxx = temp
    else:
        temp = a[i]
        if temp > maxx:
            maxx = temp

print(maxx)
