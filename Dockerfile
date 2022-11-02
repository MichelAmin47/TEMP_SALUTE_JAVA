FROM maven:3.3-jdk-8

# creating a folder /test which will be mounted a volume from the
# host machine (the working directory), then making that folder
# the working directory of the docker container.
VOLUME /test
WORKDIR /test

ENTRYPOINT [ "mvn" ]
# the command that will be executed when we run the container
CMD [ "test" ]