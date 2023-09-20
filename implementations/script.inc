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
            major_version=`python3 -m pip list | grep -F ReBench | awk -F'[^0-9]+' '{print $2}'`
            minor_version=`python3 -m pip list | grep -F ReBench | awk -F'[^0-9]+' '{print $3}'`
            patch_version=`python3 -m pip list | grep -F ReBench | awk -F'[^0-9]+' '{print $4}'`
            # echo ${major_version}.${minor_version}.${patch_version}
            rebench_maj_ver=1
            rebench_min_ver=2
            rebench_pat_ver=0
            if [ $major_version -lt $rebench_maj_ver ] || [ $minor_version -lt $rebench_min_ver ] || [ $patch_version -lt $rebench_pat_ver ]; then
                echo "WARN: rebench ... Outdated."
                python3 -m pip install --user --upgrade rebench==$rebench_maj_ver.$rebench_min_ver.$rebench_pat_ver
            else 
                echo "INFO: rebench ... OK."
            fi
        else
            echo "WARN: rebench ... Miss."
            python3 -m pip install --user rebench
        fi
    else
        echo "INFO: rebench ... OK."
    fi
}
