FROM alpine:3.15.4

ENV MYSQL_USERNAME=root
ENV MYSQL_PASSWORD=password
ENV MYSQL_INSTANCE_NAME=mysql
ENV MYSQL_PORT_3306_TCP_ADDR=mysql
ENV MYSQL_PORT_3306_TCP_PORT=3306

RUN apk update \
    && apk add --virtual build-deps gcc python3-dev musl-dev \
    && apk add --no-cache mariadb-dev \
    && apk add py3-pip \
    && apk add curl \
    && pip install mysqlclient \
    && pip install flask 

COPY app.py /app.py

ENTRYPOINT ["python3.9", "/app.py"]
CMD [""]