#!/bin/sh

store="ubuntu"
if [ -n "$1" ]; then
  store="$1"
fi

command="fabc -deststore ${store} src/fabtest/B.fab"
echo $command
eval $command

