from enums.questionstate import QuestionState
from models.answer import Answer
from utils.db_utils import SessionManager


class AnswerRepo:

    def add_answer(self, question_id, title, description, answered_by):
        with SessionManager() as session:
            ans = Answer(
                title=title,
                description=description,
                votes=0,
                answered_by=answered_by,
                question_id=question_id,
            )
            session.add(ans)
            session.commit()
            return ans
