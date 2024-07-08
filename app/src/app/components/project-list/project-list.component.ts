import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {NgForOf, NgIf} from "@angular/common";
import {Router} from "@angular/router";
import {SidebarComponent} from "../sidebar/sidebar.component";

@Component({
    selector: 'app-project-list',
    standalone: true,
    imports: [
        NgForOf,
        SidebarComponent,
        NgIf,
    ],
    templateUrl: './project-list.component.html',
    styleUrl: './project-list.component.css'
})
export class ProjectListComponent implements OnInit {
    projectList: any[] = [];

    constructor(private http: HttpClient, private router: Router) {
    }

    ngOnInit() {
        this.getProjects();
    }

    getProjects() {
        this.http
            .get('http://localhost:8080/api/projects/', {responseType: "text"})
            .subscribe(response => {
                this.projectList = JSON.parse(response);
            });
    }

    redirectToProjectPage(projectId: number) {
        this.router.navigate(['projects', projectId]).then();
    }

    redirectToProjectEditPage(projectId: number) {
        this.router.navigate(['projects/edit', projectId]).then();
    }

    redirectToProjectCreatePage() {
        this.router.navigate(['projects/create']).then();
    }
}
