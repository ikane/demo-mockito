/**
 * 
 */
package org.ikane.demo.mockito;

import org.junit.Test;
import org.mockito.Mockito;

/**
 * @author IKANE
 *
 */
public class LoginExampleTest {

	/**
	 * Test 1: Basic Happy Path
	 */
	@Test
	public void itShouldSetAccountToLoggedInWhenPasswordMatches() {
		IAccount account = Mockito.mock(IAccount.class);
		Mockito.when(account.passwordMatches(Mockito.anyString())).thenReturn(true);
		
		IAccountRepository accountRepository = Mockito.mock(IAccountRepository.class);
		Mockito.when(accountRepository.find(Mockito.anyString())).thenReturn(account);
		
		LoginService loginService = new LoginService(accountRepository);
		
		loginService.login("ikane","1234");
		
		Mockito.verify(account, Mockito.times(1)).setLoggedIn(true);
	}
}
