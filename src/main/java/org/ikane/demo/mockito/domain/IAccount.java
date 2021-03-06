/**
 * 
 */
package org.ikane.demo.mockito.domain;

/**
 * @author IKANE
 *
 */
public interface IAccount {

	boolean passwordMatches(String candidate);

	void setLoggedIn(boolean b);

	void setRevoked(boolean b);

	boolean isLoggedIn();

	boolean isRevoked();
	
	String getId();

}
