# Fifa Worldcup Qatar 2022
### Main Contributors: 
#### Karim Kabbara / David Desrochers
*This app is designed to create Tournaments and their games, as well as the teams,
the lineups for each match, and the score.*
<hr>

### Included Software
##### Admin Application
*Before the user is allowed to interact with the other components, a Tournament must be created or loaded*
        
    Create a Tournament: User enters the Tournament name and
    the start/end date. Once completed the user may interact
    with the rest of the application. For each tournament created
    all inputs are verified as valid once sent to the server, then
    each input is error checked and handled accordingly.
        *Status: Fully Functional - no known bugs

    Add Participating Country: The user enters the Country name, which
    is then added to the viewable list of countries on the right side 
    of the screen for easy viewing. For each country, each input is validated, this
    is so no duplicate countries are created, and if a country exists you will not
    be able to add it again. Once each country is added it is validated and sent    
    to the server, and finally checked for errors or exceptions and gets stored.
        *Status: Fully Functional - no known bugs

    Add Participating Team: The user creates teams for a specific country here,
    if a team name is duplicated it will throw an error and not allow that entry. Once
    a name is sent to the server, it is validated as a non-duplicate team and checked for
    and errors or null inputs, once done it gets stored.
        *Status: Fully Functional - no known bugs

    Add Players: The user adds players to the created team, The user selects the country first 
    then the user can select a team, once selected the user enters the Player name, age, height and weight
    once done, it is validated to make sure no duplicates are added, then it is sent to the server
    and then it is error checked, if it passes the player is added to the team.
        *Status: Fully Functional - no known bugs

    Add Referee: The user chooses a country from the list, then the user should
    enter the Referee name. Multiple Referees may reside in the same country. There cannot
    be the same named Referee in the same country. Once the data is validated it is
    checked for errors then stored.
        *Status: Fully Functional - no known bugs

    Create Match: The user first chooses a date for the match, then the user must
    enter the start time of the match in HH:MM format or else the time will not be validated.
    Then the first team name followed by the second, once the teams and the date/time are validated 
    they are error checked and stored.
        *Status: Fully Functional - no known bugs

    Set Lineup: The user can enter the lineup of players for each match per team. 
    The user selects a match, from that match a team, and then from that team a list
    of players to add. MAX of 11 Players per team lineup. Once the lineup is full no more can
    be added. If a player is added twice it will throw an error. Once the players are added
    they are error checked and validated then stored.
        *Status: Fully Functional - no known bugs

    Add Referee to Match: The user can add a MIN of 4 Referees to start a match, but each
    Referee must not be from the country of either team. The user must select the match
    then the user may choose a Referee to add to the Match. This is validated to make
    sure no Referee is from the same country and there is a min of 4 then it is stored.
        *Status: Fully Functional - no known bugs
    
    Record Score: The user enters the final score of a played match. The match must
    have concluded before a score is set. If a score is already set, it will not allow 
    the user to update. Once the score is recorded, it is validated and checked for errors, then
    stored.
        *Status: Fully Functional - no known bugs
##### Public Application

    Get Upcoming Matches: The user can view the list of upcoming matches from
    the current date and time, if a match is scheduled before the current date and time
    then it will not be listed. This is a viewable interface.
        *Status: Fully Functional - no known bugs

    Get Matches on Date: The user can select a specific date to view the matches
    that are currently scheduled for that date. This is a viewable interface.
        *Status: Fully Functional - no known bugs

    Get Team Games: The user may enter a team name (case-sensitive) which will then
    display all the past, current, and upcoming matches for the desired team. This is
    a viewable interface, the input is validated and checked for spelling errors or case-sensitive 
    letters. 
        *Status: Fully Functional - no known bugs

    Get Team Lineups: The user selects a match from the list and once that match is selected
    both teams lineups are shown below. The user may select a different match to view once done.
    This is a viewable interface, the user selects a match from a list which is already
    validated, there shouldn't be any errors.
        *Status: Fully Functional - no known bugs


#### Application Startup

    When a user wants to use the application, they must first run the Server Runnable. 
    Once the server interface has opened, the user can select to load from a file or start
    the Server, once the server is started then the user may open start either the Admin  
    or Public applications.