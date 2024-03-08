from sqlalchemy import select

from enums.questionstate import QuestionState
from models.question import Question
from utils.db_utils import SessionManager


class QuestionRepo:

    def add_question(self, title, description, asked_by):
        with SessionManager() as session:
            question = Question(
                title=title,
                description=description,
                votes=0,
                state=QuestionState.open,
                asked_by=asked_by.user_id,
            )
            session.add(question)
            session.commit()
            return question

    def get_by_id(self, question_id: int):
        with SessionManager() as session:
            stmt = select(Question).where(Question.question_id == question_id)
            entries = session.execute(stmt).scalar()
            return entries
