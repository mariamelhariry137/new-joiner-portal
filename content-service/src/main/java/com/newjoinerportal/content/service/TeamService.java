package com.newjoinerportal.content.service;

import com.newjoinerportal.content.model.Team;
import com.newjoinerportal.content.repo.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    public Team getTeamById(Long id) {
        return teamRepository.findById(id).orElse(null);
    }

    public Team createTeam(Team team) {
        return teamRepository.save(team);
    }

    public Team updateTeam(Long id, Team teamDetails) {
        Team team = getTeamById(id);
        if (team != null) {
            team.setName(teamDetails.getName());
            team.setDescription(teamDetails.getDescription());
            return teamRepository.save(team);
        }
        return null;
    }

    public void deleteTeam(Long id) {
        teamRepository.deleteById(id);
    }
}