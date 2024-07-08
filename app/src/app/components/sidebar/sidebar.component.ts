import {Component} from '@angular/core';
import {Router} from "@angular/router";
import {AuthService} from "../../auth.service";

@Component({
    selector: 'app-sidebar',
    standalone: true,
    imports: [],
    templateUrl: './sidebar.component.html',
    styleUrl: './sidebar.component.css'
})
export class SidebarComponent {
    constructor(private router: Router, private authService: AuthService) {
    }

    redirectToProjects() {
        this.router.navigate(['projects']).then();
    }

    redirectToBugs() {
    }

    redirectToUser() {
    }

    logOut() {
        this.authService.logout();
    }
}
