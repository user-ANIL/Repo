# RestTemplate

According to your values configured inside application.properties

time.period.refresh : Needed time to refresh and record a new value. 

time.avg : Time interval to perform calculations.

3 values are shown in the "time.avg / 3" period of time :

/prices/eth

/prices/btc

Average values shown in page as time.avg , time.type : 

/average/eth

/average/btc

Average value is calculated in the period of time.period.refresh
and overriden on first value after avg time passed.

#frontend will be configured
