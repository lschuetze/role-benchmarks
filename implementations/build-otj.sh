#!/bin/bash
SCRIPT_PATH=`dirname $0`
source $SCRIPT_PATH/script.inc
source $SCRIPT_PATH/config.inc
if [ "$1" = "1.8" ]
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
# Decide which ObjectTeams implementation to build with
echo "Build ObjectTeams Benchmarks"
pushd $SCRIPT_PATH/../benchmarks/ObjectTeams
if [ "$2" = "classic" ]
then
    if [ "$3" = "2.5.0" ]
    then
        echo "ObjectTeams Classic 2.5.0"
        ant jar -lib $SCRIPT_PATH/objectteams/classic-2.5.0/ecotj-head.jar -Dlib=$SCRIPT_PATH/objectteams/classic-2.5.0 -Dsource=${1} -Dtarget=${1} -Dver=2.5.0 -Dapr=classic
    elif [ "$3" = "3.8.0" ]
    then
        echo "ObjectTeams Classic 3.8.0"
        ant jar -lib $SCRIPT_PATH/objectteams/classic-3.8.0/ecotj-head.jar -Dlib=$SCRIPT_PATH/objectteams/classic-3.8.0 -Dsource=${1} -Dtarget=${1} -Dver=3.8.0 -Dapr=classic
    fi
elif [ "$2" = "indy" ]
then
    if [ "$3" = "2.5.0" ]
    then
        echo "ObjectTeams Indy 2.5.0"
        ant jar -lib $SCRIPT_PATH/objectteams/indy-2.5.0/ecotj-head.jar -Dlib=$SCRIPT_PATH/objectteams/indy-2.5.0 -Dsource=${1} -Dtarget=${1} -Dver=2.5.0 -Dapr=indy
    elif [ "$3" = "3.8.0" ]
    then
        echo "ObjectTeams Indy 3.8.0"
        ant jar -lib $SCRIPT_PATH/objectteams/indy-3.8.0/ecotj-head.jar -Dlib=$SCRIPT_PATH/objectteams/indy-3.8.0 -Dsource=${1} -Dtarget=${1} -Dver=3.8.0 -Dapr=indy
    fi
fi

