from typing import List
from exceptions.db_exceptions import QuestionNotFound
from models.member import Member
from repo.answer_repo import AnswerRepo
from repo.question_repo import QuestionRepo
from services.user import UserService
from utils.helpers import get_entity_name_from_id
from models.answer import Answer

PREFIX = "A"


class QuestionService:

    def __init__(self) -> None:
        self._user_service = UserService()
        self._question_repo = QuestionRepo()
        self._answer_repo = AnswerRepo()

    def add_answer(self, question_id, title, description, answerd_by):
        question = self._question_repo.get_by_id(question_id)
        answered_usr: Member = self._user_service.get_user_by_usr_id(answerd_by)
        answer: Answer = self._answer_repo.add_answer(
            question.question_id, title, description, answered_usr.user_id
        )
        return get_entity_name_from_id(answer.answer_id, PREFIX)
