class AddAnswerRequestDto:
    def __init__(self) -> None:
        self._title = None
        self._description = None
        self._answered_by_id = None
        self._question_id = None

    @property
    def title(self):
        return self._title

    @title.setter
    def title(self, val):
        self._title = val

    @property
    def description(self):
        return self._description

    @description.setter
    def description(self, val):
        self._description = val

    @property
    def answered_by(self):
        return self._answered_by_id

    @answered_by.setter
    def answered_by(self, val):
        self._answered_by_id = val

    @property
    def question_id(self):
        return self._question_id

    @question_id.setter
    def question_id(self, val):
        self._question_id = val
