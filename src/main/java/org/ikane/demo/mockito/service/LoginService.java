/**
 * 
 */
package org.ikane.demo.mockito.service;

import org.ikane.demo.mockito.dao.IAccountRepository;
import org.ikane.demo.mockito.domain.IAccount;
import org.ikane.demo.mockito.exception.AccountLoginLimitReachedException;
import org.ikane.demo.mockito.exception.AccountNotFoundException;
import org.ikane.demo.mockito.exception.AccountRevokedException;

/**
 * @author IKANE
 *
 */
public class LoginService {
	
	private IAccountRepository accountRepository;
	
	private int failedAttemps = 0;
	private String previousAccount="";

	public LoginService(IAccountRepository accountRepository) {
		this.accountRepository = accountRepository;	
	}

	public void login(String accountId, String password) {
		IAccount account = this.accountRepository.find(accountId);
		
		if(account==null) {
			throw new AccountNotFoundException();
		} 
		
		if(account.passwordMatches(password) == true) {
			if(account.isLoggedIn()) {
				throw new AccountLoginLimitReachedException();
			} else if(account.isRevoked()) {
				throw new AccountRevokedException();
			}
			account.setLoggedIn(true);
		} else {
			if(this.previousAccount.equals(accountId)) {
				failedAttemps++;
			} else {
				failedAttemps = 1;
				this.previousAccount = accountId;
			}
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
