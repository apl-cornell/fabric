#!/bin/bash
while read line; do 
	if [[ $line == Running* ]]; then
		cache=$(expr "$line" : 'Running Auction \(with[a-z]*\)')
		num=$(expr "$line" : '[^0-9]*\([0-9]*\)')
		read next 
		runtime=$(expr "$next" : '[^0-9]*\([0-9]*\)')
		read next 
		compile=$(expr "$next" : '[^0-9]*\([0-9]*\)')
		read next 
		load=$(expr "$next" : '[^0-9]*\([0-9]*\)')
		read next 
		define=$(expr "$next" : '[^0-9]*\([0-9]*\)')
		read next 
		fsload=$(expr "$next" : '[^0-9]*\([0-9]*\)')
		echo "RUN $cache CACHE $num",$runtime,$compile,$load,$define,$fsload
	elif [[ $line == Publishing* ]]; then
		num=$(expr "$line" : '[^0-9]*\([0-9]*\)')
		read next 
		read next 
		broker=$(expr "$next" : '[^0-9]*\([0-9]*\)')
		read next 
		read next 
		read next 
		read next 
		airlineA=$(expr "$next" : '[^0-9]*\([0-9]*\)')
		read next 
		read next 
		read next 
		read next 
		airlineB=$(expr "$next" : '[^0-9]*\([0-9]*\)')
		read next 
		read next 
		read next 
		read next 
		user=$(expr "$next" : '[^0-9]*\([0-9]*\)')
		read next 
		echo "PUBLISH RUN $num",$broker,$airlineA,$airlineB,$user
	elif [[ $line == Building* ]]; then
		num=$(expr "$line" : '[^0-9]*\([0-9]*\)')
		read next 
		fabil=$(expr "$next" : '[^0-9]*\([0-9]*\)')
		read next 
		fabric=$(expr "$next" : '[^0-9]*\([0-9]*\)')
		read next 
		read next 
		total=$(expr "$next" : '[^0-9]*\([0-9]*\)')
		echo "BUILD LOCAL RUN $num",$fabil,$fabric,$total
	else
		echo "UNKNOWN LINE: " $line
	fi
    #echo $line # or whaterver you want to do with the $line variable
done < $1 
