/**
 * 
 */
package org.ikane.demo.mockito;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * @author IKANE
 *
 */
public class LoginExampleTest {

	private IAccount account;
	private IAccountRepository accountRepository;
	private LoginService loginService;
	
	@BeforeClass
	public void init() {
		account = Mockito.mock(IAccount.class);
		accountRepository = Mockito.mock(IAccountRepository.class);
		Mockito.when(accountRepository.find(Mockito.anyString())).thenReturn(account);
		loginService = new LoginService(accountRepository);
	}

	/**
	 * Test 1: Basic Happy Path
	 */
	@Test
	public void itShouldSetAccountToLoggedInWhenPasswordMatches() {
		
		willPasswordMatch(true);
		
		loginService.login("ikane","1234");
		
		Mockito.verify(account, Mockito.times(1)).setLoggedIn(true);
	}

	private void willPasswordMatch(boolean value) {
		Mockito.when(account.passwordMatches(Mockito.anyString())).thenReturn(value);
	}
	
	/**
	 * Test 2: 3 Failed Logins Causes Account to be Revoked
	 */
	@Test
	public void itShouldSetAccountToRevokedAfterThreeFailedLoginAttempts() {
		
		willPasswordMatch(false);
		
		for(int i=0; i<3; i++) {
			loginService.login("ikane", "1234");
		}	
		
		Mockito.verify(account, Mockito.times(1)).setRevoked(true);
	}
}
