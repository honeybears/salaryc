import com.salaryc.user.user.entity.User
import com.salaryc.user.user.entity.UserRole
import com.salaryc.user.user.repository.UserRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class DataLoader(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) : CommandLineRunner {

    override fun run(vararg args: String?) {
        val user = User(
            email = "Test",
            password = "Test",
            role = UserRole.ADMIN_ACCOUNT
        )
        userRepository.save(user)
    }
}