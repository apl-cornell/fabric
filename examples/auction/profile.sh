#!/bin/bash
for i in $(jot $1) 
do 
	echo "Running Auction without worker cache ($i)"
	rm -rf .brokerWorker_cache/ 
	bin/start-worker --name brokerWorker \
		time run fab://brokerStore/35/auction.Main 2>&1 | grep time 
done
for i in $(jot $1) 
do 
	echo "Running Auction with worker cache ($i)"
	bin/start-worker --name brokerWorker \
		time run fab://brokerStore/35/auction.Main 2>&1 | grep time 
done
for i in $(jot $1) 
do 
	ant clean 2>&1 > /dev/null
	echo "Publishing Broker's Auction code ($i)"
	ant publish-broker 2>&1|grep complete
	echo "Publishing AirlineA's Auction code ($i)"
	ant publish-airlineA 2>&1|grep complete
	echo "Publishing AirlineB's Auction code ($i)"
	ant publish-airlineB 2>&1|grep complete
	echo "Publishing User's Auction code ($i)"
	ant publish-user 2>&1|grep complete
done
for i in $(jot $1) 
do 
	ant clean 2>&1 > /dev/null
	echo "Building all Auction code locally ($i)"
	START=$(date +%s)
	ant build-local 2>&1|grep complete
	END=$(date +%s)
	DIFF=$(echo "($END - $START)*1000" | bc)
	echo "Total build time: $DIFF ms"
done
