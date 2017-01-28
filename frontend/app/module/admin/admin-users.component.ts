import {Component, OnInit} from "@angular/core";
import {Location} from "@angular/common";
import {DataProvider} from "../service/data.service";
import {SlickGridProvider} from "../service/slick-grid.service";

@Component({
    templateUrl: 'mockup/admin/users.html'
})
export class AdminUsersComponent implements OnInit {

    constructor(private dataProvider: DataProvider, private slickGridProvider: SlickGridProvider, private location: Location) {}

    ngOnInit(): void {
        this.dataProvider.getData(this.location.path(), data => {
            this.slickGridProvider.create(data);
        });
    }

}