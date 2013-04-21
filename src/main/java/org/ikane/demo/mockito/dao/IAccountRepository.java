/**
 * 
 */
package org.ikane.demo.mockito.dao;

import org.ikane.demo.mockito.domain.IAccount;

/**
 * @author IKANE
 *
 */
public interface IAccountRepository {

	IAccount find(String accountId);

}
