FROM maven:alpine
WORKDIR /grh
ADD . /grh
EXPOSE 3000
CMD /deliver.sh

