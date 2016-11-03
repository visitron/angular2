export class Item {
    constructor(
        public name: string,
        public description: string,
        public lifecycle: number,
        public maintenanceDate: Date,
        public info: Info[]
    ) {}

    public get maintenanceDateString(): string {
        return this.maintenanceDate.toISOString().slice(0, 10);
    }

    public advanced: ItemAdvanced = new ItemAdvanced;
}

export enum Info {
    PHOTO,
    INFO,
    DETAIL,
    MONEY,
    PHONE,
    CART
}

export class InfoMapping {
    private static self: InfoMapping = new InfoMapping();
    private infoMapping: Map<Info, string> = new Map<Info, string>();

    private constructor() {
        this.infoMapping.set(Info.PHOTO, '<span class="glyphicon glyphicon-camera info-sign-color"></span>');
        this.infoMapping.set(Info.INFO, '<span class="glyphicon glyphicon-info-sign info-sign-color"></span>');
        this.infoMapping.set(Info.DETAIL, '<span class="glyphicon glyphicon-list-alt info-sign-color"></span>');
        this.infoMapping.set(Info.MONEY, '<span class="glyphicon glyphicon-usd info-sign-color"></span>');
        this.infoMapping.set(Info.PHONE, '<span class="glyphicon glyphicon-phone info-sign-color"></span>');
        this.infoMapping.set(Info.CART, '<span class="glyphicon glyphicon-shopping-cart info-sign-color"></span>');
    }

    public static get(infos: Info[]): string {
        let result: string[] = [];
        infos.forEach(info => result.push(InfoMapping.self.get(info)));
        return result.join('\n');
    }

    private get(info: Info): string {
        return this.infoMapping.get((<any> Info)[info]);
    }
}



class ItemAdvanced {
    image: string;
    parent: Item;
    requiresSpecialist: boolean;
    specialistCompany: string;
    specialistEmail: string;
    specialistPhone: string;
    specialistCost: number;
    requiresAdditionalDetails: boolean;
    additionalDetailsDescription: string;
    additionalDetailsCost: number;
}