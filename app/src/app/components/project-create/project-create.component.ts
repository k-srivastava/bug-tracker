import {Component} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {FormsModule} from "@angular/forms";

@Component({
    selector: 'app-project-create',
    standalone: true,
    imports: [
        FormsModule
    ],
    templateUrl: './project-create.component.html',
    styleUrl: './project-create.component.css'
})
export class ProjectCreateComponent {
    name: string = "";
    owner: string = "";
    description: string = "";
    creationMessage: string = "";

    constructor(private http: HttpClient) {
    }

    onSubmit(): void {
        const projectData = {
            name: this.name,
            owner_username: this.owner,
            description: this.description !== "" ? this.description : null,
        };

        this.http
            .post("http://localhost:8080/api/projects/create", projectData, {responseType: "text"})
            .subscribe(response => {
                this.creationMessage = response
            }, error => {
                this.creationMessage = error.error
            });
    }
}
