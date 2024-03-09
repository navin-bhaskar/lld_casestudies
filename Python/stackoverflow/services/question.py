from typing import List
from exceptions.db_exceptions import QuestionNotFound
from models.member import Member
from models.question import Question
from repo.question_repo import QuestionRepo
from services.user import UserService
from utils.helpers import get_entity_name_from_id, validate_and_get_entity_id

PREFIX = "Q"


class QuestionService:

    def __init__(self) -> None:
        self._user_service = UserService()
        self._question_repo = QuestionRepo()

    def ask(self, title, description, asked_by) -> str:
        asked_usr: Member = self._user_service.get_user_by_usr_id(asked_by)
        question = self._question_repo.add_question(title, description, asked_usr)
        return get_entity_name_from_id(question.question_id, PREFIX)

    def get_question_by_id(self, q_id: int) -> Question:
        question: Question = self._question_repo.get_by_id(q_id)
        if not question:
            raise QuestionNotFound(str(q_id))
        return question

    def get_question_by_question_id(self, question_id: str) -> Question:
        try:
            q_id = validate_and_get_entity_id(question_id, PREFIX)
        except ValueError:
            raise QuestionNotFound(q_id)
        try:
            question: Question = self.get_user_by_id(q_id)
            return question
        except QuestionNotFound:
            raise QuestionNotFound(q_id)
