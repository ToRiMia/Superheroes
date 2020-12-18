package torimia.superheroes.arena;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import torimia.superheroes.arena.model.dto.ArenaBattleDto;
import torimia.superheroes.arena.service.ArenaService;
import torimia.superheroes.superhero.model.dto.IdRequest;

import javax.validation.Valid;

@RestController
@RequestMapping("arena")
@RequiredArgsConstructor
public class ArenaController {

    private final ArenaService service;

    @PostMapping("battle/{id}")
    public ArenaBattleDto battle(@PathVariable("id") Long superheroId, @Valid @RequestBody IdRequest id) {
        return service.battle(superheroId, id);
    }
}
