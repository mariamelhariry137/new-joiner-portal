package com.newjoinerportal.content.service;

import com.newjoinerportal.content.exception.DataIntegrityException;
import com.newjoinerportal.content.exception.DuplicateResource;
import com.newjoinerportal.content.exception.ResourceNotFound;
import com.newjoinerportal.content.model.Team;
import com.newjoinerportal.content.repo.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    public Team getTeamById(Long id) {
        return teamRepository.findById(id).orElseThrow(()->new ResourceNotFound("Team",id));
    }

    public Team createTeam(Team team) {
        if(teamRepository.findByName(team.getName()).isPresent()){
            throw new DuplicateResource("Team with name" + team.getName()+"already exists");
        }
        try{
            return teamRepository.save(team);
        }catch(Exception e){
            throw new DataIntegrityException("failed to create team" + e.getMessage());
        }
    }

    @Transactional
    public Team updateTeam(Long id, Team teamDetails) {
        Team team = getTeamById(id);
        if(teamRepository.findByName(teamDetails.getName()).isPresent() && !team.getName().equals(teamDetails.getName())){
            throw new DuplicateResource(("Team with name"+teamDetails.getName()+"already exists"));
        }
        team.setName(teamDetails.getName());
        team.setDescription(teamDetails.getDescription());
        try{
            return teamRepository.save(team);
        } catch (Exception e) {
            throw new DataIntegrityException("Failed to update team : "+ e.getMessage());
        }
    }

    public void deleteTeam(Long id) {
        Team team = getTeamById(id);
        try{
            teamRepository.delete(team);
        }catch(Exception e){
            throw new DataIntegrityException("Cannot delete team as it is associated with contacts");
        }
        teamRepository.deleteById(id);
    }
}
