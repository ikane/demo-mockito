/**
 * 
 */
package org.ikane.demo.mockito.service;

import org.ikane.demo.mockito.domain.IAccount;


/**
 * @author ikane
 *
 */
public abstract class LoginServiceState {
	
	protected int failedAttemps = 0;

	/**
	 * 
	 */
	public LoginServiceState() {
	}
	
	abstract public void login(LoginService context, String password, IAccount account); 
}
