from sqlalchemy import Column, ForeignKey, String, Integer, create_engine, Enum
from sqlalchemy.orm import relationship

from models.base import Base
from models.basemixin import BaseMixin


class QuestionTag(Base, BaseMixin):
    __tablename__ = "question_tag_mapping"
    __table_args__ = {"mysql_engine": "InnoDB"}

    question_id = Column(Integer, primary_key=True)
    tag_id = Column(Integer, primary_key=True)

    def __repr__(self):
        return "<Member(name={0})>".format(self.name)
