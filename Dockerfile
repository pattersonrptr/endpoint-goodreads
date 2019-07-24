FROM java:8-alpine
MAINTAINER Your Name <you@example.com>

ADD target/book-api-clojure-0.0.1-SNAPSHOT-standalone.jar /book-api-clojure/app.jar

EXPOSE 8080

CMD ["java", "-jar", "/book-api-clojure/app.jar"]
