/**
 * 
 */
package org.ikane.demo.mockito.service;

import org.ikane.demo.mockito.domain.IAccount;

/**
 * @author ikane
 *
 */
public class AwaitingFirstLoginAttempt extends LoginServiceState {

	/**
	 * 
	 */
	public AwaitingFirstLoginAttempt() {
	}

	@Override
	public void handleIncorrectPassword(LoginService context, String password,
			IAccount account) {
		context.setState(new AfterFirstFailedLoginAttempt(account.getId()));
	}
}
