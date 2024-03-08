# Introduction

A simple Stackoverflow application built for the sake of practicing low level design skills.
Uses sqlalchemy for ORM install it using `pip install sqlalchemy`

# Requirements

1. System should allow for regesteration of user
2. Users must be able to ask questions
3. Any user must be able to answer any of the open questions
4. There must be provision to create user with following roles
   - Memeber: Can answer any question and ask a question
   - Moderator: Can close any question in addition to memeber operations
   - Admin: Can ban any user in addition to moderator operations
