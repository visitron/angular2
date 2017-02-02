import {Component, OnInit} from "@angular/core";
import {Location} from "@angular/common";
import {DataProvider} from "../service/data.service";
import {ActionService} from "../service/action.service";
import {ActionContext} from "../page-component/actions.component";

@Component({
    templateUrl: 'mockup/admin/config.html'
})
export class AdminConfigComponent implements OnInit {

    private config: any = {};

    constructor(private dataProvider: DataProvider, private location: Location, private actionService: ActionService) {
        this.actionService.subscribe(this.onAction.bind(this));
    }

    ngOnInit(): void {
        $('[data-toggle="tooltip"]').tooltip();
        this.dataProvider.getData('/admin/config/get', data => data
            .map((raw: any) => new Config(raw))
            .forEach((config: Config) => {
                this.config[ConfigName[config.configName]] = config;
            }));

    }

    onAction(context: ActionContext): void {
        context.data = _.values(this.config);
        context.executeAction(false);
    }

}

class Config {
    public configName: ConfigName;
    public valueType: ValueType;
    public intValue: number;
    public stringValue: string;
    public booleanValue: boolean;

    constructor(raw: any) {
        this.configName = (<any> ConfigName)[raw.configName];
        this.valueType = (<any> ValueType)[raw.valueType];
        this.intValue = raw.intValue;
        this.stringValue = raw.stringValue;
        this.booleanValue = raw.booleanValue;
    }

    withValue(value: any): Config {
        switch (this.valueType) {
            case ValueType.BOOLEAN:
                this.booleanValue = value;
                break;
            case ValueType.INTEGER:
                this.intValue = value;
                break;
            case ValueType.STRING:
                this.stringValue = value;
                break;
        }
        return this;
    }

    get value(): any {
        switch (this.valueType) {
            case ValueType.BOOLEAN:
                return this.booleanValue;
            case ValueType.INTEGER:
                return this.intValue;
            case ValueType.STRING:
                return this.stringValue;
        }
    }

    set value(value: any) {
        switch (this.valueType) {
            case ValueType.BOOLEAN:
                this.booleanValue = value;
                break;
            case ValueType.INTEGER:
                this.intValue = value;
                break;
            case ValueType.STRING:
                this.stringValue = value;
                break;
        }
    }
}

enum ValueType {INTEGER, BOOLEAN, STRING}
export enum ConfigName {
    AUTO_EXPIRE_TASK, CAPACITY, AUTO_RELEASE_CART, AUTO_REMOVE_DRAFT_USER, SEND_NOTIFICATIONS
}