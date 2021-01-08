package torimia.superheroes.arena;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import torimia.superheroes.MessageDto;
import torimia.superheroes.arena.annotations.TokenCheckable;
import torimia.superheroes.arena.model.dto.ArenaBattleDto;
import torimia.superheroes.arena.model.dto.BattleDto;
import torimia.superheroes.arena.service.ArenaService;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("arena")
@RequiredArgsConstructor
public class ArenaController {

    private final ArenaService service;

    @PostMapping("battle")
    public MessageDto battle(@Valid @RequestBody BattleDto dto) {
        return service.battle(dto);
    }
}

