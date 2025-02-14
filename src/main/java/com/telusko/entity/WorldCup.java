package com.telusko.entity;

import java.util.List;

public record WorldCup(int year, String teamWhoWonInTheFinal, List<String> playersOfTheWinningTeam, String hostNation) {
}
