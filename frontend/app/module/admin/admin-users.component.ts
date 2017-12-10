import {Component, OnInit} from "@angular/core";
import {Location} from "@angular/common";
import {DataProvider} from "../service/data.service";
import {SlickGridProvider} from "../service/slick-grid.service";
import {ActionContext} from "../page-component/actions.component";
import {ActionService} from "../service/action.service";
import * as _ from "underscore";

@Component({
    templateUrl: 'mockup/admin/users.html'
})
export class AdminUsersComponent implements OnInit {

    constructor(private dataProvider: DataProvider, private slickGridProvider: SlickGridProvider,
                private location: Location, private actionService: ActionService) {
        this.actionService.subscribe(this.onAction.bind(this));
    }

    ngOnInit(): void {
        this.dataProvider.getData(this.location.path(), data => {
            this.slickGridProvider.create(data);
            this.slickGridProvider.attachDataProvider(this.dataProvider, this.location.path());
        });
    }

    onAction(context: ActionContext): void {
        if (context.action.id === 'refresh') {
            context.executeAction(true, (monitor) => {
                monitor();
            });
        } else {
            context.data = _.values(this.slickGridProvider.getSelectedIds());
            context.executeAction(true);
        }
    }

}