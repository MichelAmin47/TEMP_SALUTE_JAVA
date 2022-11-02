#!/bin/bash
cd -- "$(dirname "$BASH_SOURCE")"
 currentHub=$(ps -ax | grep "/usr/bin/java -jar ./server.jar role hub" | awk '{print $1;}')
 echo WARNING, a hub is already running on this machine, on PID $currentHub
 kill -9 $currentHub
java -jar server.jar -role hub -hubConfig hubConfig.json
/bin/bash