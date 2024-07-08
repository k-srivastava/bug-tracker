import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";

@Injectable({providedIn: 'root'})
export class AuthService {
    private loggedIn: boolean = true;

    constructor(private http: HttpClient) {
    }

    login(username: string, password: string): boolean {
        const data = {
            username: username,
            password: password
        }

        this.loggedIn = true;
        return true;

        // this.http
        //     .post("http://localhost:8080/api/login", data, {responseType: "text"})
        //     .subscribe(response => {
        //         this.loggedIn = true;
        //         return true;
        //     }, error => {
        //         this.loggedIn = false;
        //         return false;
        //     });
        //
        // return false;
    }

    logout() {
        this.loggedIn = false;
    }

    isLoggedIn(): boolean {
        return this.loggedIn;
    }
}
