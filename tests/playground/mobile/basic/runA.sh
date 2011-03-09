#!/bin/bash

source "$(dirname $0)/defs"

if [ -z "$1" ]; then
  echo "usage: runA.sh oidOfCodebaseA"
  exit
fi

oid="$1"

command="fab fab://${store}/${oid}/A"
echo $command
eval $command
