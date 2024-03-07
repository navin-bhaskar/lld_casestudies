package com.splitmoney.splitmoney.repositories;


import com.splitmoney.splitmoney.models.Expense;
import com.splitmoney.splitmoney.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ExpenseRepository extends CrudRepository<Expense, Long> {
    List<Expense> findAllByUserExpensesUser(User user);

}
