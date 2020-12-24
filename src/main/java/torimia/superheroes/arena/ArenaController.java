package torimia.superheroes.arena;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import torimia.superheroes.MessageDto;
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

    @PostMapping("battle/result")
    public void battleResult(@Valid @RequestBody ArenaBattleDto dto, @RequestHeader("token") String token) throws AccessException {
        log.info("Result");
        if (!token.equals("Arena response"))
            throw new AccessException("Wrong token, access denied");
        else
            service.saveBattleResult(dto);
    }

    @PostMapping("battle/result_error")
    public void battleResultError(@Valid @RequestBody MessageDto message,
                                        @RequestHeader("token") String token) throws AccessException {// TODO: 24.12.20 це опять не гуд, треба створити свою помилку і кидати певний статус без ексепшн хендлера
        if (!token.equals("Arena response"))
            throw new AccessException("Wrong token, access denied");// TODO: 24.12.20  АОР, токен туди
        else {
            log.error(message.getMessage());
        }
    }
}
