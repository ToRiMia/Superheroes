package torimia.superheroes.arena;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import torimia.superheroes.MessageDto;
import torimia.superheroes.arena.model.dto.ReceivingBattleDtoFromUser;
import torimia.superheroes.arena.service.BattleService;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("arena")
@RequiredArgsConstructor
public class BattleController {

    private final BattleService service;

    @PostMapping("battle")
    public MessageDto battle(@Valid @RequestBody ReceivingBattleDtoFromUser dto) {
        return service.battleStart(dto);
    }
}

