import * as moment from "moment";

export class Item {
    public name: string = null;
    public description: string = null;
    public lifecycle: number = null;
    public maintenanceDate: Date = new Date;
    public info: Info[] = [];
}

export class ItemAdvanced extends Item {
    public image: string = null;
    public parent: Item = null;
    public specialist: Specialist = null;
    public additionalDetails: AdditionalDetail[] = [];
}

export class Specialist {
    constructor() {}
    _company: string = null;
    get company(): string {
        return this._company;
    }

    set company(company: string) {
        this._company = company;
    }

    email: string = null;
    phone: number = null;
    cost: number = null;
}

export class AdditionalDetail {
    name: string = null;
    description: string = null;
    cost: number = null;
}

export enum Info {
    PHOTO,
    INFO,
    DETAIL,
    MONEY,
    PHONE,
    CART
}

/**
 * Utility class
 */
export class InfoMapping {
    private static self: InfoMapping = new InfoMapping();
    private infoMapping: Map<Info, string> = new Map<Info, string>();

    public static get(infos: Info[]): string {
        if (infos === undefined || infos === null) return '';
        let result: string[] = [];
        infos.forEach(info => result.push(InfoMapping.self.get(info)));
        return result.join('\n');
    }

    private constructor() {
        this.infoMapping.set(Info.PHOTO, '<span class="glyphicon glyphicon-camera info-sign-color"></span>');
        this.infoMapping.set(Info.INFO, '<span class="glyphicon glyphicon-info-sign info-sign-color"></span>');
        this.infoMapping.set(Info.DETAIL, '<span class="glyphicon glyphicon-list-alt info-sign-color"></span>');
        this.infoMapping.set(Info.MONEY, '<span class="glyphicon glyphicon-usd info-sign-color"></span>');
        this.infoMapping.set(Info.PHONE, '<span class="glyphicon glyphicon-phone info-sign-color"></span>');
        this.infoMapping.set(Info.CART, '<span class="glyphicon glyphicon-shopping-cart info-sign-color"></span>');
    }

    private get(info: Info): string {
        return this.infoMapping.get((<any> Info)[info]);
    }
}

export class ItemAdvancedWrapper {
    public _hasSpecialist: boolean;
    public _hasAdditionalDetails: boolean;
    public submitButtonName: string;

    constructor (private _itemAdvanced: ItemAdvanced) {
        if (_itemAdvanced === null) {
            _itemAdvanced = new ItemAdvanced;
            this.submitButtonName = 'Create';
        } else {
            this.submitButtonName = 'Update';
        }

        if (_itemAdvanced.specialist === undefined) {
            _itemAdvanced.specialist = null;
        }

        if (_itemAdvanced.additionalDetails === undefined || _itemAdvanced.additionalDetails === null) {
            _itemAdvanced.additionalDetails = [];
        }

        this._hasSpecialist = _itemAdvanced.specialist !== null;
        this._hasAdditionalDetails = _itemAdvanced.additionalDetails.length != 0;
        this._itemAdvanced = _itemAdvanced;
    }

    get itemAdvanced(): ItemAdvanced {
        return this._itemAdvanced;
    }

    toDate(value: string): Date {
        //todo how to avoid failures on nulls
        return value == '' ? new Date : moment(value, "dd-MM-yyyy").toDate();
    }

    get hasSpecialist(): boolean {
        return this._hasSpecialist;
    }

    set hasSpecialist(value: boolean) {
        if (value && this._itemAdvanced.specialist === null) {
            this._itemAdvanced.specialist = new Specialist;
        }
        this._hasSpecialist = value;
    }

    get hasAdditionalDetails(): boolean {
        return this._hasAdditionalDetails;
    }

    set hasAdditionalDetails(value: boolean) {
        this._hasAdditionalDetails = value;
    }

    createAdditionalDetail(): void {
        this._itemAdvanced.additionalDetails.push(new AdditionalDetail);
    }

    removeAdditionalDetail(index: number): void {
        this._itemAdvanced.additionalDetails.splice(index, 1);
    }

}