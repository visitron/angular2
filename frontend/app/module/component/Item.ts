export class Item {
    public name: string = null;
    public description: string = null;
    public lifecycle: number = null;
    public maintenanceDate: Date = new Date;
    public info: Info[] = [];
}

export class ItemAdvanced extends Item {
    image: string = null;
    parent: Item = null;
    specialist: Specialist = new Specialist;
    additionalDetails: AdditionalDetail[] = [];
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
    public hasSpecialist: boolean;
    public hasAdditionalDetails: boolean;

    constructor (private _itemAdvanced: ItemAdvanced) {
        this.hasSpecialist = _itemAdvanced.specialist === null;
        this.hasAdditionalDetails = _itemAdvanced.additionalDetails.length != 0;
    }

    get itemAdvanced(): ItemAdvanced {
        return this._itemAdvanced;
    }

    createAdditionalDetail(): void {
        this._itemAdvanced.additionalDetails.push(new AdditionalDetail);
    }

    removeAdditionalDetail(index: number): void {
        this._itemAdvanced.additionalDetails.splice(index, 1);
    }

}