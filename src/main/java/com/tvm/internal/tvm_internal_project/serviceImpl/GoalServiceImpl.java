package com.tvm.internal.tvm_internal_project.serviceImpl;

import com.tvm.internal.tvm_internal_project.model.Goal;
import com.tvm.internal.tvm_internal_project.repo.GoalRepo;
import com.tvm.internal.tvm_internal_project.service.GoalSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GoalServiceImpl implements GoalSevice {

    @Autowired
    private GoalRepo goalRepo;

    public List<Goal> getAllGoals() {
        return goalRepo.findAll();
    }

    public Optional<Goal> getGoalById(Long id) {
        return goalRepo.findById(id);
    }

    public Goal createGoal(Goal goal) {
        return goalRepo.save(goal);
    }

    public Goal updateGoal(Long id, Goal updatedGoal) {
        Optional<Goal> existingGoal = goalRepo.findById(id);
        if (existingGoal.isPresent()) {
            Goal goal = existingGoal.get();
            goal.setCategory(updatedGoal.getCategory());
            goal.setDescription(updatedGoal.getDescription());
            goal.setPriority(updatedGoal.getPriority());
            goal.setStartDate(updatedGoal.getStartDate());
            goal.setEndDate(updatedGoal.getEndDate());
            goal.setMetrics(updatedGoal.getMetrics());
            goal.setOutcome(updatedGoal.getOutcome());
            goal.setWeight(updatedGoal.getWeight());
            return goalRepo.save(goal);
        } else {
            throw new RuntimeException("Goal not found with id: " + id);
        }
    }

    public void deleteGoal(Long id) {
        goalRepo.deleteById(id);
    }
}