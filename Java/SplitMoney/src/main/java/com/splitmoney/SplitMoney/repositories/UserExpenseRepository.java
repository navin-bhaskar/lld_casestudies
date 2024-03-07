package com.splitmoney.splitmoney.repositories;

import com.splitmoney.splitmoney.models.UserExpense;
import org.springframework.data.repository.CrudRepository;

public interface UserExpenseRepository extends CrudRepository<UserExpense, Long> {
}
