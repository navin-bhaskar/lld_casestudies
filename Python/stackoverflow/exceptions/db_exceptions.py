class SOException(Exception):
    pass


class EnitityNotFound(SOException):
    pass


class UserNotFound(EnitityNotFound):
    def __init__(self, usr_id: str) -> None:
        super().__init__("User not found " + usr_id)


class QuestionNotFound(EnitityNotFound):
    def __init__(self, q_id: str) -> None:
        super().__init__("Question not found " + q_id)
