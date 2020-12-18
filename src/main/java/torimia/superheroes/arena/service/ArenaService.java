package torimia.superheroes.arena.service;

import torimia.superheroes.arena.model.dto.ArenaBattleDto;
import torimia.superheroes.superhero.model.dto.IdRequest;

public interface ArenaService {

    ArenaBattleDto battle(Long superheroId, IdRequest id);
}
