package nextstep.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Size;
import nextstep.UnAuthorizedException;
import support.domain.AbstractEntity;

@Entity
public class User extends AbstractEntity {

  public static final GuestUser GUEST_USER = new GuestUser();

  @Size(min = 3, max = 20)
  @Column(unique = true, nullable = false)
  private String userId;

  @Size(min = 3, max = 20)
  @Column(nullable = false)
  private String password;

  @Size(min = 3, max = 20)
  @Column(nullable = false)
  private String name;

  @Size(max = 50)
  private String email;

  public User() {
  }

  public User(String userId, String password, String name, String email) {
    this(0L, userId, password, name, email);
  }

  public User(long id, String userId, String password, String name, String email) {
    super(id);
    this.userId = userId;
    this.password = password;
    this.name = name;
    this.email = email;
  }

  public String getUserId() {
    return userId;
  }

  public User setUserId(String userId) {
    this.userId = userId;
    return this;
  }

  public String getPassword() {
    return password;
  }

  public User setPassword(String password) {
    this.password = password;
    return this;
  }

  public String getName() {
    return name;
  }

  public User setName(String name) {
    this.name = name;
    return this;
  }

  public String getEmail() {
    return email;
  }

  public User setEmail(String email) {
    this.email = email;
    return this;
  }

  public void update(User loginUser, User target) {
    if (!matchUserId(loginUser.getUserId())) {
      throw new UnAuthorizedException();
    }

    if (!matchPassword(target.getPassword())) {
      throw new UnAuthorizedException();
    }

    this.name = target.name;
    this.email = target.email;
  }

  private boolean matchUserId(String userId) {
    return this.userId.equals(userId);
  }

  public boolean matchPassword(String targetPassword) {
    return password.equals(targetPassword);
  }

  public boolean equalsNameAndEmail(User target) {
    if (Objects.isNull(target)) {
      return false;
    }

    return name.equals(target.name) &&
        email.equals(target.email);
  }

  @JsonIgnore
  public boolean isGuestUser() {
    return false;
  }

  private static class GuestUser extends User {

    @Override
    public boolean isGuestUser() {
      return true;
    }
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    final User user = (User) o;
    return Objects.equals(userId, user.userId) &&
        Objects.equals(password, user.password) &&
        Objects.equals(name, user.name) &&
        Objects.equals(email, user.email);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), userId, password, name, email);
  }

  @Override
  public String toString() {
    return "User [userId=" + userId + ", password=" + password + ", name=" + name + ", email="
        + email + "]";
  }
}
