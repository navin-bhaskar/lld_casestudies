from sqlalchemy import Column, ForeignKey, String, Integer, create_engine, Enum
from sqlalchemy.orm import relationship

from models.base import Base
from models.basemixin import BaseMixin


class Reply(Base, BaseMixin):
    __tablename__ = "replies"
    __table_args__ = {"mysql_engine": "InnoDB"}

    reply_id = Column(Integer, primary_key=True, autoincrement=True)
    reply = Column(String(length=200))
    rpelied_by = Column(Integer, ForeignKey("members.user_id"))

    __mapper_args__ = {
        "polymorphic_identity": "member",
        "polymorphic_on": "memeber_type",
    }

    def __repr__(self):
        return "<Member(name={0})>".format(self.name)
