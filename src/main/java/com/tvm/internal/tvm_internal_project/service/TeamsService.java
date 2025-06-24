package com.tvm.internal.tvm_internal_project.service;

import com.tvm.internal.tvm_internal_project.model.Teams;

import java.util.List;
import java.util.Optional;

public interface TeamsService {

    List<Teams> getAllTeams();

    Optional<Teams> getTeamById(Long teamId);

    Teams saveTeam(Teams team);

    Teams updateTeam(Long id, Teams teamDetails);

    void deleteTeam(Long teamId);
}