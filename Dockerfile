FROM maven:alpine
WORKDIR /grh
ADD . /grh
EXPOSE 3000
