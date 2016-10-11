export class Item {
    public name: string;
    // set name(name: string) {this._name = name}
    // get name() {return this._name}
    description: string;
    lifecycle: number;
    _maintenanceDate: Date;
    get maintenanceDate(): string {
        return this._maintenanceDate.toISOString().slice(0, 10);
    }
    advanced: ItemAdvanced = new ItemAdvanced;
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