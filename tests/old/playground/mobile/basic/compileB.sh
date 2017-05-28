#!/bin/bash

source "$(dirname $0)/defs"

if [ -n "$1" ]; then
  store="$1"
fi

command="fabc -deststore ${store} src/fabtest/B.fab"
echo $command
eval $command

