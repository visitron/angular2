import {Component} from "@angular/core";
import {ActivatedRoute} from "@angular/router";
import {DataProvider} from "../service/data-provider.service";
import {Location} from "@angular/common";

@Component({
    selector: 'actions',
    templateUrl: 'mockup/parts/actions.html'
})
export class ActionsComponent {
    private actions: Action[] = [];

    constructor(private dataProvider: DataProvider, private activatedRoute: ActivatedRoute, private location: Location) {
        // this.actions.push(new Action('Drop'));
        // this.actions.push(new Action('Block'));
        // this.actions.push(new Action('Unblock'));
        // this.actions.push(new Action(null));
        // this.actions.push(new Action('Approve'));
        // this.actions.push(new Action('Remove'));
        // this.actions.push(new Action('Destroy'));

        // this.actions[5].disabled = true;
        // this.actions[0].disabled = true;

        dataProvider.getPart(location, 'actions', data => {
            debugger;
            (<string []> data).forEach(name => {
                this.actions.push(new Action(name));
            });

            console.log(data);
        });

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
    }


}