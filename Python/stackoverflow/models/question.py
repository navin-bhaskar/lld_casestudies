from sqlalchemy import Column, ForeignKey, String, Integer, create_engine, Enum
from sqlalchemy.orm import relationship

from models.base import Base
from models.basemixin import BaseMixin
from enums.questionstate import QuestionState


class Question(Base, BaseMixin):
    __tablename__ = "questions"
    question_id = Column(Integer, primary_key=True, autoincrement=True)
    title = Column(String(length=100))
    description = Column(String(length=500))
    votes = Column(Integer)
    state = Column(Enum(QuestionState))
    closing_remark = Column

    asked_by = Column(Integer, ForeignKey("members.user_id"))
    answers = relationship("Answer", back_populates="question_id")

    def __repr__(self):
        return "<Question(title={0})>".format(self.title)
