#!/bin/bash

SRC=$1
DEST=$2
mkdir $DEST
for file in $(ls $SRC)
do
 if [ -f "$SRC/$file" ]
 then
  cp "$SRC/$file" "$DEST/$file"
  echo "Copied $SRC/$file to $DEST/$file"
  for SUM1 in `md5sum "$SRC/$file"`
  do
   break
  done
  for SUM2 in `md5sum "$DEST/$file"`
  do
   break
  done
  if [ "$SUM1" != "$SUM2" ]
  then
    echo "$SRC/$file not copied correctly to $DEST/$file"
    echo "$SUM1 != $SUM2"
  fi
 else 
  if [ -d "$SRC/$file" ]
  then 
    echo "Directory: $SRC/$file"
    copycheck.sh "$SRC/$file" "$DEST/$file"
  fi
 fi
done
