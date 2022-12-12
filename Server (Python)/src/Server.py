import socket
import threading
from _thread import start_new_thread
from datetime import date
from ClientWorker import ClientWorker
from Tournament import Tournament


class Server:
    """
    This defines the Server class
    which holds all the methods and attributes
    of the Server. This class
    is responsible for allowing hosts to connect
    to it. When a connection is received, the server
    is responsible in delegating the connecting client
    to a clientWorker to handle all its requests.
    """

    _port: int  # port number to connect to
    _host: str  # host ip to connect to
    _socket: socket  # socket to create
    _keep_running: bool  # set to false when shut down
    _client_list: list  # list of all connected clients
    tournament: Tournament  # tournament object
    print_lock: threading = threading.Lock()  # for synchronization

    def __init__(self, port: int, host: str):
        """
        Initializes the server object
        :param: port (int)
        :param: host (str)
        """
        self._port = port
        self._host = host
        self._socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self._socket.bind((host, port))
        self._keep_running = True
        self.tournament = Tournament("blankTournament", date(9999, 12, 30), date(9999, 12, 31))
        self._client_list = []

    def wait_for_connection(self):
        """
        Waits for a connection to the server
        :param: port (int)
        :param: host (str)
        :return: s (socket)
        """
        s: socket = None
        self._socket.listen(20)
        while s is None:
            try:
                s, addr = self._socket.accept()
            except OSError:
                pass
        return s

    def run(self):
        """
        This method runs the server.
        It continuously calls wait_for_connection()
        and assigns any incoming connections to a
        clientWorker. The server receives the first
        message from the client telling the server
        if it is an admin or not. Finally, the thread
        is started and the server waits for new connections.
        """
        while self._keep_running:
            s: socket = self.wait_for_connection()
            worker = ClientWorker(self, s)
            self._client_list.append(worker)
            start_new_thread(worker.run, ())

    @property
    def tournament(self):
        """
        Returns tournament object
        :return: tournament object (Tournament)
        """
        return self._tournament

    @tournament.setter
    def tournament(self, new_tournament: Tournament):
        """
        Sets tournament object
        :param new_tournament: When loading tournament
        """
        self._tournament = new_tournament

    def shutdown(self):
        """
        Loops through all clients and shuts them down.
        Then the server socket is closed and the loop
        is ended
        """
        for client in self._client_list:
            client.close_connection()
        self._socket.close()
        self._keep_running = False
