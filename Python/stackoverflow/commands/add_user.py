from commands.command import Command
from controllers.user_controller import UserContoller
from dto.add_user_request_dto import AddUserRequestDto
from dto.add_user_response_dto import AddUserResponseDto
from enums.userroles import UserRoles


class AddUser(Command):
    def __init__(self) -> None:
        super().__init__("add_user")
        self._user_controller = UserContoller()

    def execute(self):
        print("Enter user name: ")
        user_name = input()
        print("Enter user email: ")
        user_email = input()
        print("Enter role: valid roles ", [e.name for e in UserRoles])
        role = input()

        request = AddUserRequestDto()
        request.user_name = user_name
        request.user_role = role
        request.user_email = user_email

        response: AddUserResponseDto = self._user_controller.add_user(request)
        print("Created user with user ID " + response.user_id)

    def get_help(self):
        return """Adds an user """
