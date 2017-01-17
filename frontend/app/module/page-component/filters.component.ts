import {Component} from "@angular/core";

@Component({
    selector: 'filters',
    templateUrl: 'mockup/parts/filters.html'
})
export class FiltersComponent {

    sampleFilterGroups: FilterGroup[] = [];
    appliedFilters: Filter[] = [];

    constructor() {
        this.sampleFilterGroups.push(new FilterGroup('Group 1', [
            new Filter('boolean','Filter 1', true),
            new Filter('boolean','Filter 2'),
            new Filter('boolean','Filter 3')
        ]));
        this.sampleFilterGroups.push(new FilterGroup('Group 2', [
            new Filter('boolean', 'Filter 4'),
            new Filter('numeric', 'Price'),
            new Filter('date', 'First Login')
        ]));
        this.sampleFilterGroups.push(new FilterGroup('Group 3', [
            new Filter('boolean','Filter 4'),
            new Filter('boolean','Filter 5')
        ]));

        //todo remove this call in the future
        let getAppliedFilters = function(groups: FilterGroup[]): Filter[] {
            let result: Filter[] = [];
            groups.forEach((group0: FilterGroup) => {
                group0.filters.forEach((filter: Filter) => {
                    if (filter.active) {
                        result.push(filter);
                    }
                });
            });

            return result;
        };

        this.appliedFilters = getAppliedFilters(this.sampleFilterGroups);
    }

    hasAnyFilter(): boolean {
        return this.appliedFilters.length > 0;
    }

}

class FilterGroup {
    constructor(private _name: string, private _filters: Filter[], private _expanded?: boolean) {}

    public get name(): string {return this._name}
    public get filters(): Filter[] {return this._filters}
    public get expanded(): boolean {return this._expanded}
}

class Filter {
    constructor(private _type: 'boolean' | 'numeric' | 'date', private _name: string, private _active?: boolean) {}

    public get name(): string {return this._name}
    public get type(): string {return this._type}
    public get active(): boolean {return this._active}

    private isRanged: boolean = false;
    private from: number|Date = null;
    private to: number|Date = null;

    public get description(): string {
        if (this.type === 'boolean') {
            return `${this.name}`;
        }

        if (this.isRanged) {
            return `${this.name}: ${this.from} - ${this.to}`;
        }

        return `${this.name}: greater than ${this.from}`;
    }

}