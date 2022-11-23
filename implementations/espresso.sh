#!/bin/bash
SCRIPT_PATH=`dirname $0`
source $SCRIPT_PATH/config.inc

cd $ESPRESSO_HOME
exec mx espresso "$@"