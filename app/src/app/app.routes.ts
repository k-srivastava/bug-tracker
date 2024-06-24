import {RouterModule, Routes} from '@angular/router';
import {AppComponent} from "./app.component";
import {LoginComponent} from "./components/login/login.component";
import {NgModule} from "@angular/core";
import {ProjectCreateComponent} from "./components/project-create/project-create.component";

export const routes: Routes = [
    {path: 'login', component: LoginComponent},
    {path: 'project/create', component: ProjectCreateComponent},
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {
}
