from typing import List
from dto.add_user_request_dto import AddUserRequestDto
from dto.add_user_response_dto import AddUserResponseDto
from exceptions.db_exceptions import UserNotFound
from services.user import UserService
from utils.helpers import validate_and_get_entity_id, get_entity_name_from_id


class UserContoller:

    def __init__(self) -> None:
        self._user_service = UserService()

    def add_user(self, request_dto: AddUserRequestDto) -> AddUserResponseDto:
        user_name = request_dto.user_name
        user_email = request_dto.user_email
        role = request_dto.user_role
        user = self._user_service.add_user(user_name, user_email, role)
        user_id = get_entity_name_from_id(user.user_id, "U")
        resp = AddUserResponseDto()
        resp.user_id = user_id
        return resp
