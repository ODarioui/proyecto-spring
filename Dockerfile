FROM maven:latest-alpine
WORKDIR /appmavenjenkins
ADD . /appmavenjenkins
EXPOSE 3000
CMD jenkins/scripts/deliver.sh
