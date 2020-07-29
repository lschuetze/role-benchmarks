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
    export JAVA11_HOME=`/usr/libexec/java_home -v 11`
    export JAVA14_HOME=`/usr/libexec/java_home -v 14`
elif [ -x /usr/sbin/update-java-alternatives ]
then
    # Java 8
    jvm_info=(`/usr/sbin/update-java-alternatives -l | grep 'java-8\|java-1.8' || true`)
    if [[ -z $jvm_info ]];
        if [ -z "$JAVA9_HOME" ] then
            echo "Java 8 installation not found";
        fi
    else
        export JAVA8_HOME=${jvm_info[2]}
    fi
    # Java 9
    jvm_info=(`/usr/sbin/update-java-alternatives -l | grep 'java-9\|java-1.9' || true`)
    if [[ -z $jvm_info ]];
        if [ -z "$JAVA9_HOME" ] then
            echo "Java 9 installation not found";
        fi
    else
        export JAVA9_HOME=${jvm_info[2]}
    fi
    # Java 11
    jvm_info=(`/usr/sbin/update-java-alternatives -l | grep 'java-11\|java-1.11' || true`)
    if [[ -z $jvm_info ]];
        if [ -z "$JAVA11_HOME" ] then
            echo "Java 11 installation not found";
        fi
    else
        export JAVA11_HOME=${jvm_info[2]}
    fi
    # Java 14
    jvm_info=(`/usr/sbin/update-java-alternatives -l | grep 'java-14\|java-1.14' || true`)
    if [[ -z $jvm_info ]]; then
        if [ -z "$JAVA14_HOME" ] then
            echo "Java 14 installation not found";
        fi
    else
        export JAVA14_HOME=${jvm_info[2]}
    fi
else
    echo "Java directories could not be guessed, please check config.inc"
fi
