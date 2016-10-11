import {Component, OnInit} from "@angular/core";
import {Item} from "./Item";
declare var $:any;
declare var _:any;

@Component({
    selector: 'addItemDialog',
    templateUrl: 'template/dialogs/add-item.html'
})

export class AddItemComponent implements OnInit {

    public item: Item = new Item;

    ngOnInit(): void {
        let item0: Item = this.item;
        $('[data-toggle="toggle"]').each(function(index: number, elem: any) {
            $(elem).bootstrapToggle();
            $(elem).change(function(val: any) {
                if (_.isEmpty(val.target.id)) {
                    console.error('All ids for toggle elements have to be defined [' + val.target.outerHTML);
                    return;
                }
                console.log("Changed! => id=[" + val.target.id + "] " + val.target.checked);
                item0.advanced[val.target.id] = val.target.checked;
            });
        });

        this.item.name = 'Fridge';
        this.item.description = 'Fridge for storing products';
        this.item.lifecycle = 360;
        this.item._maintenanceDate = new Date();

        this.item.advanced.requiresSpecialist = false;
        this.item.advanced.requiresAdditionalDetails = false;
    }

    public update(): void {

    }

}