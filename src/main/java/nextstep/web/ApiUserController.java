package nextstep.web;

import java.net.URI;
import javax.annotation.Resource;
import javax.validation.Valid;
import nextstep.domain.User;
import nextstep.security.LoginUser;
import nextstep.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class ApiUserController {

  @Resource(name = "userService")
  private UserService userService;

  @PostMapping("")
  public final ResponseEntity<Void> create(@Valid @RequestBody final User user) {

    final User savedUser = userService.add(user);

    final HttpHeaders headers = new HttpHeaders();
    headers.setLocation(URI.create("/api/users/" + savedUser.getId()));
    return new ResponseEntity<>(headers, HttpStatus.CREATED);
  }

  @GetMapping("{id}")
  public final User detail(@LoginUser final User loginUser,
      @PathVariable final long id) {
    return userService.findById(loginUser, id);
  }

  @PutMapping("{id}")
  public final User update(@LoginUser final User loginUser,
      @PathVariable final long id,
      @Valid @RequestBody final User updatedUser) {
    return userService.update(loginUser, id, updatedUser);
  }

}
