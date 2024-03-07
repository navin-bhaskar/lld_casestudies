from enum import Enum, StrEnum


class ClosingRemark(StrEnum, Enum):
    Duplicate = "Duplicate question"
    OffTopic = "Off Topic"
    TooBroad = "Too Broad"
    NotConstructive = "Not Constructive"
