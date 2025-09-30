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


    public ResponseEntity<ResponseStructure<List<Goal>>> getGoalById(UserDetails userDetails) {
        String email = userDetails.getUsername();
        User user = userRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        List<Goal> goals = goalRepo.findByUser(user);
        if (goals.isEmpty()) {
            throw new NoTaskFoundException("Goal Id Not Found");

        }


        ResponseStructure<List<Goal>> response = new ResponseStructure<>();
        response.setBody(goals);
        response.setMessage("Goal Saved Successfully");
        response.setStatusCode(HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseStructure<Goal>> createGoal(Goal goal, UserDetails userDetails) {
        String email = userDetails.getUsername();
        User user = userRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("user not found"));
        goal.setUser(user);
        ;
        Goal created = goalRepo.save(goal);
        ResponseStructure<Goal> response = new ResponseStructure<>();
        response.setBody(created);
        response.setMessage("Goal Saved Successfully");
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
//        goal.setPriority(updatedGoal.getPriority());
        goal.setStartDate(updatedGoal.getStartDate());
        goal.setEndDate(updatedGoal.getEndDate());
        goal.setMetrics(updatedGoal.getMetrics());
        goal.setOutcome(updatedGoal.getOutcome());
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
        User user = userRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        List<Goal> goals = goalRepo.findByUser(user);
        if (goals.isEmpty()) {
            throw new NoTaskFoundException("Goal Id not found:" + id);
        }
        goalRepo.delete(goals.get(0));
        ResponseStructure<String> response = new ResponseStructure<>();
        response.setBody("Goal deleted Successfully");
        response.setMessage("Success");
        response.setStatusCode(HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);

    }
}