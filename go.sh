#!/usr/bin/env bash

HOMESCHOOLING="build/install/homeschooling/bin/homeschooling"

if [ ! -f "$HOMESCHOOLING" ]; then
  ./gradlew installDist
fi

$HOMESCHOOLING "$@"
