package di

case class User(username: String, password: String)

trait UserRepositoryComponent {
  val userRepository: UserRepository

  class UserRepository {
    def authenticate(user: User): User = {
      println("authenticating user: " + user)
      user
    }

    def create(user: User) { println("creating user: " + user) }

    def delete(user: User) { println("deleting user: " + user) }
  }
}

trait UserServiceComponent { this: UserRepositoryComponent =>
  val userService: UserService

  class UserService {
    def authenticate(username: String, password: String): User =
      userRepository.authenticate(new User(username, password))

    def create(username: String, password: String) {
      userRepository.create(new User(username, password))
    }

    def delete(user: User) { userRepository.delete(user) }
  }
}

object ComponentRegistry extends UserServiceComponent with UserRepositoryComponent {
  override val userRepository = new UserRepository

  override val userService = new UserService
}

object Main extends App {
  val userService = ComponentRegistry.userService
  userService.authenticate("aaa", "bbb")
}

trait TestingEnvironment extends UserServiceComponent with UserRepositoryComponent {
  override val userRepository = new UserRepository {
    override def authenticate(user: User) = user
  }

  override val userService = new UserService
}
