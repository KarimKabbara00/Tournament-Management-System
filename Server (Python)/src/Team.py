import Country
import Player


class Team:
    """
    This defines the team class.
    The team class is responsible
    for storing the players belonging to it
    """
    _name: str  # team name
    _player_list: list  # list of players in team
    _country: Country   # Country object

    def __init__(self, name: str, country: Country):
        """
        This is the constructor
        for the Team class. It sets
        the name, and country it belongs to
        as well as instantiating the player list
        :param name: team name
        :param country: country object
        """
        self._name = name
        self._country = country
        self._player_list = []

    def add_player(self, player: Player):
        """
        Adds a player to the team
        :param player: Player object
        """
        self._player_list.append(player)

    def __str__(self) -> str:
        """
        Returns name of team
        :return: name of team (String)
        """
        return self._name

    @property
    def get_country(self) -> Country:
        """
        Returns Country object
        :return: Country object (Country)
        """
        return self._country

    @property
    def get_squad(self) -> []:
        """
        Returns list of players
        :return: list of players (List)
        """
        return self._player_list

    @property
    def get_name(self) -> str:
        """
        Returns team name
        :return: team name (String)
        """
        return self._name

    @property
    def get_squad_size(self) -> int:
        """
        Gets number of players in team
        :return: number of players (Int)
        """
        return len(self._player_list)
