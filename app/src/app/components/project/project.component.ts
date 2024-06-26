import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {HttpClient} from "@angular/common/http";

@Component({
    selector: 'app-project',
    standalone: true,
    imports: [],
    templateUrl: './project.component.html',
    styleUrl: './project.component.css'
})
export class ProjectComponent implements OnInit {
    id: number | undefined;
    name: string = "";

    constructor(private route: ActivatedRoute, private http: HttpClient) {
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
            .subscribe(response => {this.name = response});
    }
}
