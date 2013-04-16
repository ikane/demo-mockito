/**
 * 
 */
package org.ikane.demo.mockito;

/**
 * @author IKANE
 *
 */
public class LoginService {
	
	private IAccountRepository accountRepository;
	
	private int failedAttemps = 0;

	public LoginService(IAccountRepository accountRepository) {
		this.accountRepository = accountRepository;	
	}

	public void login(String accountId, String password) {
		IAccount account = this.accountRepository.find(accountId);
		account.setLoggedIn(true);
		if(account.passwordMatches(password) == false) {
			failedAttemps++;
		}
		if(failedAttemps==3) {
			account.setRevoked(true);
		}
	}

	public IAccountRepository getAccountRepository() {
		return accountRepository;
	}

	public void setAccountRepository(IAccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}
}
