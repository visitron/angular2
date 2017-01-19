import "jquery";
import "slickgrid-jquery-event-drag";
import "tinycolor2";
import "bootstrap";
import "slickgrid-core";
import "slickgrid-grid";
import "slickgrid-cellrangeselector";
import "slickgrid-cellrangedecorator";
import {NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {ShellComponent} from "./shell.component";
import {MainRoutingModule} from "./main-routing.module";

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
        MainRoutingModule,
    ],
    bootstrap: [ShellComponent]
})
export class MainModule {
}