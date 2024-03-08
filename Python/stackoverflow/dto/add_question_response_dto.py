class AddQuestionResponseDto:
    def __init__(self) -> None:
        self._question_id = None

    @property
    def question_id(self):
        return self._question_id

    @question_id.setter
    def question_id(self, val):
        self._question_id = val
