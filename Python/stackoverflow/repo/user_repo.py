from sqlalchemy import select

from models.member import Member
from enums.userroles import UserRoles
from models.moderator import Moderator
from utils.db_utils import SessionManager


class UserRepo:
    def __init__(self) -> None:
        self._user_obj_lookup = {
            UserRoles.member: Member,
            UserRoles.moderator: Moderator,
        }

    def add_user(self, user_name, user_email, role):
        assert role in self._user_obj_lookup

        with SessionManager() as session:
            usr = self._user_obj_lookup[role](name=user_name, email=user_email)
            session.add(usr)
            session.commit()
            return usr

    def get_by_id(self, user_id: int):
        with SessionManager() as session:
            stmt = select(Member).where(Member.user_id == user_id)
            entries = session.execute(stmt).scalar()
            return entries
