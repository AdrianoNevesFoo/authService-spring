package br.com.lingua.liguadeouro;

import br.com.lingua.liguadeouro.auth.model.Role;
import br.com.lingua.liguadeouro.auth.model.User;
import br.com.lingua.liguadeouro.auth.service.RoleService;
import br.com.lingua.liguadeouro.auth.service.UserService;
import br.com.lingua.liguadeouro.enums.RoleNameEnum;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIn.isIn;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LiguaDeOuroApplicationTests {

	@Autowired(required=true)
	UserService userService;

	@Autowired
	RoleService roleService;

	private User userBruce;
	private User userDick;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Before
	public void setUp() {
		this.userBruce = new User();
		this.userBruce.setUsername("Bijay");
		this.userBruce.setName("Bruce Wayne");
		this.userBruce.setPassword("1234");
		this.userBruce.setEmail("brucewayne@gmail.com");


		this.userDick= new User();
		this.userDick.setUsername("NightWing");
		this.userDick.setName("Dick Grayson");
		this.userDick.setPassword("1234");
		this.userDick.setEmail("nightwing@gmail.com");
	}


	@Test
	public void searchUser() {

		final List<User> results = this.userService.search("name:fasdfasdfsadfsad,deletedAt^null");
		Assert.assertThat(this.userBruce, isIn(results));
	}

	@Test
	public void searchRole() {

		final List<Role> results  = this.roleService.list(Example.of(Role.builder().name(RoleNameEnum.ROLE_USER).build()));
//		final List<Role> results = this.roleService.search(String.format("name:%s,deletedAt^null", RoleNameEnum.valueOf("ROLE_USER		")));
		Assert.assertTrue(!results.isEmpty());
	}


	@Test
	public void existBy() {
		Boolean exist = this.userService.existsBy("name","bijayemail@");
		Assert.assertTrue(exist);
	}


	@Test
	public void contextLoads() {


		Optional<User> optionalUser = this.userService.save(this.userDick);

		Assert.assertTrue(optionalUser.isPresent());
	}

	@Test
	public void getListByExample() {

		final List<User> results = this.userService.list(Example.of(this.userBruce));
		assertThat(this.userBruce, isIn(results));

	}

}
