from sqlalchemy import Column, ForeignKey, String, Integer, create_engine, Enum
from sqlalchemy.orm import relationship

from models.base import Base
from models.basemixin import BaseMixin


class Member(Base, BaseMixin):
    __tablename__ = "members"
    __table_args__ = {"mysql_engine": "InnoDB"}

    user_id = Column(Integer, primary_key=True, autoincrement=True)
    memeber_type = Column(String(length=30))
    name = Column(String(length=50))
    email = Column(String(length=100))
    # role = Column(Enum(userroles.UserRoles))
    reputation_points = Column(Integer, default=0)

    __mapper_args__ = {
        "polymorphic_identity": "member",
        "polymorphic_on": "memeber_type",
    }

    def __repr__(self):
        return "<Member(name={0})>".format(self.name)
