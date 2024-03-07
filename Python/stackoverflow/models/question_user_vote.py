from sqlalchemy import Column, ForeignKey, String, Integer, create_engine, Enum
from sqlalchemy.orm import relationship

from base import Base
from basemixin import BaseMixin
from enums.votetype import VoteType


class QuestionUserVote(Base, BaseMixin):
    __tablename__ = "question_user_vote"
    id = Column(Integer, primary_key=True, autoincrement=True)
    voted_by = Column(Integer, ForeignKey("members.user_id"))
    question_id = Column(Integer, ForeignKey("questions.id"))
    vote_type = Column(Enum(VoteType))

    # def __repr__(self):
    #     return "<Question(title={0})>".format(self.title)
