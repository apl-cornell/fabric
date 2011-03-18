#!/bin/bash
../../../../bin/fabc -worker brokerWorker -deststore brokerStore -publish-only broker_codebase/broker/*.fab 
echo "done"
sleep 1
../../../../bin/fabc -worker airlineAWorker -deststore airlineAStore -publish-only -addCodebase fab://brokerStore/34 -sourcepath airlineA_codebase airlineA_codebase/airlineA/*.fab InitA.fab
echo "done"
sleep 1
../../../../bin/fabc -worker airlineBWorker -deststore airlineBStore -publish-only -addCodebase fab://brokerStore/34 -sourcepath airlineB_codebase airlineB_codebase/airlineB/*.fab InitB.fab
echo "done"
sleep 1
../../../../bin/fabc -worker userWorker -deststore userStore -publish-only -addCodebase fab://brokerStore/34 -sourcepath user_codebase user_codebase/user/*.fab InitUser.fab
echo "done"
sleep 1
../../../../bin/fabc -worker brokerWorker -deststore brokerStore -publish-only AirlineExample.fab Main.fab -addCodebase fab://brokerStore/34
echo "done"
