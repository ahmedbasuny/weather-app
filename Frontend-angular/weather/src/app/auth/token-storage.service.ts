import { Injectable } from '@angular/core';


const TOKEN_KEY = 'AuthToken';
const USERNAME_KEY = 'AuthUsername';
const AUTHORITIES_KEY = 'AuthAuthorities';

@Injectable({
  providedIn: 'root'
})
export class TokenStorageService {

  private roles: Array<string> = [];

  constructor() { }

  /**
   * signout function
   * @author Ahmed Basuny
   */
  signOut() {
    console.info("signOut function has been called...");
    window.sessionStorage.clear();
  }

  /**
   * save token
   * @param token: string
   * @author Ahmed Basuny
   */
  public saveToken(token: string) {
    console.info("saveToken function has been called...");
    console.info("saveToken function params ==> ( " + token + " )");
    window.sessionStorage.removeItem(TOKEN_KEY);
    window.sessionStorage.setItem(TOKEN_KEY, token);
  }

  /**
   * get token
   * @returns token: string
   * @author Ahmed Basuny
   */
  public getToken(): string {
    console.info("getToken function has been called...");
    return sessionStorage.getItem(TOKEN_KEY);
  }

  /**
   * save user name
   * @param username: string
   * @author Ahmed Basuny
   */
  public saveUsername(username: string) {
    console.info("saveUsername function has been called...");
    console.info("saveUsername function params ==> ( " + username + " )");
    window.sessionStorage.removeItem(USERNAME_KEY);
    window.sessionStorage.setItem(USERNAME_KEY, username);
  }

  /**
   * get username
   * @returns username: string
   * @author Ahmed Basuny
   */
  public getUsername(): string {
    console.info("getUsername function has been called...");
    return sessionStorage.getItem(USERNAME_KEY);
  }

  /**
   * save roles
   * @param authorities: string[]
   * @author Ahmed Basuny
   */
  public saveAuthorities(authorities: string[]) {
    console.info("saveAuthorities function has been called...");
    console.info("saveAuthorities function params ==> ( " + authorities.every + " )");
    window.sessionStorage.removeItem(AUTHORITIES_KEY);
    window.sessionStorage.setItem(AUTHORITIES_KEY, JSON.stringify(authorities));
  }

  /**
   * get roles
   * @returns authorities: string[]
   * @author Ahmed Basuny
   */
  public getAuthorities(): string[] {
    console.info("getAuthorities function has been called...");
    this.roles = [];
    if (sessionStorage.getItem(TOKEN_KEY)) {
      JSON.parse(sessionStorage.getItem(AUTHORITIES_KEY)).forEach(authority => {
        this.roles.push(authority.authority);
      });
    }
    return this.roles;
  }
}
