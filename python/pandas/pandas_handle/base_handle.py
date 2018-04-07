#coding=utf-8
from pandas import Series, DataFrame
import pandas as pd

#从内存中：
s = Series([100, "python", "java", "ruby"])
print s
print s.values, s.index

#自定义索引
s = Series([100, "python", "java", "ruby"], index=["n", "p", "j", "r"])
print s
s = Series({"name":"xiaoming", "id":123})
print s


#dataFrame内存构造
d = {"name":["tom" , "jack", "black"], "id":[1,2,3]}
df = DataFrame(d)
print df
df = DataFrame(d, index=[10,11,12])
print df
print df.columns
print df['name']  #是一个series
#增加列
df["age"] = 18
print df



#csv文件的处理
with open("a.csv") as f:
    while True:
        line = f.readline()
        if line is None or line == "":
            break
        print line

import csv
reader = csv.reader(open("a.csv"))
for line in reader:
    print line

#读成dataframe
pd_csv = pd.read_csv("a.csv")
print pd_csv

#详细处理方法：
rs = pd_csv.sort_values(by=["python"], ascending=True)
print rs

#读excel
rs = pd.read_excel(r"D:\\test\\yes.xls")
print rs
rs = pd.ExcelFile(r"D:\\test\\yes.xls")
print rs.sheet_names
