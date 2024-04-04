#!/bin/bash

usage()
{
    echo "Usage $cmdName [ -h | --help ] [ -s | --server ] [ -k | --client ] [ -p | --package ] [ -c | --clean ]"
    echo ""
    echo "-h, --help     Displays this message"
    echo "-s, --server   Build server"
    echo "-k, --client   Build client"
    echo "-p, --package  Dont package"
    echo "-c, --clean    Run a clean build"
    exit 1
}

if [ $# -gt 0 ]; then
    FOUND=0
    for arg in $@; do
        case $arg in
            -h|--help)
                    usage;
                    ;;
            -s|--server)
                    echo "Server build...";
                    SERVER_BUILD=1;
                    ;;
            -k|--client)
                    echo "Client build...";
                    CLEAN_BUILD=1;
                    ;;
            -p|--package)
                    FOUND=1
                    echo "Dont package...";
                    export NO_PACKAGE=1;
                    ;;
            -c|--clean)
                    FOUND=1
                    echo "Clean build...";
                    CLEAN_BUILD=1;
                    ;;
        esac
    done
    if [ $FOUND -eq 0 ]; then
        echo "Invalid uption"
        usage
    fi

    if [ "$SERVER_BUILD" = "1" ]; then
        if [ "$CLIENT_BUILD" = "1" ]; then
            if [ "$CLEAN_BUILD" = "1" ]; then
                export BOTH_CLEAN_BUILD=1
            else
                export BOTH_REG_BUILD=1
            fi
        else
            if [ "$CLEAN_BUILD" = "1" ]; then
                export SERVER_CLEAN_BUILD=1
            else
                export SERVER_REG_BUILD=1
            fi
        fi
    elif [ "$CLIENT_BUILD" = "1" ]; then
            if [ "$CLEAN_BUILD" = "1" ]; then
                export CLIENT_CLEAN_BUILD=1
            else
                export CLIENT_REG_BUILD=1
            fi
    else
        if [ "$CLEAN_BUILD" = "1" ]; then
            export BOTH_CLEAN_BUILD=1
        fi
    fi
fi

#If we made it here we want just a regular build
export BOTH_REG_BUILD=1
