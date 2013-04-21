/**
 * 
 */
package org.ikane.demo.mockito.service;

import org.ikane.demo.mockito.domain.IAccount;

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

	
	@Override
	public void handleIncorrectPassword(LoginService context, String password,
			IAccount account) {
		if (previousAccountId.equals(account.getId())) {
            account.setRevoked(true);
            context.setState(new AwaitingFirstLoginAttempt());
         } else {
            context.setState(new AfterFirstFailedLoginAttempt(account.getId()));
         }
	}

}
