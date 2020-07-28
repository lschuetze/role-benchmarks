#!/bin/bash
# make script fail on first error
set -e
# make SCRIPT_PATH absolute
cd ../../implementations
pushd `dirname $0` > /dev/null
SCRIPT_PATH=`pwd`
popd > /dev/null

/bin/bash $SCRIPT_PATH/build-otj.sh 14 indy 3.8.0

JFR="-XX:StartFlightRecording=filename=bench-indy3.jfr,settings=${SCRIPT_PATH}/extensive" 
#--add-exports=jdk.management.agent/jdk.internal.agent=ALL-UNNAMED"
# -XX:FlightRecorderOptions=stackdepth=128 -XX:StartFlightRecording=name=JMC_Default,maxsize=100m -Djava.net.preferIPv4Stack=true -XX:+UnlockDiagnosticVMOptions -Djdk.attach.allowAttachSelf=true --add-exports=java.management/sun.management=ALL-UNNAMED


#/bin/bash $SCRIPT_PATH/java14.sh

#~/Downloads/openjdk-15-ea+33_osx-x64_bin/jdk-15.jdk/Contents/Home/bin/java

WEAV="/Users/lschuetze/Development/repos/role-benchmarks/benchmarks/ObjectTeams/weavables.txt"

/bin/bash $SCRIPT_PATH/java14.sh -ea $JFR -Dot.weavable=$WEAV -server -XX:-TieredCompilation -Xmx4G --add-reads jdk.dynalink=ALL-UNNAMED --add-reads java.base=ALL-UNNAMED --add-reads jdk.localedata=ALL-UNNAMED -Xbootclasspath/a:./objectteams/indy-3.8.0/otre_min.jar -javaagent:./objectteams/indy-3.8.0/otredyn_agent.jar -jar ../benchmarks/ObjectTeams/benchmarks-indy-3.8.0.jar benchmark.BankBenchmark2 1 3000
