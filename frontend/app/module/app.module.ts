import {NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {ItemComponent} from "./component/items-list.component";
import {AddItemComponent} from "./component/add-item.component";

@NgModule({
    imports: [BrowserModule],
    declarations: [ItemComponent, AddItemComponent],
    bootstrap: [ItemComponent]
})

export class MaintenanceModule {}