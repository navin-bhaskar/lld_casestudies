from sqlalchemy import create_engine


class Singleton(type):
    _instances = {}

    def __call__(cls, *args, **kwargs):
        if cls not in cls._instances:
            cls._instances[cls] = super(Singleton, cls).__call__(*args, **kwargs)
        return cls._instances[cls]


class AppConfig:

    __metaclass__ = Singleton

    def __init__(self) -> None:
        self._engine = create_engine("mysql+mysqlconnector://root@localhost:3306/so")

    def get_engine(self):
        return self._engine
