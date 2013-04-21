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
public class AfterSecondFailedLoginAttempt extends LoginServiceState {

	private String previousAccountId;
	 
	   public AfterSecondFailedLoginAttempt(String previousAccountId) {
	      this.previousAccountId = previousAccountId;
	      failedAttemps = 2;
	   }

	/**
	 * @param password
	 * @param account
	 */
	public void login(LoginService context, String password, IAccount account) {
		if(account.passwordMatches(password) == true) {
			if(account.isLoggedIn()) {
				throw new AccountLoginLimitReachedException();
			} else if(account.isRevoked()) {
				throw new AccountRevokedException();
			}
			account.setLoggedIn(true);
		} else {
			if (previousAccountId.equals(account.getId())) {
	            account.setRevoked(true);
	            context.setState(new AwaitingFirstLoginAttempt());
	         } else {
	            context.setState(new AfterFirstFailedLoginAttempt(account.getId()));
	         }
		}
	}

}
