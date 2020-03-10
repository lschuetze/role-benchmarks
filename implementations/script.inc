#!/bin/bash

function check_for_tools() {
    echo "\r\nTools: "
    for tool in $@; do
        if [ -x `type -t $tool` ]; then
            echo "WARN: $tool ... Miss."
            notok=true
        else
            echo "INFO: $tool ... OK."
        fi
    done
    if [ "$notok" == true ]; then
        echo "Not OK."
        return 1
    fi
}

function check_for_rebench() {
    if [ -x `type -t rebench` ]; then
        if [ -x `python3 -m pip list | grep -F ReBench >/dev/null` ]; then
            echo "INFO: rebench ... OK."
        else
            echo "WARN: rebench ... Miss."
            python3 -m pip install --user rebench
        fi
    else
        echo "INFO: rebench ... OK."
    fi
}
