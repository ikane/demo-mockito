/**
 * 
 */
package org.ikane.demo.mockito.service;

import org.ikane.demo.mockito.domain.IAccount;
import org.ikane.demo.mockito.exception.AccountLoginLimitReachedException;
import org.ikane.demo.mockito.exception.AccountRevokedException;


/**
 * @author ikane
 *
 */
public abstract class LoginServiceState {
	
	protected int failedAttemps = 0;

	public void login(LoginService context, String password, IAccount account) {
		if(account.passwordMatches(password) == true) {
			if(account.isLoggedIn()) {
				throw new AccountLoginLimitReachedException();
			} else if(account.isRevoked()) {
				throw new AccountRevokedException();
			}
			account.setLoggedIn(true);
			context.setState(new AwaitingFirstLoginAttempt());
		} else {
			handleIncorrectPassword(context, password, account);
		}
	}
	
	abstract public void handleIncorrectPassword(LoginService context, String password, IAccount account); 
}
