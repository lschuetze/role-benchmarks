#!/bin/bash
set -e # make script fail on first error
SCRIPT_PATH=`dirname $0`
source $SCRIPT_PATH/script.inc

echo "Start setup ..."
#Check for requirements
check_for_tools ant pip3 python3
check_for_rebench

# echo "\r\nRuntimes:"
# # Install GraalVM
# if [[ "$OSTYPE" == "darwin"* ]]; then
#     if [[ -d "$SCRIPT_PATH/graalvm/Contents/Home/" ]]; then
#         echo "INFO: GraalVM ... OK."
#     else
#         echo "ERR: GraalVM ... not found."
#     fi
# elif [[ "$OSTYPE" == "linux-gnu" ]]; then
#     if [[ -d "$SCRIPT_PATH/graalvm/Home/" ]]; then
#         echo "INFO: GraalVM ... OK."
#     else
#         echo "ERR: GraalVM ... not found."
#     fi
# fi
echo "OK."
echo "\r\nSetup done."

read -p "Build benchmarks? (y/n)" -n 1 -r
echo
if [[ $REPLY =~ ^[Yy]$ ]]; then
    echo "\r\nBuild benchmarks."
    # Build benchmarks
    # /bin/bash $SCRIPT_PATH/build-java.sh 14
    # /bin/bash $SCRIPT_PATH/build-otj.sh 1.8 classic 2.5.0
    # /bin/bash $SCRIPT_PATH/build-otj.sh 9 indy 2.5.0
    #
    #/bin/bash $SCRIPT_PATH/build-otj.sh 14 classic 3.8.0
    /bin/bash $SCRIPT_PATH/build-otj.sh 20 classic 3.31.0
    #/bin/bash $SCRIPT_PATH/build-otj.sh 14 indy 3.8.0
    #/bin/bash $SCRIPT_PATH/build-otj.sh 14 graceful-indy 3.8.0
    # 
    #/bin/bash $SCRIPT_PATH/build-otj.sh 11 classic 3.8.0
    #/bin/bash $SCRIPT_PATH/build-otj.sh 11 indy 3.8.0
    #/bin/bash $SCRIPT_PATH/build-jmh.sh 14 classic
    #/bin/bash $SCRIPT_PATH/build-jmh.sh 14 indy
    #/bin/bash $SCRIPT_PATH/build-jmh.sh 14 graceful

    /bin/bash $SCRIPT_PATH/build-scroll.sh 20
    /bin/bash $SCRIPT_PATH/build-java.sh 20
    /bin/bash $SCRIPT_PATH/build-role4j.sh 1.7

else
    echo "Aborting ..."
fi


