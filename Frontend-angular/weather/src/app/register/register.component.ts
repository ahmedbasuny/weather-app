import { Component, OnInit } from '@angular/core';
import { SignupModel } from '../auth/model/SignupModel';
import { AuthService } from '../auth/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  form: any = {};
  signupInfo: SignupModel;
  isSignedUp = false;
  isSignUpFailed = false;
  errorMessage = '';
 
  constructor(private authService: AuthService) { }
 
  ngOnInit() { }
 
  /**
   * submit registration data
   * @author Ahmed Basuny
   */
  onSubmit() {
    console.log("onSubmit function has been called.");
    console.log(this.form);
 
    this.signupInfo = new SignupModel(
      this.form.name,
      this.form.email,
      this.form.phone,
      this.form.password);
 
    this.authService.signUp(this.signupInfo).subscribe(
      data => {
        console.log(data);
        this.isSignedUp = true;
        this.isSignUpFailed = false;
      },
      error => {
        console.log(error);
        this.errorMessage = error.error.message;
        this.isSignUpFailed = true;
      }
    );
  }

}
