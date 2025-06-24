package com.tvm.internal.tvm_internal_project.serviceImpl;

import com.tvm.internal.tvm_internal_project.model.Teams;
import com.tvm.internal.tvm_internal_project.repo.TeamsRepo;
import com.tvm.internal.tvm_internal_project.service.TeamsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeamsServiceImpl implements TeamsService {

    @Autowired
    TeamsRepo teamsRepo;

    public List<Teams> getAllTeams() {
        return teamsRepo.findAll();
    }

    public Optional<Teams> getTeamById(Long teamId) {
        return teamsRepo.findById(teamId);
    }

    public Teams saveTeam(Teams team) {
        return teamsRepo.save(team);
    }

    @Override
    public Teams updateTeam(Long id, Teams teamDetails) {
        Optional<Teams> existingTeamOptional = teamsRepo.findById(id);

        if (existingTeamOptional.isPresent()) {
            Teams existingTeam = existingTeamOptional.get();

            existingTeam.setTeamName(teamDetails.getTeamName() != null ? teamDetails.getTeamName() : existingTeam.getTeamName());

            if (teamDetails.getEmployees() != null && !teamDetails.getEmployees().isEmpty()) {
                existingTeam.setEmployees(teamDetails.getEmployees());
            }

            if (teamDetails.getGoals() != null && !teamDetails.getGoals().isEmpty()) {
                existingTeam.setGoals(teamDetails.getGoals());
            }
            return teamsRepo.save(existingTeam);
        } else {
            throw new RuntimeException("Team not found with id " + id);
        }
    }


    public void deleteTeam(Long teamId) {
        teamsRepo.deleteById(teamId);
    }
}