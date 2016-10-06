import {NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {ItemComponent} from "./component/itemComponent";

@NgModule({
    imports: [BrowserModule],
    declarations: [ItemComponent],
    bootstrap: [ItemComponent]
})

export class MaintenanceModule {}