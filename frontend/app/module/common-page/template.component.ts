import {Component} from "@angular/core";
import {Location} from "@angular/common";
import {Action, ActionContext} from "../page-component/actions.component";
import {ActionService} from "../service/action.service";
import {SlickGridProvider} from "../service/slick-grid.service";
import {Http} from "@angular/http";

@Component({
    templateUrl: 'mockup/parts/template.html'
})
export class TemplateComponent {
    private actionContext: ActionContext;
    private busy: boolean = false;

    constructor(private actionService: ActionService, private location: Location,
                private slickGridProvider: SlickGridProvider, private http: Http) {

        this.actionContext = new ActionContext(new Action({id: "refresh"}), null, http, slickGridProvider,
            this.complete.bind(this));
    }
    refresh(): void {
        this.busy = true;

        this.actionContext.actionURL = this.location.path();
        this.actionContext.data = this.busy;

        this.actionService.sendAction(this.actionContext);
    }
    complete(): void {
        this.busy = false;
    }
}