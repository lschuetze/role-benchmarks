#!/bin/bash
SCRIPT_PATH=`dirname $0`
source $SCRIPT_PATH/script.inc
source $SCRIPT_PATH/config.inc
if [ "$1" = "1.7" ]
then
    export JAVA_HOME=$JAVA7_HOME
elif [ "$1" = "1.8" ]
then
    export JAVA_HOME=$JAVA8_HOME
elif [ "$1" = "9" ]
then
    export JAVA_HOME=$JAVA9_HOME
elif [ "$1" = "11" ]
then
    export JAVA_HOME=$JAVA11_HOME
elif [ "$1" = "14" ]
then
    export JAVA_HOME=$JAVA14_HOME
elif [ "$1" = "17" ]
then
    export JAVA_HOME=$JAVA17_HOME
elif [ "$1" = "20" ]
then
    export JAVA_HOME=$JAVA20_HOME
else
    echo "Java version was neither 1.7, 1.8, 9, 11, 14, 17, or 20"
fi

echo "Build SCROLL Benchmarks"
pushd $SCRIPT_PATH/../benchmarks/scroll

sbt assembly
