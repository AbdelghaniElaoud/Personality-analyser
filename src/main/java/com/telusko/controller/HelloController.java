package com.telusko.controller;

import com.telusko.entity.Personality;
import com.telusko.entity.UserResponse;
import com.telusko.entity.WorldCup;
import com.telusko.services.PromptMaster;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class HelloController {
    private PromptMaster promptMaster;

    public HelloController(PromptMaster promptMaster) {
        this.promptMaster = promptMaster;
    }

    @GetMapping(value = "/ask/{year}"   )
    public WorldCup getResponse(@PathVariable String year){
        return promptMaster.theWinningTeamOfAWorldCupTournament(year);
    }

    @PostMapping(value = "/person"   )
    public Personality getResponse(@RequestBody List<UserResponse> userResponses){
        return promptMaster.personalityAnalysis(userResponses);
    }

}
