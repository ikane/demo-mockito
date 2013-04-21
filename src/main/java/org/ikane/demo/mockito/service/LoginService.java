/**
 * 
 */
package org.ikane.demo.mockito.service;

import org.ikane.demo.mockito.dao.IAccountRepository;
import org.ikane.demo.mockito.domain.IAccount;
import org.ikane.demo.mockito.exception.AccountNotFoundException;

/**
 * @author IKANE
 *
 */
public class LoginService {
	
	private IAccountRepository accountRepository;
	
	private LoginServiceState state = new AwaitingFirstLoginAttempt();

	public LoginService(IAccountRepository accountRepository) {
		this.accountRepository = accountRepository;	
	}

	public void login(String accountId, String password) {
		IAccount account = this.accountRepository.find(accountId);
		
		if(account==null) {
			throw new AccountNotFoundException();
		} 
		
		state.login(this, password, account);
	}

	public IAccountRepository getAccountRepository() {
		return accountRepository;
	}

	public void setAccountRepository(IAccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	public LoginServiceState getState() {
		return state;
	}

	public void setState(LoginServiceState state) {
		this.state = state;
	}
}
