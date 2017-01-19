import {Component, OnInit} from "@angular/core";
import {Location} from "@angular/common";
import {DataProvider} from "../service/data-provider.service";

@Component({
    templateUrl: 'mockup/admin/users.html'
})
export class AdminUsersComponent implements OnInit {

    constructor(private dataProvider: DataProvider, private location: Location) {
        dataProvider.getData(location, data => {
            console.log(data);
        });
    }

    ngOnInit(): void {

    }

}