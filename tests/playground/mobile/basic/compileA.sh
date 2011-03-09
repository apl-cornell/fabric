#!/bin/bash

source "$(dirname $0)/defs"

if [ -z "${1}" ]; then
  echo "usage: compileB.sh oidOfCodebaseForB"
  exit
fi

codebase="fab://${store}/${1}"

command="fabc -deststore ${store} -addCodebase ${codebase} src/fabtest/B.fab"
echo $command
eval $command
