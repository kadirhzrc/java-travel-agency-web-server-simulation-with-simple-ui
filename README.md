# java-travel-agency-web-server-simulation-with-simple-ui
A Java program that simulates 3 separate machines sending information to each other using web servers as User - Travel Agency - Hotel & Airline Web Server. Written in native Java tools using TCP. Term project for CSE4074 Computer Networks course.

Simple Java Swing UI takes inputs of travel to send to travel agency. These are arrival date, return date, number of people and preferred hotels & airlines. Data is then sent to Agency server and request is forwarded to airline & hotel servers. Forwarded request is indeed an HTTP request and same can be achieved with a browser as well. HTTP parsing is made in native. Airline and hotel servers use .txt databases to keep record of availability. Then a return message is sent back to the Agency. Then through there user is informed of the result of the sent request. If the request fails because of availability Agency server creates an itinerary and proposes a similar travel offer to the user.
