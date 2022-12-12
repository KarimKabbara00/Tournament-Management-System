from datetime import datetime
import Country
from LineUp import LineUp
import Player
import Referee
import Team


class Match:
    """
    This defines the Match class.
    The Match class is responsible
    for creating matches, setting lineups
    setting referees, and match scores
    """
    _date_time: datetime  # start time of match
    _score_team_a: int  # team A score
    _score_team_b: int  # team B score
    _line_up_a: LineUp  # lineup A
    _line_up_b: LineUp  # lineup B
    _referee_list: list  # list of referees for match
    _is_recorded: bool  # is score recorded

    def __init__(self, _date_time: datetime, team_a: Team, team_b: Team):
        """
        This is the constructor
        for the Match class. It sets
        the date/time, and lineups for teams
        A and B. This initializes the referee list.
        :param _date_time: start time of match
        :param team_a: team A object
        :param team_b: team B object
        """
        self._date_time = _date_time
        self._line_up_a = LineUp(team_a)
        self._line_up_b = LineUp(team_b)
        self._referee_list = []
        self._is_recorded = False

    @property
    def get_team_a(self) -> LineUp:
        """
        Returns lineup A
        :return: lineup A (Lineup)
        """
        return self._line_up_a

    @property
    def get_team_b(self) -> LineUp:
        """
        Returns lineup B
        :return: lineup B (Lineup)
        """
        return self._line_up_b

    @property
    def get_start_time(self) -> datetime:
        """
        Returns start time of match
        :return: start time (datetime)
        """
        return self._date_time

    @property
    def get_referee_list(self) -> []:
        """
        Returns referee list
        :return: referee list (List)
        """
        return self._referee_list

    @property
    def get_lineup_a_country(self) -> Country:
        """
        Gets Lineup A country
        :return: Lineup A country (Country)
        """
        return self._line_up_a.get_team.get_country

    @property
    def get_lineup_b_country(self) -> Country:
        """
        Gets Lineup B country
        :return: Lineup B country (Country)
        """
        return self._line_up_b.get_team.get_country

    @property
    def get_is_recorded(self) -> bool:
        """
        Returns if the match is recorded
        :return: true or false (Bool)
        """
        return self._is_recorded

    def is_upcoming(self) -> bool:
        """
        Returns if the match is upcoming
        :return: true or false (Bool)
        """
        return self._date_time > datetime.now()

    def add_player(self, player: Player, team: Team):
        """
        Adds a player to a lineup
        :param player: player object
        :param team: team object
        """
        flag: bool = True

        if self._line_up_a.get_team == team:
            for p in self._line_up_a.get_players:
                if p.get_name == player.get_name:
                    flag = False
                    break
            if flag:
                self._line_up_a.add_player(player)

        elif self._line_up_b.get_team == team:
            for p in self._line_up_b.get_players:
                if p.get_name == player.get_name:
                    flag = False
                    break
            if flag:
                self._line_up_b.add_player(player)

    def add_referee(self, referee: Referee):
        """
        Adds a referee to a match
        :param referee: referee object
        """
        self._referee_list.append(referee)

    def set_match_score(self, a: int, b: int):
        """
        Sets the match score
        :param a: team A score
        :param b: team B score
        """
        if not self._is_recorded:
            self._is_recorded = True
            self._score_team_a = a
            self._score_team_b = b

    def __str__(self) -> str:
        """
        String representation of match
        :return: match string (String)
        """
        return self._line_up_a.get_team.get_name + " vs. " + self._line_up_b.get_team.get_name + " on " + \
               str(self._date_time.date()) + " at " + str(self._date_time.time())

    def recorded_to_string(self) -> str:
        """
        Recorded string representation of match
        :return: recorded match string (String)
        """
        return self._line_up_a.get_team.get_name + " vs. " + self._line_up_b.get_team.get_name + " on " + \
               str(self._date_time.date()) + " at " + str(self._date_time.time()) + " --> Final Score: " + \
               str(self._score_team_a) + " - " + str(self._score_team_b)
