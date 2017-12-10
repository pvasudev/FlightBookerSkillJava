#!/bin/bash
mvn assembly:assembly -DdescriptorId=jar-with-dependencies package
aws s3 cp --acl public-read target/FlightBookerSkill-1.0-SNAPSHOT-jar-with-dependencies.jar s3://flightbookerskill
aws lambda update-function-code --function-name FlightBookerJava --s3-bucket flightbookerskill --s3-key FlightBookerSkill-1.0-SNAPSHOT-jar-with-dependencies.jar

