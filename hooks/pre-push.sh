#!/usr/bin/env bash

if [[ -n $(git status -s -uno) ]] ; then
    echo "There are uncommitted changes, aborting checks."
    exit 1
fi

echo "Running code checks..."

# Run quick static code checks
./gradlew detekt
# TODO Replace the line above with the line below if you want to use type resolution
# ./gradlew detektDebug detektDebugUnitTest detektDebugAndroidTest --continue

status=$?

if [[ "$status" = 0 ]] ; then
    echo "Code checks found no problems."
    exit 0
else
    echo 1>&2 "Code checks found violations, some may have been automatically fixed in your working directory."
    exit 1
fi
