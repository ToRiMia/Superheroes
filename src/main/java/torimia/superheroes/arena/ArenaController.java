package torimia.superheroes.arena;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import torimia.superheroes.MessageDto;
import torimia.superheroes.arena.annotations.TokenCheckable;
import torimia.superheroes.arena.model.dto.ArenaBattleDto;
import torimia.superheroes.arena.model.dto.BattleDto;
import torimia.superheroes.arena.service.ArenaService;

import javax.validation.Valid;
import java.rmi.AccessException;

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

    @TokenCheckable
    @PostMapping("battle/result")
    public void battleResult(@Valid @RequestBody ArenaBattleDto dto,
                             @RequestHeader("token") String token){
        log.info("Result");
        service.saveBattleResult(dto);
    }

    @TokenCheckable
    @PostMapping("battle/result_error")
    public void battleResultError(@Valid @RequestBody MessageDto message,
                                  @RequestHeader("token") String token) {
        log.error(message.getMessage());
    }
}

