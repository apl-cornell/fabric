#!/bin/bash

source "$(dirname $0)/defs"

if [ -z "$1" ]; then
  echo "usage: runB.sh oidOfCodebaseB"
  exit
fi

oid="${1}"

command="fab --name $store fab://${store}/${oid}/fabtest/B"
echo $command
eval $command
