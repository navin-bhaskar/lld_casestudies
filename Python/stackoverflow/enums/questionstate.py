from enum import Enum, StrEnum


class QuestionState(StrEnum, Enum):
    open = "Open"
    closed = "Closed"
    onhold = "On hold"
    deleted = "Deleted"
