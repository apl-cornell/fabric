function commit_count {
	grep "committed" $1 | wc -l
}

function commit_latencies {
	echo -e "count\tlatency";
	cat $1 | grep -v ":" | grep -o "[0-9]* ms" | cut -d' ' -f1 | sort -n | uniq -c
}

function total_times {
	echo -e "count\ttime";
	# get the file,
	# remove subtxns,
	# cut out stuff between time and started/committed,
	# join lines between first started and the next committed,
	# remove non-timestamps
	# cut to first and last time stamp
	# print out their differences and sort and bin
	cat $1 \
		| grep -v ":" \
		| sed 's/,.*thread [0-9]*,//' \
		| sed ':a;$!N;s/]\n/]|/;ta;P;D' \
		| sed 's/ [^|]*//g' \
		| sed 's/|[^|]*|/|/g' \
		| sed 's/|/\n/g' \
		| while read start ; do read stop ; echo $(($stop - $start)) ; done \
		| sort -n | uniq -c
}

function average_total {
	TOTAL=$(commit_count $1);
	WEIGHTED=$(total_times $1 | tail -n +2 | while read line ; do echo $line | sed 's/ /*/' | bc ; done | paste -sd+ - | bc)
	python -c "print(1000 * "${TOTAL}"/"${WEIGHTED}".0, \"txn/s\")"
}

function successful_times {
	echo -e "count\ttime";
	# get the file,
	# remove subtxns,
	# Only get commit and immediately preceding start
	# cut out stuff between time and started/committed,
	# join lines between first started and the next committed,
	# remove non-timestamps
	# cut to first and last time stamp
	# print out their differences and sort and bin
	cat $1 \
		| grep -v ":" \
		| grep -B 1 "committed" \
		| sed 's/,.*thread [0-9]*,//' \
		| sed ':a;$!N;s/]\n/]|/;ta;P;D' \
		| sed 's/ [^|]*//g' \
		| sed 's/|[^|]*|/|/g' \
		| sed 's/|/\n/g' \
		| while read start ; do read stop ; echo $(($stop - $start)) ; done \
		| sort -n | uniq -c
}
