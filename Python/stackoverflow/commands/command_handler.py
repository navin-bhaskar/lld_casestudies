from commands.add_answer import AddAnswer
from commands.add_user import AddUser
from commands.ask_question import PostQuestion


class CommandHandler:

    def __init__(self) -> None:
        self._commands_list = [AddUser(), PostQuestion(), AddAnswer()]
        self._cmd_lookup = dict()
        self._all_help = ""

        for cmd in self._commands_list:
            cmd_name: str = cmd.get_name()
            self._cmd_lookup[cmd_name] = cmd
            self._all_help += cmd_name + ": " + cmd.get_help() + "\n"

    def execute_cmd(self, line: str):
        cmd = line.strip().lower()
        if cmd == "help":
            print(self._all_help)
            return
        if cmd not in self._cmd_lookup:
            print("Command was not found")
            return
        cmd_hndlr = self._cmd_lookup[cmd]
        try:
            cmd_hndlr.execute()
        except Exception as ex:
            print("Got an error: " + str(ex))
