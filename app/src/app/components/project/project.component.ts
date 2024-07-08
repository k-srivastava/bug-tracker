import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {HttpClient} from "@angular/common/http";
import {SidebarComponent} from "../sidebar/sidebar.component";

@Component({
    selector: 'app-project',
    standalone: true,
    imports: [
        SidebarComponent
    ],
    templateUrl: './project.component.html',
    styleUrl: './project.component.css'
})
export class ProjectComponent implements OnInit {
    private apiResponse: any;

    id: number | undefined;
    name: string = "";
    owner: string = "";
    description: string = "";

    constructor(private route: ActivatedRoute, private http: HttpClient, private router: Router) {
    }

    ngOnInit() {
        this.route.params.subscribe(params => {
           this.id = params['id'];
           this.getProjectDetails();
        });
    }

    getProjectDetails() {
        this.http
            .get(`http://localhost:8080/api/projects/${this.id}`, {responseType: "text"})
            .subscribe(response => {
                this.apiResponse = JSON.parse(response);

                this.name = this.apiResponse['name'];
                this.owner = this.apiResponse['owner_username'];
                this.description = this.apiResponse['description'];
            });
    }

    redirectToProjectEditPage(projectId: number) {
        this.router.navigate(['projects/edit', projectId]).then();
    }
}
