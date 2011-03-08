#!/bin/sh

store="store0"
codebase="fab://${store}/123"
if [ -n "$store" ]; then
  store="$1"
fi
if [ -n "$2" ]; then
  codebase="$2"
fi

command="filc -deststore ${store} -addCodebase ${codebase} src/fabtest/B.fab"
echo $command
eval $command
