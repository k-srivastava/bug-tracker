import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BugTrackerService {
  private baseUrl = 'http://localhost:8082/api';

  constructor(private http: HttpClient) { }

  createProject(projectData: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/projects/create`, projectData);
  }

  assignBug(bugId: number, bugOwner: string): Observable<any> {
    return this.http.put(`${this.baseUrl}/bugs/${bugId}/assign`, { owner: bugOwner });
  }

  closeBug(bugId: number): Observable<any> {
    return this.http.put(`${this.baseUrl}/bugs/${bugId}/close`, null);
  }
}
