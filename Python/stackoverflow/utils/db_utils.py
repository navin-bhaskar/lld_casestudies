from appconfig.appconfig import AppConfig
from sqlalchemy.orm import sessionmaker
from models.answer import Answer

from models.base import Base
from models.question import Question
from models.member import Member

Session = sessionmaker(bind=AppConfig().get_engine(), expire_on_commit=False)


def get_session():
    return Session()


def init_models():
    engine = AppConfig().get_engine()
    Member.register()
    Question.register()
    Answer.register()
    Base.metadata.create_all(engine)


# Context manager for handling sessions
class SessionManager:
    def __enter__(self):
        self.session = get_session()
        return self.session

    def __exit__(self, exc_type, exc_val, exc_tb):
        pass
