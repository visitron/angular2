import {Component, OnInit} from "@angular/core";
import {Location} from "@angular/common";
import {DataProvider} from "../service/data.service";

@Component({
    templateUrl: 'mockup/admin/config.html'
})
export class AdminConfigComponent implements OnInit {

    constructor(private dataProvider: DataProvider, private location: Location) {}

    ngOnInit(): void {
        $('[data-toggle="tooltip"]').tooltip();

        this.dataProvider.getData('/admin/config/get', data => data.map((raw: any) => new Config(raw)));

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
}

enum ValueType {INTEGER, BOOLEAN, STRING}
enum ConfigName {
    AUTO_EXPIRE_TASK, CAPACITY, AUTO_RELEASE_CART, AUTO_REMOVE_DRAFT_USER, SEND_NOTIFICATIONS
}