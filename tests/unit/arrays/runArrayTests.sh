SIZE1=10000
SIZE2=1000000
SIZE3=100
MAXCOUNT=500
INCOUNT=50
MAXCOUNT1=2000
INCOUNT1=200
ISBATCH=true
#fabargs='-j "-Xint"'
#javargs='-Xint'
while true
 do
   fab $fabargs arrays.ResizableArrayTest01 7 $SIZE1 $SIZE2 $MAXCOUNT $INCOUNT $ISBATCH
   fab $fabargs arrays.ResizableArrayTest01 8 $SIZE1 $SIZE2 $MAXCOUNT $INCOUNT $ISBATCH
   fab $fabargs arrays.ResizableArrayTest01 9 $SIZE1 $SIZE2 $MAXCOUNT $INCOUNT $ISBATCH
   fab $fabargs arrays.ObjectArrayTest01 $SIZE1 $SIZE2 $MAXCOUNT $INCOUNT $ISBATCH
   java $javargs JavaArrayTest01 $SIZE1 $SIZE2 $MAXCOUNT $INCOUNT $ISBATCH
   fab $fabargs arrays.ResizableArrayTest01 7 $SIZE3 $SIZE2 $MAXCOUNT1 $INCOUNT1 $ISBATCH
   fab $fabargs arrays.ResizableArrayTest01 8 $SIZE3 $SIZE2 $MAXCOUNT1 $INCOUNT1 $ISBATCH
   fab $fabargs arrays.ResizableArrayTest01 9 $SIZE3 $SIZE2 $MAXCOUNT1 $INCOUNT1 $ISBATCH
   fab $fabargs arrays.ObjectArrayTest01 $SIZE3 $SIZE2 $MAXCOUNT1 $INCOUNT1 $ISBATCH
   java $javargs JavaArrayTest01 $SIZE3 $SIZE2 $MAXCOUNT1 $INCOUNT1 $ISBATCH
 done
