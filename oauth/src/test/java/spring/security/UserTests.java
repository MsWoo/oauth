package spring.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.oauth.entity.User;
import com.example.oauth.entity.UserRole;
import com.example.oauth.repository.UserRepository;

@SpringBootTest
public class UserTests {

	@Autowired
	private UserRepository repository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Test
	public void insertDummies() {

//		User user = User.builder().email("1234" + "@naver.com").username("우민수").fromSocial(false)
//				.password(passwordEncoder.encode("1234")).build();
//
//		user.addUserRole(UserRole.USER);
//		user.addUserRole(UserRole.ADMIN);
//
//		repository.saveUser(user);

	}

//    @Test
//    public void testRead() {
//
//        Optional<ClubMember> result = repository.findByEmail("user95@zerock.org", false);
//
//        ClubMember clubMember = result.get();
//
//        System.out.println(clubMember);
//
//    }

}
