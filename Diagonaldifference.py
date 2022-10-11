def diagonalDifference(a):
    a1 = []
    a2 = []
    counter = len(a) - 1
    for i in range(len(a)):
        x1 = a[i]
        x2 = a[i]
        a2.append(x2[counter])
        counter = counter - 1
        a1.append(x1[i])
    return abs(sum(a1) - sum(a2))
a = [[1,2,3],[4,5,6],[9,8,9]]
print(diagonalDifference(a))
