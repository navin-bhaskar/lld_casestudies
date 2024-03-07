from commands.add_user import AddUser
from commands.post_question import PostQuestion


class CommandHandler:

    def __init__(self) -> None:
        self._commands_list = [AddUser(), PostQuestion()]
        self._cmd_lookup = dict()

        for cmd in self._commands_list:
            self._cmd_lookup[cmd.get_name()] = cmd

    def execute_cmd(self, line: str):
        cmd = line.strip()
        if cmd not in self._cmd_lookup:
            print("Command was not found")
            return
        cmd_hndlr = self._cmd_lookup[cmd]
        try:
            cmd_hndlr.execute()
        except Exception as ex:
            print("Got an error: " + str(ex))
