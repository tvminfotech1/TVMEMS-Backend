package com.tvm.internal.tvm_internal_project.serviceImpl;

import com.tvm.internal.tvm_internal_project.exception.NoTaskFoundException;
import com.tvm.internal.tvm_internal_project.model.Goal;
import com.tvm.internal.tvm_internal_project.model.User;
import com.tvm.internal.tvm_internal_project.repo.GoalRepo;
import com.tvm.internal.tvm_internal_project.repo.UserRepo;
import com.tvm.internal.tvm_internal_project.response.ResponseStructure;
import com.tvm.internal.tvm_internal_project.service.GoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GoalServiceImpl implements GoalService {
    @Autowired
    private GoalRepo goalRepo;

    @Autowired
    private UserRepo userRepo;

    @Override
    public ResponseEntity<ResponseStructure<List<Goal>>> getAllGoals(UserDetails userDetails) {
        String email = userDetails.getUsername();
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        List<Goal> goals = goalRepo.findByUser(user);
        if (user.getRoles().equals("ROLE_ADMIN")) {
            goals = goalRepo.findAll();
        }
        else {
            goals = goalRepo.findByUser(user);
        }
        if (goals.isEmpty()) {
            throw new NoTaskFoundException("No goals found");
        }
        ResponseStructure<List<Goal>> response = new ResponseStructure<>();
        response.setBody(goals);
        response.setMessage("Goals fetched successfully");
        response.setStatusCode(HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<ResponseStructure<List<Goal>>> getGoalsByUserId(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with ID: " + userId));
        List<Goal> goals = goalRepo.findByUser(user);
        if (goals.isEmpty()) {
            throw new NoTaskFoundException("No Goals Found for this User ID");
        }
        ResponseStructure<List<Goal>> response = new ResponseStructure<>();
        response.setBody(goals);
        response.setMessage("Goals fetched successfully for User ID: " + userId);
        response.setStatusCode(HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<ResponseStructure<List<Goal>>> getGoalById(UserDetails userDetails) {
        String email = userDetails.getUsername();
        User user = userRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        List<Goal> goals = goalRepo.findByUser(user);
        if (goals.isEmpty()) {
            throw new NoTaskFoundException("Goal Id Not Found");
        }
        ResponseStructure<List<Goal>> response = new ResponseStructure<>();
        response.setBody(goals);
        response.setMessage("Goal fetched Successfully");
        response.setStatusCode(HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseStructure<Goal>> createGoal(Goal goal, UserDetails userDetails) {
        String email = userDetails.getUsername();
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        goal.setUser(user);
        goal.setStatus("Pending");
        goal.setStartDate(null);
        goal.setDueDate(null);
        goal.setEndDate(null);
        Goal created = goalRepo.save(goal);
        ResponseStructure<Goal> response = new ResponseStructure<>();
        response.setBody(created);
        response.setMessage("Goal saved successfully");
        response.setStatusCode(HttpStatus.CREATED.value());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ResponseStructure<Goal>> updateGoal(Long id, Goal updatedGoal, UserDetails userDetails) {
        String email = userDetails.getUsername();
        User user = userRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Goal goal = goalRepo.findById(id).filter(g -> g.getUser().getEmployeeId().equals(user.getEmployeeId())).orElseThrow(() -> new NoTaskFoundException("Goal Id not found: " + id));
        if (!goal.getUser().getEmployeeId().equals(user.getEmployeeId())) {
            throw new NoTaskFoundException("Goal does not belong to the logged-in user");
        }
        goal.setCategory(updatedGoal.getCategory());
        goal.setDescription(updatedGoal.getDescription());
        goal.setStartDate(updatedGoal.getStartDate());
        goal.setDueDate(updatedGoal.getDueDate());
        goal.setEndDate(updatedGoal.getEndDate());
        goal.setStatus(updatedGoal.getStatus());
        goal.setProgress(updatedGoal.getProgress());
        goal.setWeight(updatedGoal.getWeight());
        Goal updated = goalRepo.save(goal);
        ResponseStructure<Goal> response = new ResponseStructure<>();
        response.setBody(updated);
        response.setMessage("Goal updated Successfully");
        response.setStatusCode(HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseStructure<String>> deleteGoal(Long id, UserDetails userDetails) {
        String email = userDetails.getUsername();
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Goal goal = goalRepo.findById(id)
                .filter(g -> g.getUser().getEmployeeId().equals(user.getEmployeeId()))
                .orElseThrow(() -> new NoTaskFoundException("Goal Id not found: " + id));
        goalRepo.delete(goal);
        ResponseStructure<String> response = new ResponseStructure<>();
        response.setBody("Goal deleted successfully");
        response.setMessage("Success");
        response.setStatusCode(HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}