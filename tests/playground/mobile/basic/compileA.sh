#!/bin/sh

if [ -z "${1}" ]; then
  echo "usage: compileB.sh oidOfCodebaseForB"
  exit
fi

store="ubuntu"
codebase="fab://${store}/${1}"

command="fabc -deststore ${store} -addCodebase ${codebase} src/fabtest/B.fab"
echo $command
eval $command
