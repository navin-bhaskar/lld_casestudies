from typing import List
from appconfig.appconfig import AppConfig
from dto.add_user_request_dto import AddUserRequestDto
from dto.add_user_response_dto import AddUserResponseDto
from exceptions.db_exceptions import UserNotFound
from models.member import Member
from services.user import UserService


class UserContoller:

    def __init__(self) -> None:
        self._user_service = UserService()

    def add_user(self, request_dto: AddUserRequestDto) -> AddUserResponseDto:
        user_name = request_dto.user_name
        user_email = request_dto.user_email
        role = request_dto.user_role
        user_id: str = self._user_service.add_user(user_name, user_email, role)
        resp = AddUserResponseDto()
        resp.user_id = user_id
        return resp
