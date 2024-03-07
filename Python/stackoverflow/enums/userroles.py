from enum import Enum


class UserRoles(Enum):
    guest = 0
    member = 5
    moderator = 10
    admin = 100
