import "jquery";
import "slickgrid-jquery-event-drag";
import "tinycolor";
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
import {ConfigProvider} from "./service/config.service";

/**
 * App constants:
 * 'mode' = 'mock' | 'remote'
 */

export const CONFIG = {
    "mode": "remote",
    "remote": "http://localhost:3002",
    "mock": "/mock"
};

@NgModule({
    imports: [
        BrowserModule,
        MainRoutingModule
    ],
    providers: [ConfigProvider],
    bootstrap: [ShellComponent]
})
export class MainModule {
}