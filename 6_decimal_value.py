#!/bin/python3

import math
import os
import random
import re
import sys

#
# Complete the 'plusMinus' function below.
#
# The function accepts INTEGER_ARRAY arr as parameter.
# Print the 6 decimal value of each fraction on a new line with  places after the decimal.

def plusMinus(arr):
    total = len(arr)
    n1 = 0
    n2 = 0
    n3 = 0
    for i in arr:
        if i > 0:
            n1 = n1 + 1
        elif i < 0:
            n2 = n2 + 1
        else:
            n3 = n3 + 1 
    print("{:.6f}".format(n1/total))
    print("{:.6f}".format(n2/total))
    print("{:.6f}".format(n3/total))
if __name__ == '__main__':
    n = int(input().strip())

    arr = list(map(int, input().rstrip().split()))

    plusMinus(arr)
