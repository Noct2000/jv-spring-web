package mate.academy.spring.controller;

import mate.academy.spring.dto.UserResponseDto;
import mate.academy.spring.model.User;
import mate.academy.spring.service.UserService;
import mate.academy.spring.service.mapper.UserMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService,
                          UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping("/inject")
    public String inject() {
        User bohdan = new User();
        User oleh = new User();
        User roman = new User();
        bohdan.setName("Bohdan");
        bohdan.setLastName("Chupika");
        roman.setName("Roman");
        roman.setLastName("Dubovskyi");
        oleh.setName("Oleh");
        oleh.setLastName("Kvasha");
        Stream.of(bohdan, oleh, roman).forEach(userService::add);
        return "DONE!!!";
    }

    @GetMapping("/{userId}")
    public UserResponseDto get(@PathVariable Long userId) {
        return userMapper.toResponseDto(userService.get(userId));
    }

    @GetMapping("/")
    public List<UserResponseDto> getAll() {

        return userService.getAll().stream()
                .map(userMapper::toResponseDto)
                .collect(Collectors.toList());
    }

}