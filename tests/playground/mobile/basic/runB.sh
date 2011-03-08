#!/bin/sh

store="store0"
oid="123"
if [ -n "$1" ]; then
  store="$1"
fi
if [ -n "$2" ]; then
  oid="$2"
fi

command="fab codebase_${store}.${oid}.B"
echo $command
eval $command
