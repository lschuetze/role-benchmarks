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
elif [ "$1" = "14" ]
then
    export JAVA_HOME=$JAVA14_HOME
else
    echo "Java version was neither 8, 9, 11 or 14"
fi

if [ "$2" = "classic" ]
then
    ARTEFACTS=jmh-otj-artefacts-classic
    LIBS=$SCRIPT_PATH/objectteams/classic
    POM=pom-classic.xml
elif [ "$2" = "indy" ]
then
    ARTEFACTS=jmh-otj-artefacts-indy
    LIBS=$SCRIPT_PATH/objectteams/graceful-indy
    POM=pom-indy.xml
elif [ "$2" = "graceful" ]
then
    ARTEFACTS=jmh-otj-artefacts-graceful
    LIBS=$SCRIPT_PATH/objectteams/graceful-indy
    POM=pom-graceful.xml
else
    echo "Approach not chosen."
fi

# Decide which ObjectTeams implementation to build with
echo "Build ObjectTeams Benchmarks for $2"
pushd $SCRIPT_PATH/../benchmarks/jmh-otj
ant install -lib $LIBS-3.8.0/ecotj-head.jar -Dartefacts=$ARTEFACTS -Dlib=$LIBS-3.8.0
mvn verify -f$POM