#!/usr/bin/env bash

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"

HOMESCHOOLING="${DIR}/build/install/homeschooling/bin/homeschooling"

if [ ! -f "$HOMESCHOOLING" ]; then
  "${DIR}/gradlew" installDist
fi

if [ "$#" -gt 0 ]; then
  "$HOMESCHOOLING" "$@"
else
  "$HOMESCHOOLING" --help
fi

