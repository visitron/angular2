import {Component, OnInit, Sanitizer, SecurityContext} from "@angular/core";
import {DomSanitizer, SafeHtml} from '@angular/platform-browser';
import * as moment from 'moment';
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
        let nowDate: Date = new Date;
        let itemDate: Date = new Date(item.maintenanceDate);

        let durationNow: moment.Duration = moment.duration({y: nowDate.getUTCFullYear(), m: nowDate.getUTCMonth(), day: nowDate.getUTCDay(), ms: 0});
        let durationItem: moment.Duration = moment.duration({y: itemDate.getUTCFullYear(), m: itemDate.getUTCMonth(), day: itemDate.getUTCDay(), ms: 0});

        let days: number = durationNow.subtract(durationItem).asDays();

        console.log(days);

        return (days * 100 / item.lifecycle).toString() + '%';
    }
}