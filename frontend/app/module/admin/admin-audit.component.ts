import {Component, OnInit} from "@angular/core";
import {Location} from "@angular/common";
import {DataProvider} from "../service/data.service";
import {SlickGridProvider} from "../service/slick-grid.service";
import "@types/slickgrid";

@Component({
    templateUrl: 'mockup/admin/audit.html'
})
export class AdminAuditComponent implements OnInit {

    private grid: Slick.Grid<any> = null;

    constructor(private dataProvider: DataProvider, private slickGridProvider: SlickGridProvider, private location: Location) {}

    ngOnInit(): void {

        this.dataProvider.getData(this.location.path(), data => {
            this.grid = this.slickGridProvider.create(data);
        });

    }
}