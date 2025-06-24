package com.tvm.internal.tvm_internal_project.service;

import com.tvm.internal.tvm_internal_project.model.Goal;

import java.util.List;
import java.util.Optional;

public interface GoalSevice {

    List<Goal> getAllGoals();

    Optional<Goal> getGoalById(Long id);

    Goal createGoal(Goal goal);

    Goal updateGoal(Long id, Goal updatedGoal);

    void deleteGoal(Long id);
}