#!/bin/bash

PATH_TO_CODE_BASE=`pwd`

JAVA_OPTS="-Djava.rmi.server.codebase=file://$PATH_TO_CODE_BASE/sac-client-1.0-SNAPSHOT/lib/jars/sac-client-1.0-SNAPSHOT.jar"

MAIN_CLASS="pod.rmi.client.Client"

#java -Djava.rmi.server.codebase="file:///Users/JuanmaAlonso/ITBA/POD/TP 3 - RMI/server-api-client/sac/client/target/sac-client-1.0-SNAPSHOT/lib/jars/rmi-params-client-1.0-SNAPSHOT.jar" -cp 'lib/jars/*'  $MAIN_CLASS $*
java $JAVA_OPTS -cp 'lib/jars/*'  $MAIN_CLASS $*
