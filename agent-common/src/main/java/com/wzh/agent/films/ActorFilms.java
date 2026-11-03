package com.wzh.agent.films;

import java.util.List;

public record ActorFilms(
        String actor,
        List<String> movies) {
}
