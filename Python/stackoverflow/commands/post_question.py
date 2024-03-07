from commands.command import Command
from services.question import QuestionService


class PostQuestion(Command):
    def __init__(self) -> None:
        super().__init__("ask_as")
        self._q_service = QuestionService()

    def get_help(self):
        return """Ask a question with system generated user id"""

    def execute(self):
        title = input("Enter title of the question. ")
        description = input("Describe your question: ")
        asked_by = input("Who is asking this question (eg U_1, U_2 ...)? ")

        question = self._q_service.ask(title, description, asked_by)
        print("Created question with id " + question)
