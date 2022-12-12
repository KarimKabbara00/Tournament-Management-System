from Team import Team
from Player import Player


class LineUp:
    """
    This defines the Lineup class
    which holds all the methods and attributes
    of the Lineups. This class
    is responsible for adding players to a lineup
    and retrieving necessary information.
    """
    _team: Team # team object
    _line_up_players: list  # players in lineup

    def __init__(self, team: Team):
        """
        This is the constructor
        for the LineUp class. It sets
        the team and initializes the player list
        :param team: team object
        """
        self._team = team
        self._line_up_players = []

    @property
    def get_team(self) -> Team:
        """
        Gets the team for the lineup
        :return: Team object (Team)
        """
        return self._team

    @property
    def get_players(self) -> []:
        """
        Gets players in the lineup
        :return: Players (List)
        """
        return self._line_up_players

    def add_player(self, player: Player):
        """
        Adds a player to a lineup
        :param player: Player object
        """
        if len(self._line_up_players) < 11:  # if list has 10 or fewer players
            self._line_up_players.append(player)  # add player to line_up
        else:
            pass

    def __str__(self) -> str:
        """
        String representation of lineup
        :return: lineup as String (String)
        """
        line_up = ""
        for players in self._line_up_players:  # for each player in list
            line_up += (players.__name__ + "\n")  # print player name, then enter
        return line_up
