from _thread import start_new_thread
from enum import Enum
from Server import Server


class ServerApplication:
    """
    This defines the ServerApplication class
    which holds all the methods and attributes
    of the ServerApplication. This class
    is responsible for loading, saving serialized
    object files. It is also responsible for starting
    and stopping the server.
    """

    _filename: str  # file name to write/load to
    _server: Server  # server to be instantiated

    def __init__(self):
        """
        This is the constructor
        for the ServerApplication class
        It sets the file name to serialize to
        """
        self._filename = "tournament.bin"

    def start_server(self):
        """
        This method instantiates the server
        with a given port and IP. Then the server
        is run
        """
        self._server = Server(9988, "127.0.0.1")
        self._server.run()

    def shutdown(self):
        """
        This method shuts down the server
        """
        self._server.shutdown()

    def load_from_file(self):
        """
        This method calls the load_from_file() method
        in the tournament class and loads the saved
        file if it exists. An exception is thrown otherwise.
        """
        self._server.tournament = self._server.tournament.load_from_file(self._filename)

    def save_to_file(self):
        """
        This method calls the save_from_file() method
        in the tournament class and saves the current
        tournament object.
        """
        self._server.tournament.save_to_file(self._filename)


class Options(Enum):
    """
    This class is used for
    better readability in the
    conditional statements in
    the main function
    """

    load = 1
    start = 2
    stop = 3
    save = 4
    exit = 5


if __name__ == '__main__':
    """
    Runnable code. Instantiates
    the server application and
    allows for server operation
    """

    choice: int  # CLI user input
    keep_running: bool = True  # keep running till exited
    started: bool = False  # if server is started
    server_application = ServerApplication()  # instantiate server application

    # options to choose from
    option_load = "1. Load Previous State"
    option_start = "2. Start Server"
    option_stop = "3. Stop Server"
    option_save = "4. Save to File"
    option_exit = "5. Exit"

    while keep_running:
        print(f"{option_load}\n{option_start}\n{option_stop}\n{option_save}\n{option_exit}\n")
        choice = int(input("Choice: "))

        if choice == Options.load.value:
            if started:
                server_application.load_from_file()
            else:
                print("Server must be started to load!")

        elif choice == Options.start.value:
            if not started:
                start_new_thread(server_application.start_server, ())
                started = True
            else:
                print("Server already running!")

        elif choice == Options.stop.value:
            if started:
                server_application.shutdown()
                started = False
            else:
                print("Server not started!")

        elif choice == Options.save.value:
            if not started:
                server_application.save_to_file()
            else:
                print("Shutdown the server first!")

        elif choice == Options.exit.value:
            keep_running = False

        else:
            print("Invalid choice")
        print("\n\n")
