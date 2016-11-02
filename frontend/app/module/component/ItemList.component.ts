import {Component, OnInit} from "@angular/core";
import {AddItemComponent} from "./AddItem.component";
import {ItemService} from "./Item.service";
import {Item} from "./Item";
import {Observable} from 'rxjs';

@Component({
    selector: 'maintenanceApp',
    templateUrl: 'template/items-list.html'
})

export class ItemListComponent implements OnInit {
    constructor(private itemService: ItemService) {}

    public items: Item[] = [];

    ngOnInit(): void {
        this.itemService.getItems().subscribe(items => this.items = items);
    }
}