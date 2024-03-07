class AddUserResponseDto:

    def __init__(self) -> None:
        self._user_id = None

    @property
    def user_id(self) -> str:
        return self._user_id

    @user_id.setter
    def user_id(self, value: str):
        self._user_id = value
