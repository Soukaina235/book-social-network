import { Component } from '@angular/core';
import {Router} from "@angular/router";
import {AuthenticationService} from "../../services/services/authentication.service";

@Component({
  selector: 'app-activate-account',
  templateUrl: './activate-account.component.html',
  styleUrls: ['./activate-account.component.scss']
})
export class ActivateAccountComponent {
  message = ''; // this will hold any kind of message, weather success message, activation message or error message
  isOkay = true; // to track if the account activation is okay or not
  submitted = false; // track if the user has submitted the activation code or not

  constructor(
    private router: Router,
    private authService: AuthenticationService
  ) {
  }

  onCodeCompleted(token: string) {
    this.confirmAccount(token);
  }

  redirectToLogin() {
    this.router.navigate(['login']);
  }

  private confirmAccount(token: string) {
    this.authService.confirm({
      token
    }).subscribe({
      next: () => {
        this.message = 'Your account has been successfully activated. \nNow you can proceed to login.';
        this.submitted = true;
        this.isOkay = true;
      },
      error: () => {
        this.message = 'The token has been expired or is invalid.';
        this.submitted = true;
        this.isOkay = false;
      }
    });
  }
}
