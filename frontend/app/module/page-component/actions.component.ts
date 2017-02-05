import {Component, OnDestroy} from "@angular/core";
import {Router, NavigationStart} from "@angular/router";
import {DataProvider} from "../service/data.service";
import {Location} from "@angular/common";
import "rxjs/add/operator/map";
import {Subscription} from "rxjs";
import {SlickGridProvider} from "../service/slick-grid.service";
import {Http, Headers} from "@angular/http";
import {ActionService} from "../service/action.service";
import {ConfigProvider} from "../service/config.service";

@Component({
    selector: 'actions',
    templateUrl: 'mockup/parts/actions.html'
})
export class ActionsComponent implements OnDestroy {
    private actions: Action[] = [];
    private subscription: Subscription = null;
    private busy: boolean = false;
    private actionURL: string;

    constructor(private dataProvider: DataProvider, private location: Location, private router: Router,
                private slickGridProvider: SlickGridProvider, private http: Http,
                private actionService: ActionService, private configProvider: ConfigProvider) {

        console.log('ActionsComponent is created');

        let getActions = (location: string) => {
            dataProvider.getPart(location, 'actions', data => {
                this.actions.splice(0);
                (<string []> data).forEach((raw: any) => {
                    this.actions.push(new Action(raw));
                });
            });
        };
        this.actionURL = configProvider.host + location.path();
        getActions(location.path());

        this.subscription = this.router.events.subscribe(event => {
            if (event instanceof NavigationStart) {
                this.actionURL = configProvider.host + event.url;
                getActions(event.url);
            }
        });
    }

    ngOnDestroy(): void {
        console.log('ActionsComponent is destroyed');
        this.subscription.unsubscribe();
    }

    sendAction(action: Action): void {
        this.busy = true;
        let context: ActionContext = new ActionContext(action, this.actionURL, this.http, this.slickGridProvider, this.complete.bind(this));
        this.actionService.sendAction(context);
    }

    complete() {
        this.busy = false;
    }

}

class Action {
    disabled: boolean = false;
    protected get detail(): IconDetail {

        return IconRepository.find(this.name);
    };

    name: string = null;
    id: string = null;

    constructor(raw: any | null) {
        if (raw === null) return;
        this.name = raw.name;
        this.id = raw.id;
    }

    public get disabledClass(): string {
        return this.disabled ? 'disabled' : 'highlight';
    }
}

class IconDetail {
    private iconClass: string;
    constructor(iconLogoClass: string, iconColorClass: string) {
        this.iconClass = 'glyphicon glyphicon-' + iconLogoClass +
            ' action-' + iconColorClass;
    }
}

class IconRepository {
    private static instance: IconRepository = new IconRepository();
    public static find(name: string): IconDetail {
        if (_.isEmpty(name)) return new IconDetail('', '');
        let result: IconDetail = <IconDetail> IconRepository.instance.repo.get(name);
        return _.isEmpty(result) ? new IconDetail('question-sign', 'gold') : result;
    }

    private repo: Map<string, IconDetail> = new Map<string, IconDetail>();

    private constructor() {
        this.repo.set('Drop Session', new IconDetail('share', 'orange'));
        this.repo.set('Block', new IconDetail('ban-circle', 'orange'));
        this.repo.set('Unblock', new IconDetail('play-circle', 'green'));
        this.repo.set('Approve', new IconDetail('ok-circle', 'green'));
        this.repo.set('Remove', new IconDetail('trash', 'red'));
        this.repo.set('Export to Excel', new IconDetail('share', 'green'));
        this.repo.set('Export to PDF', new IconDetail('share', 'green'));
        this.repo.set('Save', new IconDetail('floppy-disk', 'green'));
        this.repo.set('Print', new IconDetail('print', 'orange'));
        this.repo.set('Reset', new IconDetail('repeat', 'red'));
    }

}

export class ActionContext {
    public data: any;

    constructor(public action: Action, private actionURL: string, private http: Http, private slickGridProvider: SlickGridProvider, private complete: () => void) {}

    public executeAction(refreshSlickGrid?: boolean, action?: (monitor: () => void) => void): void {

        if (_.isFunction(action)) {
            action(this.complete.bind(this));
            return;
        }

        let headers: Headers = new Headers;
        headers.append('Content-Type', 'application/json');
        this.http
            .post(`${this.actionURL}/action/${this.action.id}`, this.data, {headers: headers})
            .subscribe(response => {
                if (refreshSlickGrid) this.slickGridProvider.refresh();
                this.complete();
            }, error => {
                this.complete();
                let selector = $('#action-error-popover-id');
                selector
                    .popover({
                        content: error.text(),
                        delay: 300,
                        placement: 'left',
                        title: "Error",
                    })
                    .popover('show');

                setTimeout(() => {selector.popover('destroy')}, 2000);
            });
    };
}
