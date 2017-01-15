import "jquery";
import "slickgrid-jquery-event-drag";
import "tinycolor2";
import "bootstrap";
import "slickgrid-core";
import "slickgrid-grid";
import {NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {RouterModule} from "@angular/router";
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
import {AdminComponent} from "./common-page/admin.component";
/**
 * App constants:
 * 'BACKEND_MODE' = 'mock' | 'real'
 */

const APP_PARAMS = {
    "BACKEND_MODE": "mock"
};

@NgModule({
    imports: [
        BrowserModule,
        RouterModule.forRoot([
            {path: 'login', component: LoginComponent},
            {path: 'register', component: RegisterComponent},
            {path: 'admin', component: AdminComponent},
            {path: '**', component: PageNotFoundComponent},
            {path: '', redirectTo: 'login', pathMatch: 'full'}
        ])
    ],
    declarations: [LoginComponent, SearchComponent, LoginInfoComponent, HeaderComponent, ContentComponent,
        FiltersComponent, ActionsComponent, NavigationBarComponent, RegisterComponent, PageNotFoundComponent,
        ShellComponent, AdminComponent],
    // imports: [BrowserModule, FormsModule, UiSwitchModule, HttpModule],
    // declarations: [ItemListComponent, AddItemComponent],
    // providers: [
    //     ItemService,
    //     NotificationService,
    //     {provide: "APP_PARAMS", useValue: APP_PARAMS},
    //     {provide: Paths, useFactory: PathsFactory, deps: ["APP_PARAMS"]}
    // ],
    bootstrap: [ShellComponent]
})
export class MainModule {
}