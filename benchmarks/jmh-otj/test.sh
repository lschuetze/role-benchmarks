#!/bin/bash
# make script fail on first error
set -e
# make SCRIPT_PATH absolute
cd ../../implementations
pushd `dirname $0` > /dev/null
SCRIPT_PATH=`pwd`
popd > /dev/null

#/bin/bash ${SCRIPT_PATH}/build-jmh.sh 14 graceful
#/bin/bash ${SCRIPT_PATH}/build-jmh.sh 14 indy
#/bin/bash ${SCRIPT_PATH}/build-jmh.sh 14 classic

LIBS="${SCRIPT_PATH}/objectteams/graceful-indy-3.8.0"
JVM_ARGS="-Dot.weavable=../benchmarks/jmh-otj/weavables.txt"
JVM_ARGS="$JVM_ARGS -Xbootclasspath/a:${LIBS}/otre_min.jar"
JVM_ARGS="$JVM_ARGS -javaagent:${LIBS}/otredyn_agent.jar"
JVM_ARGS="$JVM_ARGS --add-reads jdk.dynalink=ALL-UNNAMED --add-reads java.base=ALL-UNNAMED --add-reads jdk.localedata=ALL-UNNAMED"

/bin/bash $SCRIPT_PATH/java14.sh $JVM_ARGS -jar ${SCRIPT_PATH}/../benchmarks/jmh-otj/target/jmh-otj-artefacts-graceful-infra.jar ActiveCallinBenchmark "$@"

LIBS="${SCRIPT_PATH}/objectteams/classic-3.8.0"
JVM_ARGS="-Dot.weavable=../benchmarks/jmh-otj/weavables.txt"
JVM_ARGS="$JVM_ARGS -Xbootclasspath/a:${LIBS}/otre_min.jar"
JVM_ARGS="$JVM_ARGS -javaagent:${LIBS}/otredyn_agent.jar"
JVM_ARGS="$JVM_ARGS --add-reads java.base=ALL-UNNAMED --add-reads jdk.localedata=ALL-UNNAMED"

/bin/bash $SCRIPT_PATH/java14.sh $JVM_ARGS -jar ${SCRIPT_PATH}/../benchmarks/jmh-otj/target/jmh-otj-artefacts-classic-infra.jar ActiveCallinBenchmark "$@"

