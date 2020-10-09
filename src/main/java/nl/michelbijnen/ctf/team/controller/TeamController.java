package nl.michelbijnen.ctf.team.controller;

import nl.michelbijnen.ctf.team.dtos.TeamDto;
import nl.michelbijnen.ctf.team.model.Challenge;
import nl.michelbijnen.ctf.team.model.Team;
import nl.michelbijnen.ctf.team.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TeamController {

    @Autowired
    private TeamRepository teamRepository;

    @GetMapping("/")
    public ResponseEntity<List<TeamDto>> getScoreboard() {
        List<Team> teams = this.teamRepository.findAll();
        List<TeamDto> teamDtos = new ArrayList<>();
        for (Team team : teams) {
            TeamDto teamDto = new TeamDto();
            teamDto.setName(team.getName());
            teamDto.setPoints(0);
            for (Challenge challenge : team.getSolvedChallenges()) {
                teamDto.setPoints(teamDto.getPoints() + challenge.getPoints());
            }
            teamDtos.add(teamDto);
        }

        return ResponseEntity.ok(teamDtos);
    }
}
