from sqlalchemy import Column, ForeignKey, String, Integer, create_engine, Enum
from sqlalchemy.orm import relationship

from models.base import Base
from models.basemixin import BaseMixin


class Member(Base, BaseMixin):
    __tablename__ = "tags"
    __table_args__ = {"mysql_engine": "InnoDB"}

    tag_id = Column(Integer, primary_key=True, autoincrement=True)
    tag = Column(String(length=50))
    description = Column(String(length=100))

    def __repr__(self):
        return "<Tag(name={0})>".format(self.name)
