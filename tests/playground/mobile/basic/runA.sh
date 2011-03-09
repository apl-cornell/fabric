#!/bin/sh

if [ -z "1" ]; then
  echo "usage: runA.sh oidOfCodebaseA"
  exit
fi

store="ubuntu"
oid="$1"

command="fab fab://${store}/${oid}/A"
echo $command
eval $command
