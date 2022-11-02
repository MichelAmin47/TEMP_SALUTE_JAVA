# Solutions

## Status:
[![pipeline status](https://gitlab.com/valori/salute/java8_webdriver/badges/master/pipeline.svg)](https://gitlab.com/valori/salute/java8_webdriver/-/commits/master)

## Running the smoke test
The smoke test can be run by using `mvn clean test -Dtest=runners.RunCukesSmokeTest`

## Developing tests:
Develop your tests by using the following principles.

###Project structure
See documentation/ProjectStructure.mb

###Cucumber
See documentation/Cucumber.mb

###Page Object model
See documentation/PageObjectModel.mb

## Deploying to the cloud:
Requires a machine or docker container in which docker-compose can be run. In the repository is an example yml file for gitlab which uses the official docker-in-docker container. However we install the docker-compose command on it ourselves. In the future we will provide a docker image with docker-compose pre-installed.

Next we run our tests using the docker-compose up command.

`docker-compose up --abort-on-container-exit`
The --abort-on-container-exit flag stops the containers as soon as a container has exited. The test container exits as soon as tests have finished running. The test container runs all tests inside the project.

### Retrieving test results
Test results will be published the working directory's target folder. Most CI platforms will need a pipeline step that retrieves these test artifacts. An example can be found in our gitlab-ci.yml file.
