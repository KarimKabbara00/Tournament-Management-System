from datetime import datetime, date
from Country import Country
from Match import Match
from Player import Player
from Referee import Referee
from Team import Team
import pickle


class Tournament(object):
    """
    This defines the Tournament class
    which holds all the methods and attributes
    of the Tournament. This class
    is responsible for the logic behind all
    the methods defined in this project.
    This class allows for creation of
    countries, teams, players, referees,
    and matches, then assigning each
    to a match.
    Inherits object for serialization.
    """
    _name: str  # name of the tournament
    _start_date: date  # start date
    _end_date: date  # end date
    _country_list: list  # list of countries
    _match_list: list  # list of matches
    _recorded_match_list: list  # list of recorded matches
    _loaded_tournament: None  # tournament to be loaded

    def __init__(self, name: str, start_date: date, end_date: date):
        """
        This is the constructor
        for the Tournament class. It sets
        the name, start date, end date
        as well as initialize arrayLists
        to store all the matches,
        countries,and recorded matches
        :param name: name of the tournament
        :param start_date: start date
        :param end_date:   end date
        """
        self._name = name
        self._start_date = start_date
        self._end_date = end_date
        self._country_list = []
        self._recorded_match_list = []
        self._match_list = []

    def load_from_file(self, filename: str):
        """
        Loads tournament from file
        :param filename: file name
        :return: loaded tournament (Tournament)
        """
        with open(filename, mode='rb') as binary_file:
            self._loaded_tournament = pickle.load(binary_file)
        return self._loaded_tournament

    def save_to_file(self, filename: str):
        """
        Saves tournament to file
        :param filename: file name
        """
        with open(filename, mode='wb') as binary_file:
            pickle.dump(self, binary_file)

    def add_country(self, country_name: str):
        """
        This method adds a country to the country list after
        checking if the country doesn't already exist.
        :param country_name: name of country
        """
        exists: bool = False
        for c in self._country_list:
            if c.get_country_name == country_name:
                exists = True
        if not exists:
            self._country_list.append(Country(country_name))

    def add_team(self, team_name: str, country_name: str):
        """
        Adds a team to a country.
        :param team_name: team name
        :param country_name: country name
        """
        exists: bool = False
        for c in self._country_list:
            if c.get_country_name == country_name:
                for t in c.get_teams_in_country:
                    if t.get_name == team_name:
                        exists = True
                if not exists:
                    c.add_team(Team(team_name, c))
                    break

    def add_referee(self, name: str, country: str):
        """
        Adds a referee to a country
        :param name: referee name
        :param country: country name
        """
        exists: bool = False
        for c in self._country_list:
            if c.get_country_name == country:
                for r in c.get_referees_in_country:
                    if r.get_name == name:
                        exists = True
                if not exists:
                    c.add_referee(Referee(name, c))
                    break

    def add_player(self, team_name: str, player_name: str, age: int, weight: float, height: float):
        """
        Adds a player to a team
        :param team_name: team name
        :param player_name: player name
        :param age: player age
        :param weight: player weight
        :param height: player height
        """
        exists: bool = False
        for country in self._country_list:
            for team in country.get_teams_in_country:
                if team.get_name == team_name:
                    for player in team.get_squad:
                        if player.get_name == player_name:
                            exists = True
                    if team.get_squad_size < 35 and not exists:
                        team.add_player(Player(player_name, age, weight, height))

    def add_match(self, start_time: datetime, team_a: str, team_b: str):
        """
        Creates a match
        :param start_time: start time of match
        :param team_a: team A
        :param team_b: team B
        """
        exists: bool = False
        team_1: Team = None
        team_2: Team = None
        for match in self._match_list:
            if match.get_start_time == start_time:
                exists = True

        for country in self._country_list:
            for team in country.get_teams_in_country:
                if team.get_name == team_a:
                    team_1 = team
                elif team.get_name == team_b:
                    team_2 = team

        if (team_1 is not None and team_2 is not None) and (team_1.get_name != team_2.get_name) and not exists:
            self._match_list.append(Match(start_time, team_1, team_2))

    def add_referee_to_match(self, start_time: datetime, referee_name: str):
        """
        Adds a referee to a match
        :param start_time: match start time
        :param referee_name: referee name
        """
        already_exists: bool = False
        for match in self._match_list:
            if match.get_start_time == start_time:
                for country in self._country_list:
                    for referee in country.get_referees_in_country:
                        if referee.get_name == referee_name:
                            if referee.get_country != match.get_lineup_a_country.get_country_name and \
                                    referee.get_country != match.get_lineup_b_country.get_country_name:
                                for ref in match.get_referee_list:
                                    if ref.get_name == referee_name:
                                        already_exists = True
                                        break
                                if not already_exists:
                                    match.add_referee(referee)

    def add_player_to_match(self, start_time: str, team_name: str, player_name: str):
        """
        Adds a player to a match lineup
        :param start_time: start time of match
        :param team_name:  name of team
        :param player_name: player name
        """
        for match in self._match_list:
            if match.get_start_time.strftime('%Y-%m-%d %H:%M') == start_time:
                for country in self._country_list:
                    for team in country.get_teams_in_country:
                        if team.get_name == team_name:
                            for player in team.get_squad:
                                if player.get_name == player_name:
                                    match.add_player(player, team)

    def set_match_score(self, start_time: str, score_a: int, score_b: int):
        """
        Sets a past match score
        :param start_time: match start time
        :param score_a: team A score
        :param score_b: team B score
        """
        for match in self._match_list:
            if match.get_start_time.strftime('%Y-%m-%d %H:%M') == start_time:
                if match.get_start_time < datetime.now():
                    if not match.get_is_recorded:
                        match.set_match_score(score_a, score_b)
                        self._recorded_match_list.append(match)
                        break

    def get_upcoming_matches(self) -> []:
        """
        Gets all upcoming matches
        :return: list of upcoming matches (List)
        """
        upcoming_matches = []
        for match in self._match_list:
            if match.is_upcoming():
                upcoming_matches.append(match)
        return upcoming_matches

    def get_matches_on(self, start_date: str) -> []:
        """
        Gets all matches on a specific date
        :param start_date: match start date
        :return: list of matches on a date (List)
        """
        matches = []
        for match in self._match_list:
            if match.get_start_time.date().__str__() == start_date:
                matches.append(match)
        return matches

    def get_matches_for(self, team_name: str) -> []:
        """
        Gets matches for a team
        :param team_name: team name
        :return: list of matches for a team (List)
        """
        matches = []
        for match in self._match_list:
            if match.get_team_a.get_team.get_name == team_name or match.get_team_b.get_team.get_name == team_name:
                matches.append(match)
        return matches

    def get_match_lineups(self, start_datetime: datetime) -> []:
        """
        Gets lineups for a match
        :param start_datetime: match start time
        :return: list of lineups for a match (List)
        """
        lineups = []
        for match in self._match_list:
            if match.get_start_time.strftime('%Y-%m-%d %H:%M') == start_datetime.strftime('%Y-%m-%d %H:%M'):
                lineups.append(match.get_team_a)
                lineups.append(match.get_team_b)
                break
        return lineups

    def get_country_list(self) -> str:
        """
        Gets list of countries as strings
        :return: String of matches (String)
        """
        countries: str = ""
        for country in self._country_list:
            countries += country.get_country_name + "|"
        return countries

    def get_team_list(self, country: str) -> str:
        """
        Gets list of teams for a
        country as a string
        :param country: country name
        :return: String of teams (String)
        """
        teams: str = ""
        for c in self._country_list:
            if c.get_country_name == country:
                for team in c.get_teams_in_country:
                    teams += team.get_name + "|"
                break
        return teams

    def get_all_teams(self) -> str:
        """
        Gets all teams as a string
        :return: String of all teams (String)
        """
        teams: str = ""
        for c in self._country_list:
            for team in c.get_teams_in_country:
                teams += team.get_name + "|"
        return teams

    def get_player_list(self, team: str) -> str:
        """
        Gets all players for a team as a string
        :param team: team name
        :return: String of players for team (String)
        """
        players: str = ""
        for country in self._country_list:
            for t in country.get_teams_in_country:
                if t.get_name == team:
                    for player in t.get_squad:
                        players += player.get_name + "|"
                    return players

    def get_referee_list(self) -> str:
        """
        Gets all referees as strings
        :return: All referees as strings (Strings)
        """
        referees: str = ""
        for country in self._country_list:
            for referee in country.get_referees_in_country:
                referees += referee.get_name + "|"
        return referees

    def get_matches(self) -> str:
        """
        Gets all matches
        :return: __str__ of all matches (String)
        """
        matches: str = ""
        for match in self._match_list:
            matches += match.__str__() + "|"
        return matches

    def get_referees_on(self, start_datetime: datetime) -> str:
        """
        Gets all referees for a match as strings
        :param start_datetime: match start time
        :return: String of all referees for a match (String)
        """
        referees: str = ""
        for match in self._match_list:
            if match.get_start_time.strftime('%Y-%m-%d %H:%M') == start_datetime.strftime('%Y-%m-%d %H:%M'):
                for referee in match.get_referee_list:
                    referees += referee.get_name + "|"
                break
        return referees

    def get_teams_for_match(self, start_datetime: datetime) -> str:
        """
        Gets all teams for a match
        :param start_datetime: match start time
        :return: String of teams for a match (String)
        """
        teams: str = ""
        for lineup in self.get_match_lineups(start_datetime):
            teams += lineup.get_team.get_name + "|"
        return teams

    def get_players_in_team(self, team: str, start_datetime: datetime) -> str:
        """
        Gets all players in a team as strings
        :param team: team name
        :param start_datetime: match start time
        :return: String of players in a team (String)
        """
        players: str = ""
        for lineup in self.get_match_lineups(start_datetime):
            if lineup.get_team.get_name == team:
                for player in lineup.get_team.get_squad:
                    players += player.get_name + "|"
                break
        return players

    def get_players_in_lineup(self, team: str, start_datetime: datetime) -> str:
        """
        Gets all players in a lineup as a strings
        :param team: team name
        :param start_datetime: match start time
        :return: String of players in a lineup (String)
        """
        players: str = ""
        for lineup in self.get_match_lineups(start_datetime):
            if lineup.get_team.get_name == team:
                for player in lineup.get_players:
                    players += player.get_name + "|"
                break
        return players

    def get_past_matches(self) -> str:
        """
        Gets all matches before current time.
        :return: String of all matches before now (String)
        """
        matches: str = ""
        for match in self._match_list:
            if match.get_start_time < datetime.now():
                matches += match.__str__() + "|"
        return matches

    def get_recorded_matches(self) -> str:
        """
        Gets all recorded matches as strings
        :return: String of all recorded matches (String)
        """
        matches: str = ""
        for match in self._recorded_match_list:
            matches += match.recorded_to_string() + "|"
        return matches

    def get_upcoming_matches_as_strings(self) -> str:
        """
        Gets upcoming matches as strings
        :return: String of upcoming matches (String)
        """
        upcoming_matches: str = ""
        for match in self.get_upcoming_matches():
            upcoming_matches += match.__str__() + "|"
        return upcoming_matches

    def get_matches_on_as_strings(self, start_date: str) -> str:
        """
        Gets all matches on a date
        :param start_date: start date of match
        :return: String of all matches on a date (String)
        """
        matches: str = ""
        for match in self.get_matches_on(start_date):
            if match.get_start_time < datetime.now():
                matches += match.recorded_to_string() + "|"
            else:
                matches += match.__str__() + "|"
        return matches

    def get_matches_for_as_strings(self, team_name: str) -> str:
        """
        Gets all matches for a team as strings
        :param team_name: team name
        :return: String of all natches for a team (String)
        """
        matches: str = ""
        for match in self.get_matches_for(team_name):
            if match.get_start_time < datetime.now():
                matches += match.recorded_to_string() + "|"
            else:
                matches += match.__str__() + "|"
        return matches

    def get_match_lineups_as_strings(self, match_name: str) -> str:
        """
        Gets lineups for a match as strings
        :param match_name: name of match
        :return: String of match lineups (String)
        """
        try:
            team_1: list = match_name.split(" vs. ")
            team_2: list = team_1[1].split(" on ")
            date_time: str = team_2[1][0:10] + " " + team_2[1][14:]
            line_up_team_1: str = ""
            line_up_team_2: str = ""
            date_time: datetime = datetime(int(date_time[0:4]), int(date_time[5:7]), int(date_time[8:10]),
                                           int(date_time[11:13]), int(date_time[14:16]))
        except IndexError:
            return ""

        for lineup in self.get_match_lineups(date_time):
            if lineup.get_team.get_name == team_1[0]:
                for player in lineup.get_players:
                    line_up_team_1 += player.get_name + "|"
            elif lineup.get_team.get_name == team_2[0]:
                for player in lineup.get_players:
                    line_up_team_2 += player.get_name + "|"
        return line_up_team_1 + "$" + line_up_team_2

    @property
    def get_tournament_name(self):
        """
        Returns tournament name
        :return: Name of tournament (String)
        """
        return self._name
