#!/bin/sh

store="store0"
if [ -n "$1" ]; then
  store="$1"
fi

command="filc -deststore ${store} src/fabtest/B.fab"
echo $command
eval $command

