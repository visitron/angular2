export class Item {
    public name: string;
    public description: string;
    public lifecycle: number;
    public maintenanceDate: Date;
    public info: Info[] = [];
}

export class ItemAdvanced extends Item {
    image: string;
    parent: Item;
    specialist: Specialist;
    additionalDetails: AdditionalDetail[] = [];
}

export class Specialist {
    company: string;
    email: string;
    phone: number;
    cost: number;
}

export class AdditionalDetail {
    name: string;
    description: string;
    cost: number;
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