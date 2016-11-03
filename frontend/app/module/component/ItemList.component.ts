import {Component, OnInit, Sanitizer, SecurityContext} from "@angular/core";
import {DomSanitizer, SafeHtml} from '@angular/platform-browser';
import * as moment from 'moment';
import * as Color from 'color-js';
import {AddItemComponent} from "./AddItem.component";
import {ItemService} from "./Item.service";
import {Item} from "./Item";
import {InfoMapping} from "./Item";
import {Observable} from 'rxjs';

@Component({
    selector: 'maintenanceApp',
    templateUrl: 'template/items-list.html'
})

export class ItemListComponent implements OnInit {
    constructor(private itemService: ItemService, private sanitizer: DomSanitizer) {}

    public items: Item[] = [];

    ngOnInit(): void {
        this.itemService.getItems().subscribe(items => this.items = items);
    }

    getGlyphicons(item: Item): SafeHtml {
        return this.sanitizer.bypassSecurityTrustHtml(InfoMapping.get(item.info));
    }

    calcDaysRemains(item: Item): string {
        this.calcColorCode(item);
        let diff: number = moment().diff(moment(item.maintenanceDate), "days");
        console.log(diff);
        return ((item.lifecycle - diff) * 100 / item.lifecycle).toString() + "%";
    }

    calcColorCode(item: Item): string {
        let d: any = Color();
        console.log(d);
        return 'red';
    }
}