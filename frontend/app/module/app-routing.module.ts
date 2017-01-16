// import "jquery";
// import "slickgrid-jquery-event-drag";
// import "tinycolor2";
// import "bootstrap";
// import "slickgrid-core";
// import "slickgrid-grid";
import {NgModule} from "@angular/core";
// import {BrowserModule} from "@angular/platform-browser";
import {RouterModule, Routes} from "@angular/router";
import {LoginComponent} from "./login.component";
import {SearchComponent} from "./page-component/search.component";
import {LoginInfoComponent} from "./page-component/login-info.component";
import {HeaderComponent} from "./common-page/page-header.component";
import {ContentComponent} from "./common-page/page-content.component";
import {FiltersComponent} from "./page-component/filters.component";
import {ActionsComponent} from "./page-component/actions.component";
import {NavigationBarComponent} from "./page-component/navigation-bar.component";
import {RegisterComponent} from "./register.component";
import {PageNotFoundComponent} from "./page-not-found.component";
import {ShellComponent} from "./shell.component";
import {AdminComponent} from "./admin/admin.component";
import {AdminAuditComponent} from "./admin/admin-audit.component";
import {AdminConfigComponent} from "./admin/admin-config.component";
import {AdminUsersComponent} from "./admin/admin-users.component";
import {TemplateComponent} from "./common-page/template.component";


const routes: Routes = [
    {path: 'login', component: LoginComponent},
    {path: 'register', component: RegisterComponent},
    {path: '', redirectTo: 'login', pathMatch: 'full'},

    {path: 'admin', component: TemplateComponent, children: [
        {path: '', component: AdminComponent, children: [
            {path: '', redirectTo: 'users'},
            {path: 'users', component: AdminUsersComponent},
            {path: 'config', component: AdminConfigComponent},
            {path: 'audit', component: AdminAuditComponent}
        ]},
    ]},

    {path: '**', component: PageNotFoundComponent}
];

@NgModule({
    imports: [
        // BrowserModule,
        RouterModule.forRoot(routes)
    ],
    declarations: [LoginComponent, SearchComponent, LoginInfoComponent, HeaderComponent, ContentComponent,
        FiltersComponent, ActionsComponent, NavigationBarComponent, RegisterComponent, PageNotFoundComponent,
        ShellComponent, AdminComponent, AdminUsersComponent, AdminConfigComponent, AdminAuditComponent, TemplateComponent]
    // exports: [RouterModule]
    // bootstrap: [ShellComponent]
})
export class MainRoutingModule {
}