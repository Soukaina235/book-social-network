import { Component } from '@angular/core';
import {AuthenticationRequest} from "../../services/models/authentication-request";
import {Router} from "@angular/router";
import {AuthenticationService} from "../../services/services/authentication.service";
import {TokenService} from "../../services/token/token.service";
import {AuthenticationResponse} from "../../services/models/authentication-response";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  authRequest: AuthenticationRequest = {email: '', password: ''}
  errorMsg: Array<string> = [];

  constructor(
    private router: Router,
    private authService: AuthenticationService,
    private tokenService: TokenService
  ) {
  }

  login() {
    // reset the error messages in case we had some before (like when user enters bad credentials)
    this.errorMsg = [];
    this.authService.authenticate({
        body: this.authRequest
    }).subscribe({
      // this is a callback function that is executed if the authentication request is successful
      next: (res) => {
        // save the token
        this.tokenService.token = res.token as string;
        this.router.navigate(['books'])
      },
      error: (err) => { // This callback function is executed if the authentication request fails.
        console.log(err);

/*        // he didn't need to parse the error in the tutorial, but i had to because my error is a string
        // and to access the validationErrors, i need it to be of type JSON
        let parsedError;
        try {
          parsedError = JSON.parse(err.error);
          console.log(parsedError.validationErrors);
        } catch (e) {
          console.error('Error parsing JSON:', e);
        }*/
        if (err.error.validationErrors) { // we named it validationErrors in our backend, so the json error will contain this validationErrors
          this.errorMsg = err.error.validationErrors; // errorMsg is a list
        } else {
          // in case we have a BadCredentialsException for example, because we in the backend error handler we specified error in this case
          this.errorMsg.push(err.error.error)
        }
      }
    })
  }

  register() {
    this.router.navigate(['register']);
  }
}
