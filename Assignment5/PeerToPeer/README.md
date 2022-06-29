#### Screencast: https://youtu.be/0kkOwwxBduY


#### Purpose:
This program should allow for multiple peers to be able to communicate with one another. The npeers are able to talk back and forth and receive information from eachother. I have been having trouble with the third peer because of a "port in use" issue.

### How to run it

Arguments are name and port. Start 2 to many peers each having a unique port number. 

Run in Terminal 1: gradle runPeer --args "A 8080" --console=plain -q
Run in Terminal 2: gradle runPeer --args "B 8081 8080" --console=plain -q
Run in Terminal 3: gradle runPeer --args "C 8082 8080" --console=plain -q
