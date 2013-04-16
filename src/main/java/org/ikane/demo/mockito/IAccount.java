/**
 * 
 */
package org.ikane.demo.mockito;

/**
 * @author IKANE
 *
 */
public interface IAccount {

	boolean passwordMatches(String candidate);

	void setLoggedIn(boolean b);

	void setRevoked(boolean b);

}
