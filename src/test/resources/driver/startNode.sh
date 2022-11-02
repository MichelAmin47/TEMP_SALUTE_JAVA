#!/bin/bash
cd -- "$(dirname "$BASH_SOURCE")"
# currentHub=$(ps -ax | grep "/usr/bin/java -jar server.jar role hub" | awk '{print $1;}')
# echo WARNING, a hub is already running on this machine, on PID $currentHub
# kill -9 $currentHub

# determine the current path of this file
parent_path=$( cd "$(dirname "${BASH_SOURCE[0]}")" ; pwd -P )

# I expect this property file to be present one folder above the current path
PROPERTY_FILE=../project.properties

# use grep to find the property in the property file|  cut the value bit off | and remove any leading whitespaces
ROOTFOLDER=$(grep -w -i 'driver_location' $PROPERTY_FILE  | cut -f2 -d'=' | sed 's/^[ \t]*//;s/[ \t]*$//')
BROWSER=$(grep -w -i 'browser' $PROPERTY_FILE  | cut -f2 -d'='|sed 's/^[ \t]*//;s/[ \t]*$//')
VERSION=$(grep -w -i 'chromeVersion' $PROPERTY_FILE  | cut -f2 -d'='|sed 's/^[ \t]*//;s/[ \t]*$//')

echo "Using ChromeDriver located in: ${ROOTFOLDER}${BROWSER}/${VERSION}/chromedriver "

# use the determined path to specify the chromedriver location
java -Dwebdriver.chrome.driver="${ROOTFOLDER}${BROWSER}/${VERSION}/chromedriver" -jar server.jar -role node -nodeConfig nodeConfig.json
/bin/bash