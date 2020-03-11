#!/bin/bash
SCRIPT_PATH=`dirname $0`
source $SCRIPT_PATH/script.inc
source $SCRIPT_PATH/config.inc
if [ "$1" = "8" ]
then
    export JAVA_HOME=$JAVA8_HOME
elif [ "$1" = "9" ]
then
    export JAVA_HOME=$JAVA9_HOME
elif [ "$1" = "11" ]
then
    export JAVA_HOME=$JAVA11_HOME
else
    echo "Java version was neither 8 or 9"
fi
if [ "$2" = "classic" ]
then
    echo "Build ObjectTeams Benchmarks"
    pushd $SCRIPT_PATH/../benchmarks/ObjectTeams
    ant jar -lib lib/ecotj-head.jar
elif [ "$2" = "indy" ]
then
    echo "Build ObjectTeams Indy Benchmarks"
    pushd $SCRIPT_PATH/../benchmarks/ObjectTeamsIndy
    ant jar -lib lib/ecotj-head.jar
fi

