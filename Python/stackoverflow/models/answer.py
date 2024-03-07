from sqlalchemy import Column, ForeignKey, String, Integer, create_engine, Enum
from sqlalchemy.orm import relationship

from base import Base
from basemixin import BaseMixin


class Answer(Base, BaseMixin):
    __tablename__ = "answers"
    answer_id = Column(Integer, primary_key=True, autoincrement=True)
    title = Column(String(length=100))
    description = Column(String(length=500))
    votes = Column(Integer)
    answered_by = Column(Integer, ForeignKey("members.user_id"))
    question = relationship("Question.question_id")

    def __repr__(self):
        return "<Answer(title={0})>".format(self.title)
