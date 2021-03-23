package torimia.superheroes.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import torimia.superheroes.user.model.User;
import torimia.superheroes.user.model.UserDtoRequest;
import torimia.superheroes.user.model.UserDtoResponse;
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

    @PostMapping(REGISTER)
    public UserDtoResponse create(@Valid @RequestBody UserDtoRequest dto) {
        return service.create(dto);
    }

    @GetMapping(BY_ID)
    public UserDtoResponse getById(@PathVariable(ID) String id, @CheckUser User user) {
        return service.getById(id);
    }


    @PutMapping(BY_ID)
    public UserDtoResponse update(
            @PathVariable(ID) String id,
            @Valid @RequestBody UserDtoRequest dto) {
        return service.update(id, dto);
    }

    @DeleteMapping(BY_ID)
    public void remove(@PathVariable(ID) String id) {
        service.delete(id);
    }
}
