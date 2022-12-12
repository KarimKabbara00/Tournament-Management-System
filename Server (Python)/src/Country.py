import Referee
import Team


class Country:
    """
    This defines the Country class
    which holds all the methods and attributes
    of the Country. This class
    is responsible for adding teams to a country
    and retrieving necessary information
    """

    _country_name: str  # country name
    _teams_in_country: list  # list of teams in country
    _referees_in_country: list  # list of referees in country

    def __init__(self, country_name):
        """
        This is the constructor
        for the Country class. It sets
        the country name and initializes
        the team and referee lists
        :param country_name: Country name
        """
        self._country_name = country_name
        self._teams_in_country = []
        self._referees_in_country = []

    @property
    def get_country_name(self) -> str:
        """
        Returns country name
        :return: country name (String)
        """
        return self._country_name

    @property
    def get_teams_in_country(self) -> []:
        """
        Returns teams in country
        :return: teams in country (List)
        """
        return self._teams_in_country

    @property
    def get_referees_in_country(self) -> []:
        """
        Returns referees in country
        :return: referees in country (List)
        """
        return self._referees_in_country

    def add_team(self, team: Team):
        """
        Adds a team to the country
        :param team: Team object
        """
        self._teams_in_country.append(team)

    def add_referee(self, referee: Referee):
        """
        Adds a referee to a country
        :param referee: referee object
        """
        self._referees_in_country.append(referee)

    def __str__(self) -> str:
        """
        string reprentation of country
        :return: Country name (String)
        """
        return self._country_name
