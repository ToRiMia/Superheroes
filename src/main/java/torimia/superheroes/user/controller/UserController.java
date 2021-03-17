package torimia.superheroes.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import torimia.superheroes.user.model.UserDto;
import torimia.superheroes.user.service.UserService;

import javax.validation.Valid;

import static torimia.superheroes.user.controller.UserController.Path.BY_ID;
import static torimia.superheroes.user.controller.UserController.Path.REGISTER;
import static torimia.superheroes.user.controller.UserController.Path.Variable.ID;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    interface Path {
        String REGISTER = "/register";

        String BY_ID = "/{" + ID + "}";

        interface Variable {
            String ID = "id";
        }
    }

    @PostMapping
    public UserDto create(@Valid @RequestBody UserDto dto) {
        return service.create(dto);
    }

    @GetMapping(BY_ID)
    public UserDto getById(@PathVariable(ID) Long id) {
        return service.getById(id);
    }


    @PutMapping(BY_ID)
    public UserDto update(
            @PathVariable(ID) Long id,
            @Valid @RequestBody UserDto dto) {
        return service.update(id, dto);
    }

    @DeleteMapping(BY_ID)
    public void remove(@PathVariable(ID) Long id) {
        service.delete(id);
    }
}
