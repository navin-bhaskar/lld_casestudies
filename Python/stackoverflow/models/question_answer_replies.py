from sqlalchemy import Column, ForeignKey, String, Integer, create_engine, Enum
from sqlalchemy.orm import relationship

from models.base import Base
from models.basemixin import BaseMixin


class QuestionAnswerReply(Base, BaseMixin):
    __tablename__ = "question_answer_replies_mapping"
    __table_args__ = {"mysql_engine": "InnoDB"}

    enitity_id = Column(Integer, primary_key=True)
    reply_id = Column(Integer, primary_key=True)
