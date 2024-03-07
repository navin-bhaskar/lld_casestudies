from abc import ABC, abstractmethod


class Command(ABC):

    def __init__(self, name) -> None:
        super().__init__()
        self._name = name

    @abstractmethod
    def execute():
        pass

    def get_name(self):
        return self._name

    @abstractmethod
    def get_help(self):
        pass
