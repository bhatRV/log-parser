﻿NOTE: this tool has been tested with Windows, hence the default file path used ,follows windows format. Should be updated in Test class to right format before running test cases.
 
UseCases
----------

(1) java tool that can parse and load the given log file

(2) The tool takes log file name as the main input

(3) 3 methods are exposed right now to do the following:

     1. To find the number of unique IP addresses

     2. To find the top X most visited IP

     3. To find the top X most used URL
     



How to use the Log parser tool
------------------------------------

 (1) The application was created using java8  and maven

 (2) To compile:
    mvn clean install


 (3) usage:


        1. To find the number of unique IP addresses:
            java -jar <target/log_parser-0.0.1-SNAPSHOT.jar> <logFilePath>


        2. To find the top X most visited IP:
               java -jar <target/log_parser-0.0.1-SNAPSHOT.jar> X

                 X==> how many entries you would like to see , takes integer values as input
                 (Response printed along with the number of occurrences)

        3. To find the top X most used URL:
               java -jar <target/log_parser-0.0.1-SNAPSHOT.jar> X
                X==> how many entries you would like to see
                 (Response printed along with the number of occurrences)


Assumption
------------------------------------

 >> Basic tool , No REST APIs included. Can be extended if required
 >> import statements are Auto added, dint invest time to remove the wild chars.hope that's ok
 >>Code has been tested with Windows as OS,
     with just the file name format change in LogParserImplTest ; tool can be used with other OS as well

 >> Screen shot of out put attached as output.jpg
