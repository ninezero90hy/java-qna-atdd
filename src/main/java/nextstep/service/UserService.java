package nextstep.service;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import nextstep.UnAuthenticationException;
import nextstep.UnAuthorizedException;
import nextstep.domain.User;
import nextstep.domain.UserRepository;
import nextstep.security.HttpSessionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userService")
public class UserService {

  @Resource(name = "userRepository")
  private UserRepository userRepository;

  public User add(User user) {
    return userRepository.save(user);
  }

  @Transactional
  public User update(User loginUser, long id, User updatedUser) {
    User original = findById(loginUser, id);
    original.update(loginUser, updatedUser);
    return original;
  }

  public User findById(User loginUser, long id) {
    return userRepository.findById(id)
        .filter(user -> user.equals(loginUser))
        .orElseThrow(UnAuthorizedException::new);
  }

  public List<User> findAll() {
    return userRepository.findAll();
  }

  public User login(String userId, String password) throws UnAuthenticationException {
    return userRepository.findByUserId(userId)
        .filter(user -> user.getPassword().equals(password))
        .orElseThrow(UnAuthenticationException::new);
  }

  public void createSession(final User user, final HttpSession httpSession) {
    httpSession.setAttribute(HttpSessionUtils.USER_SESSION_KEY, user);
  }

}
