import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { LoginModel } from './model/LoginModel';
import { Observable } from 'rxjs';
import { JwtResponse } from './model/JwtResponse';
import { SignupModel } from './model/SignupModel';


const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private loginUrl = 'http://localhost:8080/api/auth/signin';
  private signupUrl = 'http://localhost:8080/api/auth/signup';

  constructor(private http: HttpClient) { }

  /**
   * login function
   * @param credentials model (username, password)
   * @returns Observable<JwtResponse>
   * @author Ahmed Basuny
   */
  attemptAuth(credentials: LoginModel): Observable<JwtResponse> {
    console.info("attemptAuth function has been called...");
    console.info("attemptAuth function params ==> " + credentials.mail);

    return this.http.post<JwtResponse>(this.loginUrl, credentials, httpOptions);
  }

  /**
   * register
   * @param info model with user data
   * @returns Observable<strings>
   * @author Ahmed Basuny
   */
  signUp(info: SignupModel): Observable<string> {
    console.info("signUp function has been called...");
    console.info("signUp function params ==> ( " + info.name + ", " + info.mail + ", " + info.phone + " )");

    return this.http.post<string>(this.signupUrl, info, httpOptions);
  }
}
