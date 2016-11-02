import construct = Reflect.construct;
import get = Reflect.get;
export class Item {
    private infoGlyphicons: string;
    constructor(
        private name: string,
        private description: string,
        private lifecycle: number,
        private maintenanceDate: Date,
        private info: Info[]
    ) {
        debugger;
        console.log('Constructor ====');
        let result: string[] = [];
        this.info.forEach(info => result.push(InfoMapping.get(info)));
        this.infoGlyphicons = result.join('\n');
    }

    public get maintenanceDateString(): string {
        return this.maintenanceDate.toISOString().slice(0, 10);
    }

    public advanced: ItemAdvanced = new ItemAdvanced;
}

enum Info {
    PHOTO,
    INFO,
    DETAIL,
    MONEY,
    PHONE,
    CART
}

class InfoMapping {
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

    public static get(info: Info): string {
        return InfoMapping.self.get(info);
    }

    private get(info: Info): string {
        return this.infoMapping.get(info);
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