class Player:
    """
    This defines the Player class.
    The Player class is responsible
    for creating a player and returning
    necessary information.
    """
    _name: str  # player name
    _age: int   # player age
    _height: float  # player height
    _weight: float  # player weight

    def __init__(self, name: str, age: int, height: float, weight: float):
        """
        This is the constructor
        for the player class. It sets
        the name, age, height and weight.
        :param name: player name
        :param age: player age
        :param height: player height
        :param weight: player weight
        """
        self._name = name
        self._age = age
        self._height = height
        self._weight = weight

    @property
    def get_name(self) -> str:
        """
        Returns player name
        :return: player name (String)
        """
        return self._name

    @property
    def get_age(self) -> int:
        """
        Gets player age
        :return: player age (Int)
        """
        return self._age

    @property
    def get_height(self) -> float:
        """
        Gets player height
        :return: player height (Float)
        """
        return self._height

    @property
    def get_weight(self) -> float:
        """
        Returns player weight
        :return: player weight (Float)
        """
        return self._weight
