#!/bin/bash

USER_BASE_PATH=`python3 -m site --user-base`

$USER_BASE_PATH/bin/rebench --scheduler=random -c analysis-ch4.conf

DATA_ROOT=`pwd`/data

REV=`git rev-parse HEAD | cut -c1-8`

NUM_PREV=`ls -l $DATA_ROOT | grep ^d | wc -l`
NUM_PREV=`printf "%03d" $NUM_PREV`

TARGET_PATH=$DATA_ROOT/$NUM_PREV-$REV
LATEST=$DATA_ROOT/latest

mkdir -p $TARGET_PATH
cp benchmark.data $TARGET_PATH/
rm $LATEST
ln -s $TARGET_PATH $LATEST
