#!/bin/bash
# make script fail on first error
set -e
# make SCRIPT_PATH absolute
cd ../../implementations
pushd `dirname $0` > /dev/null
SCRIPT_PATH=`pwd`
popd > /dev/null

/bin/bash $SCRIPT_PATH/build-otj.sh 14 classic 3.8.0

JFR="-XX:StartFlightRecording=filename=bench-classic2.jfr,settings=profile"
WEAV="/Users/lschuetze/Development/repos/role-benchmarks/benchmarks/ObjectTeams/weavables.txt"

/bin/bash $SCRIPT_PATH/java14.sh -ea $JFR -Dot.weavable=$WEAV -server -XX:-TieredCompilation -Xmx4G --add-reads jdk.dynalink=ALL-UNNAMED --add-reads java.base=ALL-UNNAMED --add-reads jdk.localedata=ALL-UNNAMED -Xbootclasspath/a:./objectteams/classic-3.8.0/otre_min.jar -javaagent:./objectteams/classic-3.8.0/otredyn_agent.jar -jar ../benchmarks/ObjectTeams/benchmarks-classic-3.8.0.jar benchmark.BankBenchmark 1 2000
