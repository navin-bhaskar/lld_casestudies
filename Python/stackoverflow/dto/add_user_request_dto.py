from enums.userroles import UserRoles


class AddUserRequestDto:

    def __init__(self) -> None:
        self._usr_name = None
        self._usr_email = None
        self._usr_role = None

    @property
    def user_name(self):
        return self._usr_name

    @user_name.setter
    def user_name(self, usr_name):
        if not usr_name or not isinstance(usr_name, str):
            raise ValueError("Not a valid user name")
        self._usr_name = usr_name

    @property
    def user_email(self):
        return self._usr_email

    @user_email.setter
    def user_email(self, email):
        if not email or not isinstance(email, str):
            # TODO: Add more email validation
            raise ValueError("Not a valid user email")

        self._usr_email = email

    @property
    def user_role(self):
        return self._usr_role

    @user_role.setter
    def user_role(self, usr_role):
        try:
            role = UserRoles[usr_role.lower()]
        except Exception as ex:
            raise ValueError("Not a valid user role")
        self._usr_role = role
