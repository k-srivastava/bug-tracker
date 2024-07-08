import {Component} from '@angular/core';
import {FormsModule} from "@angular/forms";
import {AuthService} from "../../auth.service";
import {Router} from "@angular/router";

@Component({
    selector: 'app-login',
    standalone: true,
    imports: [FormsModule],
    providers: [],
    templateUrl: './login.component.html',
    styleUrl: './login.component.css'
})
export class LoginComponent {
    username: string = "";
    password: string = "";
    loginMessage: string = "";

    constructor(private authService: AuthService, private router: Router) {
    }

    onSubmit(): void {
        const result = this.authService.login(this.username, this.password);

        if (result) {
            this.loginMessage = `Login successful for ${this.username}.`;
            setTimeout(() => {
                this.router.navigate(['projects']).then();
            }, 500);
        } else
            this.loginMessage = 'Login failed. Invalid email or password.';
    }

    logOut() {
        this.authService.logout();
    }
}
