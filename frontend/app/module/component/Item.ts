export class Item {
    name: string;
    description: string;
    lifecycle: number;
    maintenanceDate: Date;
    get maintenanceDateString(): string {
        return this.maintenanceDate.toISOString().slice(0, 10);
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