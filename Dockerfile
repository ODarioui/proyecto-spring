FROM maven:3.6.2-alpine
WORKDIR /grh
ADD . /grh
EXPOSE 3000
CMD chmod +x ./deliver.sh
CMD ./deliver.sh

