package com.tvm.internal.tvm_internal_project.controller;

import com.tvm.internal.tvm_internal_project.model.Goal;
import com.tvm.internal.tvm_internal_project.response.ResponseStructure;
import com.tvm.internal.tvm_internal_project.serviceImpl.GoalServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/goals")
public class GoalsController {

    @Autowired
    private GoalServiceImpl goalService;

    @GetMapping("/all")
    public ResponseEntity<ResponseStructure<List<Goal>>> getAllGoals() {
        return goalService.getAllGoals();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ResponseStructure<List<Goal>>> getGoalsByUserId(@PathVariable Long userId) {
        return goalService.getGoalsByUserId(userId);
    }

    @GetMapping
    public ResponseEntity<ResponseStructure<List<Goal>>> getGoalById( @AuthenticationPrincipal UserDetails userDetails) {
        return goalService.getGoalById(userDetails);
    }

    @PostMapping
    public ResponseEntity<ResponseStructure<Goal>> createGoal(@RequestBody Goal goal,@AuthenticationPrincipal UserDetails userDetails) {
        return goalService.createGoal(goal,userDetails);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseStructure<Goal>> updateGoal(@PathVariable Long id, @RequestBody Goal goal,@AuthenticationPrincipal UserDetails userDetails) {
        return goalService.updateGoal(id, goal,userDetails);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseStructure<String>> deleteGoal(@PathVariable Long id,@AuthenticationPrincipal UserDetails userDetails) {
        return goalService.deleteGoal(id,userDetails);
    }
}