import socket
from datetime import date, datetime
import Server
from Tournament import Tournament


class ClientWorker:
    """
    This defines the ClientWorker class
    which holds all the methods and attributes
    of the ClientWorker. This class
    is responsible for receiving commands from
    the client and translating that to the
    correct function call.
    """
    _server: socket  # server socket
    _connection: socket  # individual client socket
    _keepRunning: bool  # true until client shut down
    _isAdmin: bool  # if client is admin

    def __init__(self, server: Server, s: socket):
        """
        This is the constructor for the
        clientWorker. It assigns the server
        and initializes the client socket.
        :param server: server socket
        :param s: client socket
        """
        self._server = server
        self._connection = s
        self._keepRunning = True

    def run(self):
        """
        This method runs the clientWorker and
        continuously receives commands from the client.
        The first message from the client tells the client worker
        if the client is an admin or not.
        The command is then sent to the process_client_message()
        method to execute the appropriate method.
        """
        try:
            temp = str(self._connection.recv(1024))
            if temp.find("false") != -1:
                self._isAdmin = False
            if temp.find("true") != -1:
                self._isAdmin = True
        except OSError:
            pass

        while True:
            try:
                command = str(self._connection.recv(1024))
                self.process_client_message(command)
            except Exception:
                self.close_connection()
                break

    def process_client_message(self, command: str):
        """
        This method process the command
        sent from the client and calls the
        appropriate function from the tournament.
        The commands are received in this format:
             Command|arg1|arg2|arg3|...
        The first section is the command to be
        executed, and the rest are the arguments
        that are necessary for that command.
        The input is split by '|' into an array.
        :param command: command from the client
        """
        args = command[2:-5].split("|")
        self._server.print_lock.acquire()   # synchronization: Acquire the lock

        if self._isAdmin:
            if args[0] == "Create Tournament":
                self._server.tournament = Tournament(args[1],
                                                     date(int(args[2][0:4]), int(args[2][5:7]), int(args[2][8:])),
                                                     date(int(args[3][0:4]), int(args[3][5:7]), int(args[3][8:])))
            elif args[0] == "Add Participating Country":
                self._server.tournament.add_country(args[1])
            elif args[0] == "Add Team":
                self._server.tournament.add_team(args[1], args[2])
            elif args[0] == "Add Players" and args[1] != '' and args[2] != '' and args[3] != '' and args[4] != '' \
                    and args[5] != '':
                self._server.tournament.add_player(args[1], args[2], int(args[3]), int(args[4]), int(args[5]))
            elif args[0] == "Add Referee":
                self._server.tournament.add_referee(args[1], args[2])
            elif args[0] == "Create Match" and args[1] != '' and args[2] != '' and args[3] != '' and args[4] != '':
                self._server.tournament.add_match(datetime(int(args[1][0:4]), int(args[1][5:7]), int(args[1][8:]),
                                                           int(args[4][0:2]), int(args[4][3:])), args[2], args[3])
            elif args[0] == "Add Referee to Match":
                date_time = args[1][-22:]
                start_date = datetime(int(date_time[0:4]), int(date_time[5:7]), int(date_time[8:10]),
                                      int(date_time[14:16]), int(date_time[17:19]))
                self._server.tournament.add_referee_to_match(start_date, args[2])
            elif args[0] == "Add Player to LineUp":
                date_time = args[1][-22:]
                start_date = date_time[0:4] + '-' + date_time[5:7] + '-' + date_time[8:10] + ' ' + date_time[14:16] \
                             + ':' + date_time[17:19]
                self._server.tournament.add_player_to_match(start_date, args[2], args[3])
            elif args[0] == "Record Score":
                date_time = args[1][-22:]
                start_date = date_time[0:4] + '-' + date_time[5:7] + '-' + date_time[8:10] + ' ' + date_time[14:16] \
                             + ':' + date_time[17:19]
                self._server.tournament.set_match_score(start_date, int(args[2]), int(args[3]))

            # Additional Commands
            elif args[0] == "Get Tournament Name":
                self._connection.send((self._server.tournament.get_tournament_name + '\r\n').encode())
            elif args[0] == "Get Countries":
                self._connection.send((self._server.tournament.get_country_list() + '\r\n').encode())
            elif args[0] == "Get Teams":
                self._connection.send((self._server.tournament.get_team_list(args[1]) + '\r\n').encode())
            elif args[0] == "Get All Teams":
                self._connection.send((self._server.tournament.get_all_teams() + '\r\n').encode())
            elif args[0] == "Get Players":
                self._connection.send((self._server.tournament.get_player_list(args[1]) + '\r\n').encode())
            elif args[0] == "Get Referees":
                self._connection.send((self._server.tournament.get_referee_list() + '\r\n').encode())
            elif args[0] == "Get Matches":
                self._connection.send((self._server.tournament.get_matches() + '\r\n').encode())
            elif args[0] == "Get Referees for Match":
                date_time = args[1][-22:]
                date_time = datetime(int(date_time[0:4]), int(date_time[5:7]), int(date_time[8:10]),
                                     int(date_time[14:16]), int(date_time[17:19]))
                self._connection.send((self._server.tournament.get_referees_on(date_time) + '\r\n').encode())
            elif args[0] == "Get Teams in Match":
                date_time = args[1][-22:]
                self._connection.send((self._server.tournament.get_teams_for_match(datetime(int(date_time[0:4]),
                                                                                            int(date_time[5:7]),
                                                                                            int(date_time[8:10]),
                                                                                            int(date_time[14:16]),
                                                                                            int(date_time[17:19])))
                                       + '\r\n').encode())
            elif args[0] == "Get Players in Team":
                date_time = args[2][-22:]
                self._connection.send(
                    (self._server.tournament.get_players_in_team(args[1], datetime(int(date_time[0:4]),
                                                                                   int(date_time[5:7]),
                                                                                   int(date_time[8:10]),
                                                                                   int(date_time[14:16]),
                                                                                   int(date_time[17:19])))
                     + '\r\n').encode())
            elif args[0] == "Get Players in LineUp":
                date_time = args[2][-22:]
                self._connection.send(
                    (self._server.tournament.get_players_in_lineup(args[1], datetime(int(date_time[0:4]),
                                                                                     int(date_time[5:7]),
                                                                                     int(date_time[8:10]),
                                                                                     int(date_time[14:16]),
                                                                                     int(date_time[17:19])))
                     + '\r\n').encode())
            elif args[0] == "Get Past Matches":
                self._connection.send((self._server.tournament.get_past_matches() + '\r\n').encode())
            elif args[0] == "Get Recorded Matches":
                self._connection.send((self._server.tournament.get_recorded_matches() + '\r\n').encode())

        else:
            if args[0] == "Show Upcoming Matches":
                self._connection.send((self._server.tournament.get_upcoming_matches_as_strings() + '\r\n').encode())
            elif args[0] == "Date List of Matches":
                self._connection.send((self._server.tournament.get_matches_on_as_strings(args[1]) + '\r\n').encode())
            elif args[0] == "Team Matches":
                self._connection.send((self._server.tournament.get_matches_for_as_strings(args[1]) + '\r\n').encode())
            elif args[0] == "Get All Matches":
                self._connection.send((self._server.tournament.get_matches() + '\r\n').encode())
            elif args[0] == "Team Lineups":
                self._connection.send((self._server.tournament.get_match_lineups_as_strings(args[1]) + '\r\n').encode())

        self._server.print_lock.release()   # synchronization: Release the lock

    def close_connection(self):
        """
        Closes the socket
        """
        self._connection.close()
