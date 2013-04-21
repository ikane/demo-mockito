/**
 * 
 */
package org.ikane.demo.mockito.service;

import org.ikane.demo.mockito.domain.IAccount;

/**
 * @author ikane
 *
 */
public class AfterFirstFailedLoginAttempt extends LoginServiceState {
	
	private String previousAccountId;

	public AfterFirstFailedLoginAttempt(String previousAccountId) {
		this.previousAccountId = previousAccountId;
		failedAttemps = 1;
	}

	@Override
	public void handleIncorrectPassword(LoginService context, String password,
			IAccount account) {
		if (previousAccountId.equals(account.getId()))
            context.setState(new AfterSecondFailedLoginAttempt(account.getId()));
         else
            previousAccountId = account.getId();
	}

}
