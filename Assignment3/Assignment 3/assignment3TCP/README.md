Read me for assignment 3 TCP

Video Walkthrough Link: https://youtu.be/vt5sSoWHJoU

// A description of your project and a detailed description of what it
does.

This project focuses on a server-client relationship and playing a game between the two. You should be able to run the server and the client and enter in your name on the client side. From there, you should be able to start the game, or enter the numebr "1" to see the leaderboard. 
The server will send an image of a quote and it should pop up. Within the client side you should see the image and be able to either input an answer or "next" for more quotes. The server will receive these inputs and either provide more quotes or update your score if you got it correct. You are also being timed and if the timer runs out you will be informed on the server side that you lost. 

// b) (1.5 points) Include a checklist of the requirements marking if you think you fufill the requirements or not. 

When the user starts up it should connect to the server. The server will
reply by asking for the name of the player. - FUFILLED

The user should send their name and the server should receive it and greet the user by name. - FUFILLED

The user should be presented a choice between seeing a leader board or
playing the game (make the interface easy so a user will know what to do). -FUFILLED PARTIALLY, youre told you can when you start the connection

The leader board will show all players that have played since the frist
start of the server with their name and points. The server will maintain the leaderboard and send it to the client when requested. -FUFILLED

The leader board will show all players that have played since the frist
start of the server with their name and points. The server will maintain the leaderboard and send it to the client when requested. - N/A

If the user chooses to start the game, the server will then send over a first quote of a character – you need to print the intended answer in the server terminal to simplify grading for us (this will be worth some points). - FUFILLED PARTIALLY, answer not printed in the server

The user can then do one of three things; enter a guess, e.g. "Jack
Sparrow", type "more", or type "next". See what the more and next options do in the later contraints respectively. -   FUFILLED

The user enters a guess and the server must check the guess and respond
accordingly. If the answer is correct then they will get a new picture with a new quote (or they might win - see later). If the answer is incorrect they will be informed that the answer was incorrect and can try again. - FUFILLED PARTIALLY

If the user enters "more" then they will get another quote from the same
movie character. However, If they enter "more" when the final unique image was already displayed for this character, then they need to be informed that there are no more pictures (quotes) for this character and the image should not change. - FUFILLED

Users can always enter "next" which will make the server send a new
quote for a new character. If there are no more characters available you can show one of the old ones or inform the user and quit the round. You may implement other options but do not let things crash. - FUFILLED

If the server receives 3 correct guesses and the timer did not run out (1 minute), then the server will send a "winner" image (display in UI or open frame when using terminal). - FUFILLED, added extra time for the sake of grading, but I do know how to make it 1 minute. 

If the server receives a guess and the timer ran out the user lost and will get a "loser" image and message (display in UI or open frame when using terminal). - PARTIALLY FUFILLED, does not display image, but displays text

We also want a point system so that you get more points for answering
without asking for more quotes. The point system is to be maintained on the server! If you answer on the first quote you get 5 points, answer on the second 4 points, on the third 3 points and on the last one 1 point. If the user types "next" they loose 2 points (overall points for a round can be negative). Current points should always be displayed on the users GUI (or in the terminal). N/A

At the end of a game (if lost or won) display how many points the client
got. If the user lost, the leader board does not change. If they won add their new points to their old points on the leader board. You can assume that their name always identifies them. - FUFILLED

Your protocol must be robust. If a command that is not understood is sent to the server or an incorrect parameterization of the command, then the protocol should define how these are indicated. Your protocol must have headers and optionally payloads. This means in the context of one logical message the receiver of the message has to be able to read a header, understand the metadata it provides, and use it to assist with processing a payload (if one is even present). This protocol needs to be described in detail in the README.md. - PARTIALLY FUFILLED

Your programs must be robust. If errors occur on either the client or server or if there is a network problem, you have to consider how these should be handled in the most recoverable and informative way possible. Implement good general error handling and output. Your client/server should not crash even when invalid inputs are provided by the user. - FUFILLED

After the player wins/loses they can start a new game by entering their
name again or they can quit by typing "quit". After entering their name they can choose start or the leader board again. - PARTIALLY FUFILLED, the player can enter "quit" to quit

advice: skip this until you have everything else then get back to this:
If a game is in progress and a second user tries to connect, they should receive a message that a game is already in progress and they cannot connect. How exactly you handle this, whether you let the user wait or just do not let them do anything anymore, is up to you. DO NOT use threads, yes I know I am mean. - N/A


// c) An explanation of how we can run the program (hopefully exactly
as described in this document).

1. to run code in terminal : gradle server , grade client 

port = 8880
host = localhost

* I have /user/ for the location of the images for the game.*

Methods can be seen through the UML

Protocol needs to be described in detail in the README.md:

Request:
{<user connects to the game}
Normal Response:
{"Please enter your name"}

Request:
{<user enters their name>}
Normal Response:
{"op":"Hello! ," + name + " Your first quote is being sent, enter next if you want another quote"}

Request:
{"op":"You are correct!"}
Normal Response:
{"<user gets +5 in the leaderboard>"}
        
Request:
{<user enters "next"}
Normal Response:
{"op":<a new image is shown}
  
Request:
{"<user enters "more">}
Normal Response:
{"op":<another image is sent to the user>}
               
 Request:
{"<user enters "1">}
Normal Response:
{"op":<leaderboard is shown with the users name and points>}
  
Request:
{"<user enters "2">}
Normal Response:
{"op":<another image is shown>}  
  
