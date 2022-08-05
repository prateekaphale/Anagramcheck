a = [1,2,-1,3,4,10,10,-10,-1]
c = 0
total = 0
for i in range(len(a)):
    if c + a[i] >= a[i]:
        c = c + a[i]
        if c > total:
            total = c
    else:
        c = a[i]
print(total)
