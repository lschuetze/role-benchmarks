#!/bin/bash
SCRIPT_PATH=`dirname $0`
source $SCRIPT_PATH/config.inc
exec $JAVA17_HOME/bin/java $JDK_JIT_FLAGS "$@"
