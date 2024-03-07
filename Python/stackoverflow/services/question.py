from typing import List
from exceptions.db_exceptions import QuestionNotFound
from models.member import Member
from repo.question_repo import QuestionRepo
from services.user import UserService
from utils.helpers import get_entity_name_from_id

PREFIX = "Q"


class QuestionService:

    def __init__(self) -> None:
        self._user_service = UserService()
        self._question_repo = QuestionRepo()

    def ask(self, title, description, asked_by):
        asked_usr: Member = self._user_service.get_user_by_usr_id(asked_by)
        question = self._question_repo.add_question(title, description, asked_usr)
        return get_entity_name_from_id(question.question_id, PREFIX)
