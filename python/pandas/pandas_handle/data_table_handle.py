#coding=utf-8
#
import matplotlib.pyplot  as plt
import pandas_datareader.tsp as tsp
tspreader = tsp.TSPReader(start='2015-10-1', end='2015-12-31')
print tspreader.read()
plt.plot(tspreader.read().index, tspreader.read()["C Fund"])
plt.show()