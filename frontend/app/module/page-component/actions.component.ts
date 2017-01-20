import {Component, OnDestroy} from "@angular/core";
import {Router, NavigationStart, ActivatedRoute} from "@angular/router";
import {DataProvider} from "../service/data.service";
import {Location} from "@angular/common";
import "rxjs/add/operator/map";
import {Subscription} from "rxjs";

@Component({
    selector: 'actions',
    templateUrl: 'mockup/parts/actions.html'
})
export class ActionsComponent implements OnDestroy {
    private actions: Action[] = [];
    private subscription: Subscription = null;

    constructor(private dataProvider: DataProvider, private location: Location, private router: Router, private activatedRoute: ActivatedRoute) {

        console.log('ActionsComponent is created');

        let getActions = (location: string) => {
            dataProvider.getPart(location, 'actions', data => {
                this.actions.splice(0);
                (<string []> data).forEach(name => {
                    this.actions.push(new Action(name));
                });
            });
        };

        getActions(location.path());

        this.subscription = this.router.events.subscribe(event => {
            if (event instanceof NavigationStart) {
                getActions(event.url);
            }
        });
    }

    ngOnDestroy(): void {
        console.log('ActionsComponent is destroyed');
        this.subscription.unsubscribe();
    }

}

class Action {
    disabled: boolean = false;
    protected get detail(): IconDetail {

        return IconRepository.find(this.name);
    };

    constructor(protected name: string | null) {}

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
        this.repo.set('Drop', new IconDetail('share', 'orange'));
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