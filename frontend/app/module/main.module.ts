import "jquery";
import "slickgrid-jquery-event-drag";
import "tinycolor2";
import "bootstrap";
import "slickgrid-core";
import "slickgrid-grid";
import "slickgrid-dataview";
import "slickgrid-cellrangeselector";
import "slickgrid-cellrangedecorator";
import "slickgrid-rowselectionmodel";
import {NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {ShellComponent} from "./shell.component";
import {MainRoutingModule} from "./main-routing.module";

/**
 * App constants:
 * 'BACKEND_MODE' = 'mock' | 'real'
 */

const APP_PARAMS = {
    "BACKEND_MODE": "real"
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