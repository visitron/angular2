import {Component, OnInit} from "@angular/core";
import {Item} from "./Item";

@Component({
    selector: 'addItemDialog',
    templateUrl: 'template/dialogs/add-item.html'
})

export class AddItemComponent implements OnInit {

    public item: Item = new Item('Test', 'Test desc', 0, new Date, []);

    constructor() {}

    ngOnInit(): void {
        // this.item.name = 'Fridge';
        // this.item.description = 'Fridge for storing products';
        // this.item.lifecycle = 360;
        // this.item.maintenanceDate = new Date();
        //
        // this.item.advanced.requiresSpecialist = false;
        // this.item.advanced.requiresAdditionalDetails = false;
    }

}