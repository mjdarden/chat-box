#!/bin/bash

source ./.bashrc

pushd $MDARDEN_BUILD_TOP

. ./parseArgs.bash $@

export MDARDEN_CODE_TOP=~/repos/chat-box/

if [ "$BOTH_CLEAN_BUILD" = "1" ]; then
    echo "Both clean build..."
    rm -rf $MDARDEN_CLIENT_INSTALL_TOP
    pushd $MDARDEN_CODE_TOP/client
    mvn clean
    mvn compile
    mvn package
    popd
    rm -rf $MDARDEN_SERVER_INSTALL_TOP
    pushd $MDARDEN_CODE_TOP/server
    mvn clean
    mvn compile
    mvn package
    popd
elif [ "$BOTH_REG_BUILD" = "1" ]; then
    echo "Both regular build..."
    pushd $MDARDEN_CODE_TOP/client
    mvn compile
    mvn package
    popd
    pushd $MDARDEN_CODE_TOP/server
    mvn compile
    mvn package
    popd
elif [ "$SERVER_CLEAN_BUILD" = "1" ]; then
    echo "Server clean build..."
    rm -rf $MDARDEN_SERVER_INSTALL_TOP
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
    rm -rf $MDARDEN_CLIENT_INSTALL_TOP
    pushd $MDARDEN_CODE_TOP/client
    mvn clean
    mvn compile
    mvn package
    popd
elif [ "$CLIENT_REG_BUILD" = "1" ]; then
    echo "Client regular build..."
    pushd $MDARDEN_CODE_TOP/client
    mvn compile
    mvn package
    popd
fi

if [ "$NO_PACKAGE" != "1" ]; then
    echo "Attempting to package"
    mkdir $MDARDEN_SERVER_INSTALL_TOP/jars
    mkdir $MDARDEN_SERVER_INSTALL_TOP/scripts
    cp $MDARDEN_SERVER_INSTALL_TOP/target/server.jar $MDARDEN_SERVER_INSTALL_TOP/jars
    chmod 775 $MDARDEN_SERVER_INSTALL_TOP/jars/server.jar
    cp $MDARDEN_SERVER_CODE_TOP/scripts/run_server.bash $MDARDEN_SERVER_INSTALL_TOP/scripts
    chmod 775 $MDARDEN_SERVER_INSTALL_TOP/scripts/run_server.bash

    mkdir $MDARDEN_CLIENT_INSTALL_TOP/jars
    mkdir $MDARDEN_CLIENT_INSTALL_TOP/scripts
    cp $MDARDEN_CLIENT_INSTALL_TOP/target/client.jar $MDARDEN_CLIENT_INSTALL_TOP/jars
    chmod 775 $MDARDEN_CLIENT_INSTALL_TOP/jars/client.jar
    cp $MDARDEN_CLIENT_CODE_TOP/scripts/run_client.bash $MDARDEN_CLIENT_INSTALL_TOP/scripts
    chmod 775 $MDARDEN_CLIENT_INSTALL_TOP/scripts/run_client.bash
fi

popd