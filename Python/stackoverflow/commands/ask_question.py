from commands.command import Command
from controllers.question_controller import QuestionController
from dto.add_question_dto import AddQuestionDto
from dto.add_question_response_dto import AddQuestionResponseDto


class PostQuestion(Command):
    def __init__(self) -> None:
        super().__init__("ask_as")
        self._q_service = QuestionController()

    def get_help(self):
        return """Ask a question with system generated user id"""

    def execute(self):
        title = input("Enter title of the question. ")
        description = input("Describe your question: ")
        asked_by = input("Who is asking this question (eg U_1, U_2 ...)? ")

        req: AddQuestionDto = AddQuestionDto()
        req.asked_by = asked_by
        req.title = title
        req.description = description

        question: AddQuestionResponseDto = self._q_service.ask(req)
        print("Created question with id " + question.question_id)
