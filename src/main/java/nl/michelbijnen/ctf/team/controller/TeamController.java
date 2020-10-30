package nl.michelbijnen.ctf.team.controller;

import nl.michelbijnen.ctf.team.dtos.TeamDto;
import nl.michelbijnen.ctf.team.model.Challenge;
import nl.michelbijnen.ctf.team.model.Team;
import nl.michelbijnen.ctf.team.model.User;
import nl.michelbijnen.ctf.team.repositories.TeamRepository;
import nl.michelbijnen.ctf.team.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class TeamController {

    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public ResponseEntity<List<TeamDto>> getScoreboard(@RequestHeader String requestingUserId) {
        List<Team> teams = this.teamRepository.findAll();

        Optional<User> optionalUser = this.userRepository.findById(requestingUserId);
        String userTeamId = "";
        if (optionalUser.isPresent()) {
            userTeamId = optionalUser.get().getTeam().getId();
        }

        List<TeamDto> teamDtos = new ArrayList<>();
        for (Team team : teams) {
            TeamDto teamDto = new TeamDto();
            teamDto.setName(team.getName());
            teamDto.setPoints(0);
            teamDto.setSelf(userTeamId.equals(team.getId()));
            for (Challenge challenge : team.getSolvedChallenges()) {
                teamDto.setPoints(teamDto.getPoints() + challenge.getPoints());
            }
            teamDtos.add(teamDto);
        }

        return ResponseEntity.ok(teamDtos);
    }
}
