#!/bin/sh

if [ -z "$1" ]; then
  echo "usage: runB.sh oidOfCodebaseB"
  exit
fi

store="ubuntu"
oid="${1}"

command="fab codebase_${store}.${oid}.B"
echo $command
eval $command
