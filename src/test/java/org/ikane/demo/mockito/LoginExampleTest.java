/**
 * 
 */
package org.ikane.demo.mockito;

import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

/**
 * @author IKANE
 *
 */
public class LoginExampleTest {

	private IAccount account;
	private IAccountRepository accountRepository;
	private LoginService loginService;
	
	@Before
	public void init() {
		account = mock(IAccount.class);
		accountRepository = mock(IAccountRepository.class);
		when(accountRepository.find(anyString())).thenReturn(account);
		loginService = new LoginService(accountRepository);
	}
	
	private void willPasswordMatch(boolean value) {
		when(account.passwordMatches(anyString())).thenReturn(value);
	}

	/**
	 * Test 1: Basic Happy Path
	 */
	@Test
	public void itShouldSetAccountToLoggedInWhenPasswordMatches() {
		willPasswordMatch(true);
		loginService.login("ikane","1234");
		verify(account, times(1)).setLoggedIn(true);
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
		verify(account, times(1)).setRevoked(true);
	}
	
	/**
	 * Test 3: setLoggedIn not called if password does not match
	 */
	@Test
	public void itShouldNotSetAccountLoggedInIfPasswordDoesNotMatch() {
		willPasswordMatch(false);
		loginService.login("brett", "password");
	    verify(account, never()).setLoggedIn(true);
	}
	
	/**
	 * Test 4: Two Fails on One Account Followed By Fail on Second Account
	 */
	@Test
	public void itShouldNotRevokeSecondAccountAfterTwoFailedAttemptsFirstAccount() {
		willPasswordMatch(false);
		IAccount account2 = mock(IAccount.class);
		when(accountRepository.find("ikane")).thenReturn(account2);
		when(account2.passwordMatches(anyString())).thenReturn(false);
		
		loginService.login("brett", "password");
		loginService.login("brett", "password");
		loginService.login("ikane", "password");
		
		verify(account2, never()).setRevoked(true);
	}
	
	/**
	 * Test 5: Do not allow a second login
	 */
	@Test(expected=AccountLoginLimitReachedException.class)
	public void itShouldNowAllowConcurrentLogins() {
		willPasswordMatch(true);
		when(this.account.isLoggedIn()).thenReturn(true);
		loginService.login("brett", "password");
	}
	
	/**
	 * Test 6: AccountNotFoundException thrown if account is not found
	 */
	@Test(expected = AccountNotFoundException.class)
	public void ItShouldThrowExceptionIfAccountNotFound() {
		when(this.accountRepository.find("ikane2")).thenReturn(null);
		loginService.login("ikane2", "password");
	}
	
	/**
	 * Test 7: Cannot Login to Revoked Account
	 */
	@Test(expected = AccountRevokedException.class)
	public void ItShouldNotBePossibleToLogIntoRevokedAccount() {
		willPasswordMatch(true);
		when(this.account.isRevoked()).thenReturn(true);
		loginService.login("ikane", "password");
	}
}
