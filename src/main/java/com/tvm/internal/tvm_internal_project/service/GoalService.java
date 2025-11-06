package com.tvm.internal.tvm_internal_project.service;



import com.tvm.internal.tvm_internal_project.model.Goal;
import com.tvm.internal.tvm_internal_project.response.ResponseStructure;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface GoalService {

    // Fetch all goals of a user
    ResponseEntity<ResponseStructure<List<Goal>>> getGoalById(UserDetails userDetails);

    // Create a new goal
    ResponseEntity<ResponseStructure<Goal>> createGoal(Goal goal, UserDetails userDetails);

    // Update an existing goal
    ResponseEntity<ResponseStructure<Goal>> updateGoal(Long id, Goal updatedGoal, UserDetails userDetails);

    // Delete a goal
    ResponseEntity<ResponseStructure<String>> deleteGoal(Long id, UserDetails userDetails);

    ResponseEntity<ResponseStructure<List<Goal>>> getArchivedGoals(UserDetails userDetails);

    ResponseEntity<ResponseStructure<List<Goal>>> getAllGoals(UserDetails userDetails);
}
