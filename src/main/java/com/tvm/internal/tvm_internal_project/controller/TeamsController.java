package com.tvm.internal.tvm_internal_project.controller;

import com.tvm.internal.tvm_internal_project.model.Teams;
import com.tvm.internal.tvm_internal_project.service.TeamsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TeamsController {

    @Autowired
    private TeamsService teamService;

    @GetMapping("/teams")
    public List<Teams> getAllTeams() {
        return teamService.getAllTeams();
    }

    @GetMapping("/teams/{teamId}")
    public ResponseEntity<Teams> getTeamById(@PathVariable Long teamId) {
        return teamService.getTeamById(teamId).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/teams")
    public Teams createTeam(@RequestBody Teams team) {
        return teamService.saveTeam(team);
    }

    @PutMapping("/teams/{id}")
    public ResponseEntity<Teams> updateTeam(@PathVariable Long id, @RequestBody Teams teamDetails) {
        return ResponseEntity.ok(teamService.updateTeam(id, teamDetails));
    }


    @DeleteMapping("/teams/{teamId}")
    public ResponseEntity<Void> deleteTeam(@PathVariable Long teamId) {
        teamService.deleteTeam(teamId);
        return ResponseEntity.noContent().build();
    }
}