# bot-tracking
Project to enable / verify the users logging into the applications are not essentially bots. Initial implementation is to make sure that the users who are using our application knows how to add numbers. 

Requirement : Create a server that does two things:
1.	Respond to a client HTTP request
  Respond to a client HTTP request with a minimum following in the response body
  a)	A question with random numbers in the response body. 
    Please sum the numbers 9,5,3
    Please sum the numbers 10,2
    Please sum the numbers 5,7,1)

2.	Receive a client HTTP request
 Receive a client HTTP request with a minimum following in the request
  a)	The question with the random numbers which was send earlier in the response of the first request.
  b)	The sum of the numbers in the question.
 And respond with,
  a)	If the sum of the numbers is correct, then return a HTTP 200 OK
  b)	If the sum of the numbers is wrong or if it’s an invalid request, then return a HTTP 400 Bad Request.
 You are not required to write the client application, but you can assume that a client for this server should:
  a)	Send a request to Service 1 and receives a response body with the question.
  b)	Sum the numbers in the question
  c)	Send the sum of the numbers to the service 2 along with the original response body from 1 which had the question.
