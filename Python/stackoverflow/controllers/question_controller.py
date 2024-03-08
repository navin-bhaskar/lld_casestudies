from dto.add_question_dto import AddQuestionDto
from dto.add_question_response_dto import AddQuestionResponseDto
from services.question import QuestionService


class QuestionController:

    def __init__(self) -> None:
        self._question_service = QuestionService()

    def ask(self, request_dto: AddQuestionDto) -> AddQuestionResponseDto:
        title: str = request_dto.title
        description = request_dto.description
        asked_by = request_dto.asked_by
        question_id: str = self._question_service.ask(title, description, asked_by)
        resp = AddQuestionResponseDto()
        resp.question_id = question_id
        return resp
