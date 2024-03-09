from dto.add_answer_request_dto import AddAnswerRequestDto
from dto.add_answer_response_dto import AddAnswerResponseDto
from services.answer import AnswerService


class AnswerController:

    def __init__(self) -> None:
        self._answer_repo = AnswerService()

    def add_answer(self, request_dto: AddAnswerRequestDto) -> AddAnswerResponseDto:
        title: str = request_dto.title
        description: str = request_dto.description
        answered_by_id: str = request_dto.answered_by
        question_id: str = request_dto.question_id
        answer_id: str = self._answer_repo.add_answer(
            question_id, title, description, answered_by_id
        )
        resp: AddAnswerResponseDto = AddAnswerResponseDto()
        resp.answer_id = answer_id
        return resp
