from sqlalchemy import create_engine
from commands.command_handler import CommandHandler
from utils.db_utils import init_models


class SoApp:
    def __init__(self) -> None:
        self._cmd_hdl = CommandHandler()

    def main_loop(self):
        print("Welcome to stack over flow app, press q to quit")
        while True:
            inp = input().lower()
            if inp == "q":
                print("bye...")
            self._cmd_hdl.execute_cmd(inp)


if __name__ == "__main__":
    app = SoApp()
    init_models()
    app.main_loop()
    print("Have a nice day")
