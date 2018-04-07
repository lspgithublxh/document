#coding=utf-8
import pandas
import pandas_datareader.data as web, pandas_datareader.wb as wb
import pandas_datareader as pdr
import datetime
start = datetime.datetime(2010, 1, 1)
end = datetime.datetime(2013, 1, 27)

da = pdr.get_data_fred('GS10')
print da
print '-' * 40
gdp = web.DataReader('GDP', 'fred', start, end)
print gdp.ix['2013-01-01']

inflation = web.DataReader(['CPIAUCSL', 'CPILFESL'], 'fred', start, end)
print inflation
print '-'* 40
print inflation.head()
#世界银行
print '-'* 40
# mathces = wb.search('gdp.*capita.*const')
# dat = wb.download(indicator='NY.GDP.PCAP.KD', country=['US', 'CA', 'MX'], start=2005, end=2008)
# print dat
#基金数据
import pandas_datareader.tsp as tsp
tspreader = tsp.TSPReader(start='2015-10-1', end='2015-12-31')
print tspreader.read()
plt.plot(finace.index, finace["Open"])
# pandas_datareader.get_data_enigma('AAPL')
# print pandas_datareader.get_data_yahoo('AAPL')
#
# finace = pandas_datareader.data.DataReader("GOOG", data_source="yahoo")
# print finace.tail(3)

# s = web.DataReader("AAPL", "yahoo")
# print s

# f = web.DataReader('F', 'google', start, end)
#
# print f.ix['2010-01-04']
import os
# df = pdr.get_data_tiingo('GOOG', api_key=os.getenv('TIINGO_API_KEY'))
# print df