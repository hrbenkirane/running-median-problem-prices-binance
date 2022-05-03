# REST API Binance Crypto Infinite Median

### To get all the symbols
http://localhost:8080/symbols
### To get the information about a defined symbol
* http://localhost:8080/{symbol} 
* The symbol name must be in lowercase e.g. btcusdt instead of BTCUSDT
#### For example:
http://localhost:8080/btcusdt
#### The output of this request will be:
{ "name":"btcusdt",
  "recentPrice":41574.17,
  "recentMedian":41550.01,
  "numberOfData":376
}
* name: The name of the symbol
* NumberOfData: The number of data that have been seen for that symbol so far.
* recentMedian: The infinite median of that symbol's prices.
* recentPrice: The most recent price seen for that symbol.

## Median Algorithm

We maintain two heaps, min-heap and max-heap. When a number comes, we first compare it 
with the current median and put it to the appropriate heap.

if the new double value is less than the current median, we put it to the max-heap 
else we put it in the min-heap. Then we make sure that min und max heap properties are 
maintained. 

Next is to check whether the size difference between two heaps are more than one.
If it is, we will take the top one out of the large heap and put it to the other.
Then again we have to make sure the two heaps are preserving their heap properties. 
(general heap properties + min/max at the top of each heap)
At the end we will calculate the median, if the two heaps are in same size the median 
should be the (top value of minHeap + top value of maxHeap)/2. 

If the two heaps are unbalanced, the median should be the top value of the large heap.
Then we will move on to the next number in the stream.

### Hashmap
Hashmap implementation can be found under:
* src/main/com/cwt/symbolinfinitemedian/util/RBHashmap


## Run the application from the Commande Line
Navigate to the root of the project via command line and execute the command:
* mvnw spring-boot:run


