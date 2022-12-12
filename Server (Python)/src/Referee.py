import Country


class Referee:
    """
    This defines the Referee class.
    The Referee class is responsible
    for creating a referee and returning
    necessary information.
    """
    _name: str  # referee name
    _country: Country   # referee country

    def __init__(self, name: str, country: Country):
        """
        This is the constructor
        for the Referee class. It sets
        the referee name and country
        :param name: Referee name
        :param country: Referee country
        """
        self._name = name
        self._country = country

    @property
    def get_name(self) -> str:
        """
        Returns referee name
        :return: referee name (String)
        """
        return self._name

    @property
    def get_country(self) -> str:
        """
        Returns referee country name
        :return: referee country name (String)
        """
        return self._country.__str__()
