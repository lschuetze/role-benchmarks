#!/bin/bash
SCRIPT_PATH=`dirname $0`
source $SCRIPT_PATH/config.inc
exec $OPENJ9_HOME/bin/java $JDK_JIT_FLAGS "$@"
