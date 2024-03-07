from enum import Enum, StrEnum


class AccountState(StrEnum, Enum):
    Active = "Active"
    Closed = "Closed"
    Deleted = "Deleted"
    Banned = "Banned"
