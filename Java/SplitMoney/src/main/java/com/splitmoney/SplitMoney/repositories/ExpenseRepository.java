package com.splitmoney.splitmoney.repositories;


import com.splitmoney.splitmoney.models.Expense;
import org.springframework.data.repository.CrudRepository;

public interface ExpenseRepository extends CrudRepository<Expense, Long> {

}
