import {Component} from "@angular/core";

@Component({
    selector: 'filters',
    templateUrl: 'mockup/parts/filters.html'
})
export class FiltersComponent {

    private filterGroups: FilterGroup[] = [];
    private appliedFilters: Filter[] = [];
    private allFiltersExpanded: boolean = true;

    constructor() {
        this.filterGroups.push(new FilterGroup('Group 1', [
            new Filter('boolean','Filter 1', true),
            new Filter('boolean','Filter 2'),
            new Filter('boolean','Filter 3')
        ], true));
        this.filterGroups.push(new FilterGroup('Group 2', [
            new Filter('boolean', 'Filter 4'),
            new Filter('numeric', 'Price'),
            new Filter('date', 'First Login')
        ], true));
        this.filterGroups.push(new FilterGroup('Group 3', [
            new Filter('boolean','Filter 6'),
            new Filter('boolean','Filter 7')
        ], true));
    }

    hasAnyFilter(): boolean {
        return this.appliedFilters.length > 0;
    }

    applyFilter(filter: Filter): void {
        filter.active = true;
        this.appliedFilters.push(filter);
    }

    applyBtnDisabled(filter: Filter): boolean {
        return _.isEmpty(filter.from) && _.isEmpty(filter.to);
    }

    removeFilter(filter: Filter): void {
        filter.active = false;
        filter.from = null;
        filter.to = null;
        this.appliedFilters.splice(this.appliedFilters.indexOf(filter), 1);
    }

    clearFilters(): void {
        this.appliedFilters.forEach(filter => {
            filter.active = false;
            filter.from = null;
            filter.to = null;
        });
        this.appliedFilters.splice(0);
    }

    collapseOrExpand(group: FilterGroup): void {
        group.expanded = !group.expanded;
        let allExpanded = false;
        this.filterGroups.forEach(group => {allExpanded = allExpanded || group.expanded});
        this.allFiltersExpanded = allExpanded;
    }

    collapseOrExpandAll(): void {
        this.allFiltersExpanded = !this.allFiltersExpanded;
        this.filterGroups.forEach(group => group.expanded = this.allFiltersExpanded)
    }

}

class FilterGroup {
    constructor(private _name: string, private _filters: Filter[], private _expanded?: boolean) {}

    public get name(): string {return this._name}
    public get filters(): Filter[] {return this._filters}
    public get expanded(): boolean {return this._expanded}
    public set expanded(value: boolean) {this._expanded = value}
}

class Filter {
    constructor(private _type: 'boolean' | 'numeric' | 'date', private _name: string, private _active?: boolean) {}

    public get name(): string {return this._name}
    public get type(): string {return this._type}
    public get active(): boolean {return this._active}
    public set active(value: boolean) {this._active = value}

    private _from: number | Date = null;
    private _to: number | Date = null;
    
    public get from(): number | Date {return this._from}
    public set from(value: number | Date) {this._from = value}

    public get to(): number | Date {return this._to}
    public set to(value: number | Date) {this._to = value}

    public get description(): string {
        if (this.type === 'boolean') {
            return `${this.name}`;
        }

        if (!_.isEmpty(this.from) && !_.isEmpty(this.to)) {
            if (this.from == this.to) {
                return `${this.name}: = ${this.from}`;
            }
            return `${this.name}: ${this.from} - ${this.to}`;
        }

        if (!_.isEmpty(this.from) && _.isEmpty(this.to)) {
            return `${this.name}: >= ${this.from}`;
        }

        return `${this.name}: <= ${this.to}`;
    }

}