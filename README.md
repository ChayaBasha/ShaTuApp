# ShaTuApp
## Overview
Sha256 Tutoring Application Developed By Students for Students at Regis University. First version began Capstone Fall 2023.

## To Do (last updated November 2, 2023)
- Encode As ASCII
    - Check action should possibly show the ASCII of the input on the spot where Test is instead of in the console where the user can't see it.
    - Maybe save as a variable the result to populate the next step
    - Popup currently only happens on click action not keystroke enter. 
- Add '1' Bit
    - Change so that there is entry from previous step
    -Same key action listener issue
- Pad with '0's 
    - - Change so that there is entry from previous step
    -Same key action listener issue
- Add Msg Length
    - Change so that there is entry from previous step
    - Same key action listener issue
- Pepare Schedule
    - Just has a verify button, needs to be implemented and clearer what this is supposed to do
- Initialize Variables
    - needs implementation
- Compress Round
    - currently shows an image, we need to make this more insructional, maybe we need the images to be able to be clicked?
- Rotate n bits
    - impelement keypressListener (look at Johanna2 branch for code) (Added 11/2 on local branch Damian2)
    - lets implement an error handling that tells the user to enter something before the check can be done (Added 11/2 on local branch Damian2)
    - If its wrong let's say its wrong without giving the answer and prompt user to ask for a hint if they want it. In this allow a hint click option for a pop up hint (just have pop up say hint for now)
- Shift Right
    - add error for if nothing entered in the box
    - serparate correct from incorrect and offer a hint (Added 10/25)
    - implement keyListener for pressing enter
- Exclusive Or
    - add error for if nothing entered in the box
    - serparate correct from incorrect and offer a hint (Added 10/25)
    - implement keyListener for pressing enter
- Add two n bit
    - add error for if nothing entered in the box
    - serparate correct from incorrect and offer a hint (Added 10/25)
    - implement keyListener for pressing enter
- Ch function
    - needs to be implemented
    - add exra details as specified for the other operations
- Maj Function
    - add error for if nothing entered in the box
    - serparate correct from incorrect and offer a hint (Added 10/25)
    - implement keyListener for pressing enter
- SHA Sum 0 Value 
    - needs t be implemented
- SHA SUM 1 Value
     - add error for if nothing entered in the box
    - serparate correct from incorrect and offer a hint (Added 10/25)
    - implement keyListener for pressing enter

- Sign In 
    - want to probably .gitignore new users
    - want a cleaner closing of the port so that the port doens't get blocked (likley implimented with the token expiring and triggering a sign out)
- ADD SIGN OUT flow 

- Whole Flow
    - WE have an option as to wheter to have the initial inpput on Encode as ASCII flow through the entire steps one by one as an option OR
    - Practice each step independently
    - WE could do this with a user control option to 1. move to nex step (where it keeps the input and moves on to the next step in the process) or 2) practice this step again (where it loads a new question independent of the previous steps result)
    - I think we will eventuall want a screen before this one that will have the option to practice, teach me again, or quix me. Practice would show the JPanel we have now, teach me could load an animation walking through each step and quiz me would be like an exam. 
- Whole app
    - should we have an option to make the text bigger? Typical zoom is not working on my 2013 mac. 
    - we should probably have a screen reader/read to me option so it can read aloud
    - screen should size to device


