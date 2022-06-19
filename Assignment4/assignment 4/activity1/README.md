# Assignment 4 Activity 1
## Description
The initail Performer code only has one function for adding strings to an array: 

## Protocol

### Requests
request: { "selected": <int: 1=add, 2=pop, 3=display, 4=count, 5=switch,
0=quit>, "data": <thing to send>}

  data <string> add
  data <> pop
  data <> display
  data <> count
  data <int> <int> switch return a string

### Responses

sucess response: {"type": <"add",
"pop", "display", "count", "switch", "quit"> "data": <thing to return> }

type <String>: echoes original selected from request
data <string>: add = new list, pop = new list, display = current list, count = num elements, switch = current list


error response: {"type": "error", "message"": <error string> }
Should give good error message if something goes wrong


## How to run the program
### Terminal
Base Code, please use the following commands:
```
    For Server, run "gradle runServer -Pport=9099 -q --console=plain"
```
```   
    For Client, run "gradle runClient -Phost=localhost -Pport=9099 -q --console=plain"
```   
When trying to run the multiple tasks, 

type " gradle runTask1" and change the 1 out for "2" or "3"
for the client: type "gradle runClient"

This should allow you in to the 'game' with multiple clients. 


I believe I was able to accomplish all parts of this assignment. "add <string>" adds a string to a list, "pop" removes the top string from that list, "display" displays the elements of the list, "count" returns the number of elements in the list, and lastly, "switch" switches elements at a given index. I was also able to make it so that multiple clients could connect to a server and recieve messages back from the server. 

Screencast: https://youtu.be/oOH1PzTOd30
