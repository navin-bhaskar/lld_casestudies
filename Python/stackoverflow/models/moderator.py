from sqlalchemy import Column, String, Integer, Enum


from models.member import Member


class Moderator(Member):
    __tablename__ = "users"
    __table_args__ = {"mysql_engine": "InnoDB"}

    __mapper_args__ = {
        "polymorphic_identity": "moderator",
        "polymorphic_on": "memeber_type",
    }

    def __repr__(self):
        return "<Moderator(name={0})>".format(self.name)

    def delete_question(self, question):
        pass

    def undelete_question(self, question):
        pass
