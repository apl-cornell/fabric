#!/bin/bash
screen -S brokerStore -X stuff "run auction.InitBrokerStore
"
screen -S airlineAStore -X stuff "run auction.InitAStore
"
screen -S airlineBStore -X stuff "run auction.InitBStore
"
screen -S userStore -X stuff "run auction.InitUserStore
"
