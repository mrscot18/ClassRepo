#### Purpose:
This program allows for multiple peers to be able to communicate with one another. The npeers are able to talk back and forth and receive information from eachother.

### How to run it

Arguments are name and port. Start 2 to many peers each having a unique port number. 

Run in Terminal 1: gradle runPeer --args "Q 7000" --console=plain -q
Run in Terminal 2: gradle runPeer --args "W 7001 7000" --console=plain -q
Run in Terminal 3: gradle runPeer --args "E 7002 7000" --console=plain -q