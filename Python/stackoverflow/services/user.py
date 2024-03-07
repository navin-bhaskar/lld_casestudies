from sqlalchemy.orm import Session
from sqlalchemy import select
from exceptions.db_exceptions import UserNotFound

from models.member import Member
from enums.userroles import UserRoles
from repo.user_repo import UserRepo


from utils.helpers import validate_and_get_entity_id, get_entity_name_from_id

PREFIX = "U"


class UserService:

    def __init__(self) -> None:
        self._usr_repo = UserRepo()

    def add_user(self, user_name: str, user_email: str, role: UserRoles) -> str:
        usr: Member = self._usr_repo.add_user(user_name, user_email, role)
        return get_entity_name_from_id(usr.user_id, PREFIX)

    def get_user_by_id(self, user_id: int) -> Member:
        usr: Member = self._usr_repo.get_by_id(user_id)
        if not usr:
            raise UserNotFound(str(user_id))
        return usr

    def get_user_by_usr_id(self, user_id: str):
        try:
            usr_id = validate_and_get_entity_id(user_id, PREFIX)
        except ValueError:
            raise UserNotFound(user_id)
        try:
            usr: Member = self.get_user_by_id(usr_id)
            return usr
        except UserNotFound:
            raise UserNotFound(user_id)
