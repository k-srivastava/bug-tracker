import {Component} from '@angular/core';
import {FormsModule} from "@angular/forms";
import {HttpClient} from "@angular/common/http";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatButtonModule} from "@angular/material/button";

@Component({
    selector: 'app-login',
    standalone: true,
    imports: [
        FormsModule,
        MatFormFieldModule,
        MatInputModule,
        MatButtonModule
    ],
    providers: [],
    templateUrl: './login.component.html',
    styleUrl: './login.component.css'
})
export class LoginComponent {
    username: string = "";
    password: string = "";
    loginMessage: string = "";

    constructor(private http: HttpClient) {
    }

    onSubmit(): void {
        const loginData = {
            username: this.username,
            password: this.password
        };

        this.http
            .post("http://localhost:8080/api/login", loginData, {responseType: "text"})
            .subscribe(response => {
                this.loginMessage = response
            }, error => {
                this.loginMessage = error.error
            });
    }
}

