import {Component, OnInit} from "@angular/core";

@Component({
    selector: 'addItemDialog',
    templateUrl: 'template/dialogs/add-item.html'
})

export class AddItemComponent implements OnInit {

    ngOnInit(): void {
        $('[data-toggle="toggle"]').each(function(index: number, elem: any) {$(elem).bootstrapToggle()});
    }

}