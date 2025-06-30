package com.tvm.internal.tvm_internal_project.service;

import com.tvm.internal.tvm_internal_project.model.Goal;
import com.tvm.internal.tvm_internal_project.response.ResponseStructure;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

public interface GoalSevice {


//    List<Goal> getAllGoals();

    ResponseEntity<ResponseStructure<List<Goal>>> getGoalById( UserDetails userDetails);

    ResponseEntity<ResponseStructure<Goal>> createGoal(Goal goal,UserDetails userDetails);

    ResponseEntity<ResponseStructure<Goal>> updateGoal(Long id, Goal updatedGoal,UserDetails userDetails);

    ResponseEntity<ResponseStructure<String>> deleteGoal(Long id,UserDetails userDetails);

}