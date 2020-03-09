#!/bin/bash
# make script fail on first error
set -e
# make SCRIPT_PATH absolute
pushd `dirname $0` > /dev/null
SCRIPT_PATH=`pwd`
popd > /dev/null

source $SCRIPT_PATH/config-extra.inc

export JVMCI_VERSION_CHECK=ignore
GRAAL_JIT_FLAGS="-server -XX:+UnlockExperimentalVMOptions -XX:+EnableJVMCI -XX:+UseJVMCICompiler -d64 "

if [ -x /usr/libexec/java_home ]   # usually on OS X, this tool is available
then
  export JAVA8_HOME=`/usr/libexec/java_home -v 1.8`
  export JAVA9_HOME=`/usr/libexec/java_home -v 9`
elif [ -x /usr/sbin/update-java-alternatives ]
then
  jvm_info=(`/usr/sbin/update-java-alternatives -l | grep java-8`)
  export JAVA8_HOME=${jvm_info[2]}
  jvm_info=(`/usr/sbin/update-java-alternatives -l | grep java-9`)
  export JAVA9_HOME=${jvm_info[2]}
else
  echo "Java directories could not be guessed, please check config.inc"
fi
