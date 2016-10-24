export class Item {
    public name: string;
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