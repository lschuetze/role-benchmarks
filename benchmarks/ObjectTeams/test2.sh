#!/bin/bash
# make script fail on first error
set -e
# make SCRIPT_PATH absolute
cd ../../implementations
pushd `dirname $0` > /dev/null
SCRIPT_PATH=`pwd`
popd > /dev/null

/bin/bash $SCRIPT_PATH/build-otj.sh 14 classic 3.8.0

# HOW TO RUN: ./test2.sh <benchmark> <outer iterations> <inner iterations> <w/o degradation>
if [ "$1" = "1" ]
then
	BENCHMARK="benchmark.BankBenchmark"
elif [ "$1" = "2" ]
then
	BENCHMARK="benchmark.BankBenchmark2"
elif [ "$1" = "3" ]
then
	BENCHMARK="benchmark.TeamBenchmark3"
elif [ "$1" = "4" ]
then
	BENCHMARK="benchmark.TeamBenchmark4"
elif [ "$1" = "5" ]
then
	BENCHMARK="benchmark.TeamBenchmark5"
fi

JFR="-XX:StartFlightRecording=filename=bench-classic2.jfr,settings=profile"
WEAV="/Users/lschuetze/Development/repos/role-benchmarks/benchmarks/ObjectTeams/weavables.txt"

/bin/bash $SCRIPT_PATH/java14.sh -ea -Dot.weavable=$WEAV -server -XX:-TieredCompilation -Xmx4G --add-reads jdk.dynalink=ALL-UNNAMED --add-reads java.base=ALL-UNNAMED --add-reads jdk.localedata=ALL-UNNAMED -Xbootclasspath/a:./objectteams/classic-3.8.0/otre_min.jar -javaagent:./objectteams/classic-3.8.0/otredyn_agent.jar -jar ../benchmarks/ObjectTeams/benchmarks-classic-3.8.0.jar $BENCHMARK ${2} ${3}
