from commands.command import Command
from controllers.answer_controller import AnswerController
from dto.add_answer_request_dto import AddAnswerRequestDto
from dto.add_answer_response_dto import AddAnswerResponseDto


class AddAnswer(Command):
    def __init__(self) -> None:
        super().__init__("add_answer")
        self._answer_controller = AnswerController()

    def execute(self):
        question_id = input("Enter question ID(eg: Q_1, Q_2....): ")
        title = input("Enter answer title: ")
        description = input("Enter answer: ")
        answerd_by = input("Who is answering this question (eg: U_1, U_2....): ")

        request = AddAnswerRequestDto()
        request.question_id = question_id
        request.title = title
        request.description = description
        request.answered_by = answerd_by

        response: AddAnswerResponseDto = self._answer_controller.add_answer(request)
        print("Created answer with ID " + response.answer_id)

    def get_help(self):
        return """Adds an answer to a question """
