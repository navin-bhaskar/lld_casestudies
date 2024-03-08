class AddAnswerResponseDto:
    def __init__(self) -> None:
        self._answer_id = None

    @property
    def answer_id(self):
        return self._answer_id

    @answer_id.setter
    def answer_id(self, val):
        self._answer_id = val
