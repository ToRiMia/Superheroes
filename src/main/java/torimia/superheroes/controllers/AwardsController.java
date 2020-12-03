package torimia.superheroes.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.*;
import torimia.superheroes.model.dto.AwardDto;
import torimia.superheroes.services.AwardService;

import javax.validation.Valid;

@RestController
@RequestMapping("award")
@RequiredArgsConstructor
public class AwardsController {

    private final AwardService service;

    @GetMapping
    public Page<AwardDto> getAllPage(@SortDefault(sort = "id") Pageable page) {
        return service.getPage(page);
    }

    @PostMapping
    public AwardDto create(@Valid @RequestBody AwardDto dto) {
        return service.create(dto);
    }

    @PutMapping("{id}")
    public AwardDto update(
            @PathVariable("id") Long id,
            @Valid @RequestBody AwardDto dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("{id}")
    public void remove(@PathVariable("id") Long awardId) {
        service.delete(awardId);
    }
}
