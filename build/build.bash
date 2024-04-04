#!/bin/bash

. ./parseArgs.bash $@

export MDARDEN_CODE_TOP=~/repos/chat-box/

if [ "$BOTH_CLEAN_BUILD" = "1" ]; then
    echo "Both clean build..."
    pushd $MDARDEN_CODE_TOP/server
    mvn clean
    mvn compile
    mvn package
    popd
elif [ "$BOTH_REG_BUILD" = "1" ]; then
    echo "Both regular build..."
    pushd $MDARDEN_CODE_TOP/server
    mvn compile
    mvn package
    popd
elif [ "$SERVER_CLEAN_BUILD" = "1" ]; then
    echo "Server clean build..."
    pushd $MDARDEN_CODE_TOP/server
    mvn clean
    mvn compile
    mvn package
    popd
elif [ "$SERVER_REG_BUILD" = "1" ]; then
    echo "Server regular build..."
    pushd $MDARDEN_CODE_TOP/server
    mvn compile
    mvn package
    popd
elif [ "$CLIENT_CLEAN_BUILD" = "1" ]; then
    echo "Client clean build..."
elif [ "$CLIENT_REG_BUILD" = "1" ]; then
    echo "Client regular build..."
fi

if [ "$NO_PACKAGE" != "1" ]; then
    echo "Attempting to package"
    mkdir $MDARDEN_INSTALL_TOP/server/jars
    mkdir $MDARDEN_INSTALL_TOP/server/scripts
    cp $MDARDEN_INSTALL_TOP/server/target/server.jar $MDARDEN_INSTALL_TOP/server/jars
    chmod 775 $MDARDEN_INSTALL_TOP/server/jars/server.jar
    cp $MDARDEN_INSTALL_TOP/server/target/classes/run_server.bash $MDARDEN_INSTALL_TOP/server/scripts
    chmod 775 $MDARDEN_INSTALL_TOP/server/scripts/run_server.bash

fi